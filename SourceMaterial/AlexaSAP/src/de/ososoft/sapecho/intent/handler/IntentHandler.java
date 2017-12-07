package de.ososoft.sapecho.intent.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.amazon.speech.speechlet.SpeechletResponse;

import de.ososoft.sapecho.intent.IntentName;
import de.ososoft.sapecho.utils.ResponseMaker;
import de.ososoft.sapecho.utils.SAPConnector;
import de.ososoft.sapecho.utils.container.ConversationInformation;
import de.ososoft.sapecho.utils.container.MaterialInformation;
import de.ososoft.sapecho.utils.container.SessionAttributeName;

public abstract class IntentHandler {

	protected final Map<String, Function<ConversationInformation, SpeechletResponse>> intentMap = new HashMap<>();

	public IntentHandler() {
		intentMap.put(IntentName.AMAZON_CANCEL, this::getCancelResponse);
		intentMap.put(IntentName.AMAZON_STOP, this::getStopResponse);
		intentMap.put(IntentName.CREATE_BANF, IntentHandler::getSAPOrderResponse);
	}

	public static SpeechletResponse getWelcomeResponse() {
		String speechText = "Guten Tag, sie können jederzeit um hilfe bitten um weitere Informationen zu erhalten. Wenn sie eine Bestellanforderung erstellen wollen sagen sie Bestelle gefolgt von dem Namen des Artikels";
		String repromptText = "Du kannst versuchen Teste bitte S A P oder bestelle bitte gefolgt vom namen des artikels zu mir zu sagen";
		return ResponseMaker.createAskResponse(speechText, repromptText);
	}

	private SpeechletResponse getCancelResponse(final ConversationInformation conversationInformation) {
		conversationInformation.setSessionArticle("");
		conversationInformation.setNextIntent(SessionAttributeName.NEXT_INTENT_DEFAULT);
		conversationInformation.setFilledFields(SessionAttributeName.NEXT_INTENT_DEFAULT);
		return getWelcomeResponse();
	}

	private SpeechletResponse getStopResponse(final ConversationInformation conversationInformation) {
		conversationInformation.setSessionArticle("");
		conversationInformation.setNextIntent(SessionAttributeName.NEXT_INTENT_DEFAULT);
		conversationInformation.setFilledFields(SessionAttributeName.NEXT_INTENT_DEFAULT);
		return ResponseMaker.createTellResponse("");
	}

	public abstract Function<ConversationInformation, SpeechletResponse> get(String intentName);

	protected static SpeechletResponse getSAPOrderResponse(final ConversationInformation conversationInformation) {
		String quantity = handlePossibleSessionQuantitiy(conversationInformation);
		handlePossibleSessionMatches(conversationInformation);

		String originalArtikel = (conversationInformation.getSlotArticel().getValue() != null)
				? conversationInformation.getSlotArticel().getValue()
				: "";

		String speechText = "";
		String repromptText = "";

		if (conversationInformation.getNextIntent().equals(SessionAttributeName.NEXT_INTENT_FIELDS)) {
			String mostlikelyArtikel = conversationInformation.getSessionArticle();
			speechText = "Ihr Artikel wurde erkannt. Wie viele " + mostlikelyArtikel + " wollen sie anfordern?";
			repromptText = "Bitte nennen sie die Menge die sie anfordern wollen.";
		} else if (conversationInformation.getNextIntent().equals(SessionAttributeName.NEXT_INTENT_NEW_ORDER)) {
			String mostlikelyArtikel = conversationInformation.getSessionArticle();
			return handleSAPOrder(mostlikelyArtikel, quantity, originalArtikel);
		} else if (conversationInformation.getNextIntent().equals(SessionAttributeName.NEXT_INTENT_MATCH_ARTICLE)) {
			String mostlikelyArtikel = conversationInformation.getSessionArticle();

			speechText = "Es wurde mehr als ein passender Artikel gefunden. Meinten sie " + mostlikelyArtikel;
			repromptText = "Meinten sie " + mostlikelyArtikel;
		} else if (conversationInformation.getNextIntent().equals(SessionAttributeName.NEXT_INTENT_REPEAT)) {
			speechText = "Es wurde kein Artikel mit den Namen " + originalArtikel + " gefunden";
			repromptText = "Bitte wiederhohlen sie den Artikel den sie anfordern wollen.";
		} else {
			speechText = "Es tut mir leid, es ist ein Fehler aufgetreten.";
			repromptText = "Bitte nennen sie den Artikel den sie bestellen wollen";
		}

		return ResponseMaker.createAskResponse(speechText, repromptText, "Article", originalArtikel);
	}

