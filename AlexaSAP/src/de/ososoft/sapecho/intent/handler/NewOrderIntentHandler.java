package de.ososoft.sapecho.intent.handler;

import java.util.function.Function;

import com.amazon.speech.speechlet.SpeechletResponse;

import de.ososoft.sapecho.intent.IntentName;
import de.ososoft.sapecho.utils.ResponseMaker;
import de.ososoft.sapecho.utils.container.ConversationInformation;
import de.ososoft.sapecho.utils.container.SessionAttributeName;

public class NewOrderIntentHandler extends IntentHandler {

	public NewOrderIntentHandler() {
		intentMap.put(IntentName.AMAZON_HELP, this::getHelpResponse);
		intentMap.put(IntentName.AMAZON_CANCEL, this::getPersonalCancelResponse);
		intentMap.put(IntentName.NEGATIV_CONFIRMATION, this::getNegativConfirmationResponse);
		intentMap.put(IntentName.POSITIV_CONFIRMATION, this::getPositivConfirmationResponse);
		intentMap.put(IntentName.CREATE_BANF, IntentHandler::getSAPOrderResponse);
		intentMap.put(IntentName.DEFAULT, this::getNoValidIntentResponse);
	}

	@Override
	public Function<ConversationInformation, SpeechletResponse> get(String intentName) {
		if (intentMap.containsKey(intentName)) {
			return intentMap.get(intentName);
		}
		return intentMap.get(IntentName.DEFAULT);
	}

	private SpeechletResponse getNegativConfirmationResponse(final ConversationInformation conversationInformation) {
		conversationInformation.setSessionArticle("");
		conversationInformation.setNextIntent(SessionAttributeName.NEXT_INTENT_DEFAULT);
		conversationInformation.setFilledFields(SessionAttributeName.FILLED_FIELDS_DEFAULT);
		String speechText = "Ich habe ihnen gerne geholfen.";
		return ResponseMaker.createTellResponse(speechText);
	}

	private SpeechletResponse getPositivConfirmationResponse(final ConversationInformation conversationInformation) {
		conversationInformation.setSessionArticle("");
		conversationInformation.setNextIntent(SessionAttributeName.NEXT_INTENT_DEFAULT);
		conversationInformation.setFilledFields(SessionAttributeName.NEXT_INTENT_DEFAULT);
		String speechText = "Bitte nennen sie den Artikel den sie anfordern wollen";
		return ResponseMaker.createAskResponse(speechText, speechText);
	}

	private SpeechletResponse getHelpResponse(final ConversationInformation conversationInformation) {
		conversationInformation.getSessionArticle();
		String speechText = "Wenn Sie einen weiteren Artikel anfordern wollen sagen sie bitte Ja. Wenn sie keine weiteren Anfordern erstellen wollen sagen sie bitte Nein.";
		return ResponseMaker.createAskResponse(speechText, speechText);
	}

	private SpeechletResponse getPersonalCancelResponse(final ConversationInformation conversationInformation) {
		conversationInformation.setSessionArticle("");
		conversationInformation.setNextIntent(SessionAttributeName.NEXT_INTENT_DEFAULT);
		conversationInformation.setFilledFields(SessionAttributeName.NEXT_INTENT_DEFAULT);
		return ResponseMaker.createTellResponse("");
	}

	private SpeechletResponse getNoValidIntentResponse(final ConversationInformation conversationInformation) {
		conversationInformation.getSessionArticle();
		String speechText = "Ich kann ihren Auftrag leider nicht verstehen. Bitte beantworten sie die Frage möchten sie eine weitere Anforderung erstellen?";
		String repromptText = " Möchten sie eine weitere Anforderung erstellen?";
		return ResponseMaker.createAskResponse(speechText, repromptText);
	}
}