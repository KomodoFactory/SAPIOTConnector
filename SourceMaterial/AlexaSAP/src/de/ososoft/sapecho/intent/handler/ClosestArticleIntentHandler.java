package de.ososoft.sapecho.intent.handler;

import java.util.function.Function;

import com.amazon.speech.speechlet.SpeechletResponse;

import de.ososoft.sapecho.intent.IntentName;
import de.ososoft.sapecho.utils.ResponseMaker;
import de.ososoft.sapecho.utils.container.ConversationInformation;
import de.ososoft.sapecho.utils.container.SessionAttributeName;

public class ClosestArticleIntentHandler extends IntentHandler {

	public ClosestArticleIntentHandler() {
		intentMap.put(IntentName.AMAZON_HELP, this::getHelpResponse);
		intentMap.put(IntentName.POSITIV_CONFIRMATION, this::getPositivConfirmationResponse);
		intentMap.put(IntentName.NEGATIV_CONFIRMATION, this::getNegativConfirmationResponse);
		intentMap.put(IntentName.CREATE_BANF, IntentHandler::getSAPOrderResponse);
		intentMap.put(IntentName.DEFAULT, this::getNoValidIntentResponse);
		intentMap.put(IntentName.BOX_ORDER, this::getBoxIntentResponse);
	}

	@Override
	public Function<ConversationInformation, SpeechletResponse> get(String intentName) {
		if (intentMap.containsKey(intentName)) {
			return intentMap.get(intentName);
		}
		return intentMap.get(IntentName.DEFAULT);
	}

	private SpeechletResponse getNegativConfirmationResponse(final ConversationInformation conversationInformation) {
		conversationInformation.setFilledFields(SessionAttributeName.FILLED_FIELDS_DEFAULT);
		conversationInformation.setNextIntent(SessionAttributeName.NEXT_INTENT_REPEAT);
		conversationInformation.setSessionArticle("");
		String speechText = "Bitte wiederhohlen sie den Artikel den sie anfordern wollen.";
		return ResponseMaker.createAskResponse(speechText, speechText);
	}

	private SpeechletResponse getPositivConfirmationResponse(final ConversationInformation conversationInformation) {
		conversationInformation.setFilledFields(SessionAttributeName.FILLED_FIELDS_ARTICLE);
		conversationInformation.setNextIntent(SessionAttributeName.NEXT_INTENT_FIELDS);
		String closestArticle = conversationInformation.getSessionArticle();
		String speechText = "Wie viele " + closestArticle + " wollen Sie anfordern?";
		return ResponseMaker.createAskResponse(speechText, speechText);
	}

	private SpeechletResponse getHelpResponse(final ConversationInformation conversationInformation) {
		String closestArticel = conversationInformation.getSessionArticle();
		String speechText = "Wenn Sie " + closestArticel
				+ " anfordern wollen sagen sie bitte Ja. Wenn dies nicht ihr gewünschter Artikel ist sagen sie bitte Nein.";
		return ResponseMaker.createAskResponse(speechText, speechText);
	}

	private SpeechletResponse getNoValidIntentResponse(final ConversationInformation conversationInformation) {
		String closestArticel = conversationInformation.getSessionArticle();
		String speechText = "Ich kann ihren Auftrag leider in diesen Kontext nicht verstehen. Möchten sie " + closestArticel
				+ " anfordern?";
		String repromptText = "Wenn Sie nicht " + closestArticel + " anfordern wollen sagen sie bitte ... Nein.";
		return ResponseMaker.createAskResponse(speechText, repromptText);
	}
	
	private SpeechletResponse getBoxIntentResponse(final ConversationInformation conversationInformation) {
		String articel = "Box";
		String quantity = "1";
		conversationInformation.setNextIntent(SessionAttributeName.NEXT_INTENT_NEW_ORDER);
		return IntentHandler.handleSAPOrder(articel, quantity, articel);
	}
	
	
}