	public static SpeechletResponse handleSAPOrder(String articel, String quantity, String originalArtikel) {
		String speechText;
		String repromptText = "Wollen Sie eine weitere Bestellanforderung aufsetzen?";
		try {
			if (new SAPConnector().sendOrder(articel, quantity)) {
				speechText = "Ich habe für sie" + quantity + " " + articel
						+ " angefordert. Wollen Sie eine weitere Bestellanforderung aufsetzen?";
			}else {
				speechText = "Ich habe für sie " + quantity + " " + articel + " bestellen können";
			}
		} catch (Exception e) {
			return ResponseMaker.createTellResponse("Der Artikel " + articel + " ist nicht im SAP System eingespeichert.",
					"Fehler", e.getMessage());
		}
		return ResponseMaker.createAskResponse(speechText, repromptText, "Article", originalArtikel);
	}

	private static int handlePossibleSessionMatches(ConversationInformation conversationInformation) {
		int amountOfPossibleMatches = 0;
		String artikel = conversationInformation.getSlotArticel().getValue();
		if (artikel != null) {
			amountOfPossibleMatches = MaterialInformation.getInstance().countPossibleMatchesForMaterial(artikel);
		}
		if (amountOfPossibleMatches == 1) {
			artikel = MaterialInformation.getInstance().getMostLikelyMatchForMaterial(artikel);
			conversationInformation.setSessionArticle(artikel);
			if (!conversationInformation.getFilledFields().equals(SessionAttributeName.FILLED_FIELDS_QUANTITIY)) {
				conversationInformation.setFilledFields(SessionAttributeName.FILLED_FIELDS_ARTICLE);
				conversationInformation.setNextIntent(SessionAttributeName.NEXT_INTENT_FIELDS);
			} else {
				conversationInformation.setFilledFields(SessionAttributeName.FILLED_FIELDS_ALL);
				conversationInformation.setNextIntent(SessionAttributeName.NEXT_INTENT_NEW_ORDER);
			}
		} else if (amountOfPossibleMatches > 1) {
			String mostlikelyArticel = MaterialInformation.getInstance().getMostLikelyMatchForMaterial(artikel);
			conversationInformation.setSessionArticle(mostlikelyArticel);
			conversationInformation.setNextIntent(SessionAttributeName.NEXT_INTENT_MATCH_ARTICLE);
		} else {
			conversationInformation.setSessionArticle(" der Artikel wurde nicht verstanden ");
			conversationInformation.setNextIntent(SessionAttributeName.NEXT_INTENT_REPEAT);
		}
		return amountOfPossibleMatches;
	}

	private static String handlePossibleSessionQuantitiy(ConversationInformation conversationInformation) {
		String quantity = "";
		if (conversationInformation.getSlotQuantity() != null) {
			quantity = (conversationInformation.getSlotQuantity().getValue() != null)
					? conversationInformation.getSlotQuantity().getValue()
					: "";
		}
		if (!quantity.equals("")) {
			conversationInformation.setFilledFields(SessionAttributeName.FILLED_FIELDS_QUANTITIY);
			conversationInformation.setSessionQuantity(quantity);
		}
		return quantity;
	}

}