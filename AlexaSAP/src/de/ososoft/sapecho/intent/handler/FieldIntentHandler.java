package de.ososoft.sapecho.intent.handler;

import java.util.function.Function;

import com.amazon.speech.speechlet.SpeechletResponse;

import de.ososoft.sapecho.intent.IntentName;
import de.ososoft.sapecho.utils.ResponseMaker;
import de.ososoft.sapecho.utils.container.ConversationInformation;
import de.ososoft.sapecho.utils.container.SessionAttributeName;

public class FieldIntentHandler extends IntentHandler {
	public FieldIntentHandler() {
		intentMap.put(IntentName.AMAZON_HELP, this::getHelpResponse);
		intentMap.put(IntentName.FIELDS, this::getQuantitiyResponse);
		intentMap.put(IntentName.DEFAULT, this::getNoValidIntentResponse);
	}

	@Override
	public Function<ConversationInformation, SpeechletResponse> get(String intentName) {
		if (intentMap.containsKey(intentName)) {
			return intentMap.get(intentName);
		}
		return intentMap.get(IntentName.DEFAULT);
	}

	private SpeechletResponse getQuantitiyResponse(final ConversationInformation conversationInformation) {
		if (conversationInformation.getSlotQuantity().getValue() != null) {
			String articel = conversationInformation.getSessionArticle();
			String quantity = conversationInformation.getSlotQuantity().getValue();
			conversationInformation.setFilledFields(SessionAttributeName.FILLED_FIELDS_ALL);
			conversationInformation.setNextIntent(SessionAttributeName.NEXT_INTENT_NEW_ORDER);

			return IntentHandler.handleSAPOrder(articel, quantity, articel);
		}

		String speechText = "Es tut mir leid, ich konnte ihre gewünschte Menge nicht verstehen. bitte wiederhohlen sie die gewünschte Menge";
		String repromptText = "bitte wiederhohlen sie ihre gewünschte menge";
		return ResponseMaker.createAskResponse(speechText, repromptText);
	}

	private SpeechletResponse getHelpResponse(final ConversationInformation conversationInformation) {
		String articel = conversationInformation.getSessionArticle();
		String speechText = "Ich werde für sie " + articel
				+ " anfordern. Nennen sie mir bitte welche Menge dieser Artikel sie benötigen. Wenn sie diesen Artikel nicht anfordern wollen sagen sie bitte Alexa Stop";
		String repromptText = "Wenn Sie die Bestellanforderung abbrechen wollen sagen sie bitte ... Alexa Stop. Wenn nicht nennen sie bitte die Menge die sie bestellen wollen.";
		return ResponseMaker.createAskResponse(speechText, repromptText);
	}

	private SpeechletResponse getNoValidIntentResponse(final ConversationInformation conversationInformation) {
		String articel = conversationInformation.getSessionArticle();
		String speechText = "Ich kann ihren Auftrag leider nicht verstehen. Bitte nennen sie eine Menge die sie von " + articel
				+ " anfordern möchten?";
		String repromptText = "Wenn Sie die Bestellanforderung abbrechen wollen sagen sie bitte ... Alexa Stop. Wenn nicht nenen sie bitte die Menge die sie bestellen wollen.";
		return ResponseMaker.createAskResponse(speechText, repromptText);
	}
}