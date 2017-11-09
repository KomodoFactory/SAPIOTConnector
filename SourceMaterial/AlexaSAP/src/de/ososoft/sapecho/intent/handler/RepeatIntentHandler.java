package de.ososoft.sapecho.intent.handler;

import java.util.function.Function;

import com.amazon.speech.speechlet.SpeechletResponse;

import de.ososoft.sapecho.intent.IntentName;
import de.ososoft.sapecho.utils.ResponseMaker;
import de.ososoft.sapecho.utils.container.ConversationInformation;
import de.ososoft.sapecho.utils.container.MaterialInformation;
import de.ososoft.sapecho.utils.container.SessionAttributeName;

public class RepeatIntentHandler extends IntentHandler {

	public RepeatIntentHandler() {
		intentMap.put(IntentName.AMAZON_HELP, this::getHelpResponse);
		intentMap.put(IntentName.REPEAT_ARTICLE, this::getRepeatArticleResponse);
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

	private SpeechletResponse getRepeatArticleResponse(final ConversationInformation conversationInformation) {
		int amountOfPossibleMatches = 0;
		String speechText;
		String repromptText;
		String artikel = conversationInformation.getSlotArticel().getValue();
		if (artikel != null) {
			amountOfPossibleMatches = MaterialInformation.getInstance().countPossibleMatchesForMaterial(artikel);
		}
		if (amountOfPossibleMatches == 1) {
			conversationInformation.setSessionArticle(artikel);
			conversationInformation.setNextIntent(SessionAttributeName.NEXT_INTENT_FIELDS);
			conversationInformation.setFilledFields(SessionAttributeName.FILLED_FIELDS_ARTICLE);
			speechText = "Ihr Artikel wurde erkannt. Wie viele " + artikel + "wollen sie anfordern?";
			repromptText = "Bitte nennen sie die Menge die sie anfordern wollen.";
		} else if (amountOfPossibleMatches > 1) {
			String mostlikelyArticel = MaterialInformation.getInstance().getMostLikelyMatchForMaterial(artikel);
			conversationInformation.setSessionArticle(mostlikelyArticel);
			conversationInformation.setNextIntent(SessionAttributeName.NEXT_INTENT_MATCH_ARTICLE);
			conversationInformation.setFilledFields(SessionAttributeName.FILLED_FIELDS_DEFAULT);
			speechText = "Es wurde mehr als ein passender Artikel gefunden. Meinten sie " + mostlikelyArticel;
			repromptText = "Meinten sie " + mostlikelyArticel + "?";
		} else {
			conversationInformation.setSessionArticle("");
			conversationInformation.setNextIntent(SessionAttributeName.NEXT_INTENT_REPEAT);
			conversationInformation.setFilledFields(SessionAttributeName.FILLED_FIELDS_DEFAULT);
			speechText = "Es wurde kein Artikel mit den Namen " + artikel + " gefunden";
			repromptText = "Bitte wiederhohlen sie den Artikel den sie anfordern wollen.";
			
			
			
			return ResponseMaker.createAskResponse(speechText, repromptText, "Article", artikel);
			
		}
		return ResponseMaker.createAskResponse(speechText, repromptText);
	}

	private SpeechletResponse getHelpResponse(final ConversationInformation conversationInformation) {
		conversationInformation.getSessionArticle();
		String speechText = "Sie müssen den Artikel den sie anfordern wollen wiederhohlen. Wenn sie diesen bereits genannt haben, jedoch ein unerwünschter Artikel ausgewählt wurde, versuchen sie weitere Informationen oder den Vollen Namen des Artikels zu nutzen.";
		String repromptText = "Bitte wiederhohlen sie den Artikel den sie anfordern wollen.";
		return ResponseMaker.createAskResponse(speechText, repromptText);
	}

	private SpeechletResponse getNoValidIntentResponse(final ConversationInformation conversationInformation) {
		conversationInformation.getSessionArticle();
		String speechText = "Ich kann ihren Auftrag leider nicht verstehen. Bitte nennen sie den Artikel den sie anfordern wollen.";
		String repromptText = "Bitte wiederhohlen sie den Artikel den sie anfordern wollen. Versuchen sie dabei möglichst genau zu sein.";
		return ResponseMaker.createAskResponse(speechText, repromptText);
	}

}