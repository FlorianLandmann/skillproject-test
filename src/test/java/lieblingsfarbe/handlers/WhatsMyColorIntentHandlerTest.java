package lieblingsfarbe.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import lieblingsfarbe.PhrasesAndConstants;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class WhatsMyColorIntentHandlerTest {
    private WhatsMyColorIntentHandler handler;

    @Before
    public void setup() {
        handler = new WhatsMyColorIntentHandler();
    }

    @Test
    public void testCanHandle() {
        final HandlerInput inputMock = Mockito.mock(HandlerInput.class);
        when(inputMock.matches(any())).thenReturn(true);
        assertTrue(handler.canHandle(inputMock));
    }

    @Test
    public void testHandle() {
        final Response response = TestUtil.standardTestForHandle(handler);
        assertTrue(response.getOutputSpeech().toString().contains(PhrasesAndConstants.CARD_TITLE));
    }

    @Test
    public void testHandleColorFromSessionAttributes() {
        final Response response = TestUtil.sessionAttributesTestForHandle(handler);
        assertTrue(response.getOutputSpeech().toString().contains(PhrasesAndConstants.CARD_TITLE));
        assertTrue(response.getOutputSpeech().toString().contains("rot"));
    }

    @Test
    public void testHandleColorFromPersistentAttributes() {
        final Response response = TestUtil.persistentAttributesTestForHandle(handler);
        assertTrue(response.getOutputSpeech().toString().contains(PhrasesAndConstants.CARD_TITLE));
        assertTrue(response.getOutputSpeech().toString().contains("blau"));
    }

    @Test
    public void testHandleNoColor() {
        final Response response = TestUtil.noAttributesTestForHandle(handler);
        assertTrue(response.getOutputSpeech().toString().contains(PhrasesAndConstants.CARD_TITLE));
    }
}