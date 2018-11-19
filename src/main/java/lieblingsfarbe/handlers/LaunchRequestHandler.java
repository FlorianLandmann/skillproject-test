/*
     Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

     Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
     except in compliance with the License. A copy of the License is located at

         http://aws.amazon.com/apache2.0/

     or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
     the specific language governing permissions and limitations under the License.
*/

package main.java.lieblingsfarbe.handlers;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;


import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import com.amazon.ask.response.ResponseBuilder;
import main.java.lieblingsfarbe.PhrasesAndConstants;
import main.java.lieblingsfarbe.model.Lieblingsfarbe;

import static com.amazon.ask.request.Predicates.requestType;

public class LaunchRequestHandler implements RequestHandler {


    @Override
    public boolean canHandle(HandlerInput input) {

        return input.matches(requestType(LaunchRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

        ResponseBuilder responseBuilder = input.getResponseBuilder();

        //see if color is stored already
        AttributesManager attributesManager = input.getAttributesManager();
        Map<String, Object> persistentAttributes = attributesManager.getPersistentAttributes();
        String favoriteColor = (String) persistentAttributes.get(PhrasesAndConstants.COLOR_KEY);

        if(favoriteColor != null){
            //put stored color in session Attributes
            Lieblingsfarbe lieblingsfarbe = new Lieblingsfarbe(favoriteColor);
            input.getAttributesManager().setSessionAttributes(Collections.singletonMap(PhrasesAndConstants.COLOR_KEY,
                    lieblingsfarbe.getLieblingsfarbe()));
            String speechText =
                    String.format("%s %s. %s", PhrasesAndConstants.LIEBLINGSFARBE_IS, lieblingsfarbe.getLieblingsfarbe(),
                            PhrasesAndConstants.CHANGE_LIEBLINGSFARBE);
            responseBuilder.withSimpleCard(PhrasesAndConstants.CARD_TITLE, speechText)
                    .withSpeech(speechText)
                    .withReprompt(PhrasesAndConstants.HELP_REPROMPT);
        } else {
            responseBuilder.withSimpleCard(PhrasesAndConstants.CARD_TITLE, PhrasesAndConstants.WELCOME)
                    .withSpeech(PhrasesAndConstants.WELCOME)
                    .withReprompt(PhrasesAndConstants.HELP_REPROMPT);
        }

        return responseBuilder.build();
    }
}
