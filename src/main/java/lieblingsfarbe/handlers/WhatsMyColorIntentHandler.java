/*
     Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

     Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
     except in compliance with the License. A copy of the License is located at

         http://aws.amazon.com/apache2.0/

     or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
     the specific language governing permissions and limitations under the License.
*/

package lieblingsfarbe.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.response.ResponseBuilder;
import lieblingsfarbe.PhrasesAndConstants;
import lieblingsfarbe.model.Lieblingsfarbe;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class WhatsMyColorIntentHandler implements RequestHandler {


    @Override
    public boolean canHandle(HandlerInput input) {

        return input.matches(intentName("WhatsMyColorIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Lieblingsfarbe lieblingsfarbe = new Lieblingsfarbe((String) input.getAttributesManager().getSessionAttributes().get(PhrasesAndConstants.COLOR_KEY));

        ResponseBuilder responseBuilder = input.getResponseBuilder();

        if (lieblingsfarbe.isValid()) {
            String speechText = String.format("%s %s. %s.", PhrasesAndConstants.LIEBLINGSFARBE_IS, lieblingsfarbe.getFarbe(), PhrasesAndConstants.GOOD_BYE);
            responseBuilder.withSpeech(speechText)
                    .withSimpleCard(PhrasesAndConstants.CARD_TITLE, speechText);
        } else {
            // Since the user's favorite color is not set render an error message.
            responseBuilder.withSpeech(PhrasesAndConstants.LIEBLINGSFABE_UNKNOWN)
                    .withSimpleCard(PhrasesAndConstants.CARD_TITLE, PhrasesAndConstants.LIEBLINGSFABE_UNKNOWN)
                    .withShouldEndSession(false);
        }
        return responseBuilder.build();
    }
}
