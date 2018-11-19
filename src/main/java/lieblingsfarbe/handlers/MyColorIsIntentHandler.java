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
import com.amazon.ask.model.*;
import com.amazon.ask.response.ResponseBuilder;
import main.java.lieblingsfarbe.PhrasesAndConstants;
import main.java.lieblingsfarbe.model.Lieblingsfarbe;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;


import static com.amazon.ask.request.Predicates.intentName;
import static com.amazon.ask.request.Predicates.viewportProfile;

public class MyColorIsIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("MyColorIsIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Request request = input.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        Map<String, Slot> slots = intent.getSlots();

        // Get the color slot from the list of slots.
        Slot favoriteColorSlot = slots.get(PhrasesAndConstants.COLOR_SLOT);

        ResponseBuilder responseBuilder = input.getResponseBuilder();

        if (favoriteColorSlot.getValue() != null && favoriteColorSlot.getResolutions().toString().contains("ER_SUCCESS_MATCH")) {
            // Store the user's favorite color in the Session and store in DB then create response.
            Lieblingsfarbe lieblingsfarbe = new Lieblingsfarbe(favoriteColorSlot.getValue());
            input.getAttributesManager().setSessionAttributes(Collections.singletonMap(PhrasesAndConstants.COLOR_KEY, lieblingsfarbe.getLieblingsfarbe()));

            //store persistent
            AttributesManager attributesManager = input.getAttributesManager();
            Map<String, Object> persistentAttributes = attributesManager.getPersistentAttributes();
            persistentAttributes.put(PhrasesAndConstants.COLOR_KEY, lieblingsfarbe.getLieblingsfarbe());
            attributesManager.setPersistentAttributes(persistentAttributes);
            attributesManager.savePersistentAttributes();

            String speechText =
                    String.format("%s %s. %s", PhrasesAndConstants.LIEBLINGSFARBE_IS, lieblingsfarbe.getLieblingsfarbe(), PhrasesAndConstants.WHAT_IS_LIEBLINGSFARBE);
            responseBuilder.withSimpleCard(PhrasesAndConstants.CARD_TITLE, speechText)
                    .withSpeech(speechText)
                    .withShouldEndSession(false);

        } else {
            // Render an error since we don't know what the users favorite color is.
            responseBuilder.withSimpleCard(PhrasesAndConstants.CARD_TITLE, PhrasesAndConstants.SAY_LIEBLINGAFARBE_REPROMPT)
                    .withSpeech(PhrasesAndConstants.SAY_LIEBLINGAFARBE_REPROMPT)
                    .withReprompt(PhrasesAndConstants.REPROMPT_LIEBINGSFARBE)
                    .withShouldEndSession(false);
        }

        return responseBuilder.build();
    }

}
