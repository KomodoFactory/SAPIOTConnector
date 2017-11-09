package de.ososoft.sapecho.intent.handler;

import java.io.IOException;
import java.util.function.Function;

import com.amazon.speech.speechlet.SpeechletResponse;

import de.ososoft.sapecho.intent.IntentName;
import de.ososoft.sapecho.utils.ResponseMaker;
import de.ososoft.sapecho.utils.SAPConnector;
import de.ososoft.sapecho.utils.container.ConversationInformation;
import de.ososoft.sapecho.utils.container.SessionAttributeName;

public class DefaultIntentHandler extends IntentHandler {

	String intentThatChanged;

	public DefaultIntentHandler() {
		super();
		intentMap.put(IntentName.AMAZON_HELP, this::getHelpResponse);
		intentMap.put(IntentName.AMAZON_CANCEL, this::getPersonalCancelResponse);
		intentMap.put(IntentName.SAP_CONNECTION_TEST, this::getValidConnectionResponse);
		intentMap.put(IntentName.CREATE_BANF, IntentHandler::getSAPOrderResponse);
		intentMap.put(IntentName.DEFAULT, this::getNoValidIntentResponse);
	}

	@Override
	public Function<ConversationInformation, SpeechletResponse> get(String intentName) {
		intentThatChanged = intentName;
		if (intentMap.containsKey(intentName)) {
			return intentMap.get(intentName);
		}
		return intentMap.get(IntentName.DEFAULT);
	}

	private SpeechletResponse getValidConnectionResponse(final ConversationInformation conversationInformation) {
		try {
			String speechText;
			String repromptText;
			conversationInformation.getAttribute("");
			if (new SAPConnector().testConnection()) {
				speechText = "Ich konnte die verbindung bestätigen. Was kann ich für dich anforern?";
				repromptText = "Wie kann ich dir helfen?";
			} else {
				speechText = "Ich konnte keine Verbindung herstellen. Bitte versuch es später noch mal";
				repromptText = "Wie kann ich dir helfen?";
			}
			return ResponseMaker.createAskResponse(speechText, repromptText);
		} catch (IOException e) {
			return ResponseMaker.createErrorResponse(e);
		}
	}

	private SpeechletResponse getHelpResponse(final ConversationInformation conversationInformation) {
		conversationInformation.getAttribute("");
		String speechText = "Wenn Sie eine Bestellanforderung erstellen wollen sagen sie ... Bestelle Bitte gefolgt vom Namen des Artikels";
		return ResponseMaker.createAskResponse(speechText, speechText);
	}

	private SpeechletResponse getPersonalCancelResponse(final ConversationInformation conversationInformation) {
		conversationInformation.setSessionArticle("");
		conversationInformation.setNextIntent(SessionAttributeName.NEXT_INTENT_DEFAULT);
		conversationInformation.setFilledFields(SessionAttributeName.NEXT_INTENT_DEFAULT);
		return ResponseMaker.createTellResponse("");
	}

	private SpeechletResponse getNoValidIntentResponse(final ConversationInformation conversationInformation) {
		conversationInformation.getAttribute("");
		String speechText = "Ich kann deinen Auftrag leider nicht verstehen. Wenn Sie eine Bestellunganforderung erstellen wollen sagen sie ... Bestelle Bitte gefolgt vom Namen des Artikels oder bitten sie um Hilfe";
		String repromptText = "Wenn Sie eine Bestellunganforderung erstellen wollen sagen sie ... Bestelle Bitte gefolgt vom Namen des Artikels";
		return ResponseMaker.createAskResponse(speechText, repromptText);
	}
}
