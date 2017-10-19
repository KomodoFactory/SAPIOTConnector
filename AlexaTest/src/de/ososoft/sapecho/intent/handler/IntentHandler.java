package de.ososoft.sapecho.intent.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.SpeechletResponse;

import de.ososoft.sapecho.utils.ResponseMaker;
import de.ososoft.sapecho.utils.container.ConversationInformation;
import de.ososoft.sapecho.utils.container.IntentName;

public abstract class IntentHandler {

	protected static final Map<String, Function<ConversationInformation, SpeechletResponse>> intentMap = new HashMap<>();

	private IntentHandler() {
	}

	static {
		intentMap.put(IntentName.AMAZON_HELP, IntentHandler::getStopResponse);
		intentMap.put(IntentName.AMAZON_CANCEL, IntentHandler::getStopResponse);
		intentMap.put(IntentName.AMAZON_STOP, IntentHandler::getStopResponse);
		intentMap.put(IntentName.REPEAT, IntentHandler::getWiederhohleResponse);
		intentMap.put(IntentName.DEFAULT, IntentHandler::getNoValidIntentResponse);
		intentMap.put(IntentName.LINK, IntentHandler::getLinkResponse);
	}

	public static Function<ConversationInformation, SpeechletResponse> get(String intentName) {
		if (intentMap.containsKey(intentName)) {
			return intentMap.get(intentName);
		}
		return intentMap.get(IntentName.DEFAULT);
	}

	public static SpeechletResponse getWelcomeResponse() {
		String speechText = "Was soll ich wiederhohlen?";
		return ResponseMaker.createAskResponse(speechText, speechText, "TEST Starts Now", "");
	}

	public static SpeechletResponse getWiederhohleResponse(final ConversationInformation conversationInformation) {
		String wiederhohlung = "";
		Slot artikel = conversationInformation.getSlotArticel();
		Slot zahl = conversationInformation.getSlotZahl();
		if (artikel != null) {
			wiederhohlung = artikel.getValue();
		} else if (zahl != null) {
			wiederhohlung = zahl.getValue();
		}
		String speechText = wiederhohlung;
		String repromptText = "Was soll ich wiederhohlen";
		String cardTitle = "RepeatIntent";
		String cardContent = wiederhohlung;
		return ResponseMaker.createAskResponse(speechText, repromptText, cardTitle, cardContent);
	}
	
	public static SpeechletResponse getLinkResponse(final ConversationInformation conversationInformation) {
		conversationInformation.getIntent();
		String speechText = "Ich habe einen Link geschickt";
		String cardTitle = "Link Test";
		String cardContent = "https://stackoverflow.com/questions/38780665/how-to-display-hyperlinks-in-echo-alexa-cards";
		return ResponseMaker.createTellResponse(speechText, cardTitle, cardContent);
	}

	private static SpeechletResponse getStopResponse(final ConversationInformation conversationInformation) {
		conversationInformation.getIntent();
		return ResponseMaker.createResponse("");
	}

	private static SpeechletResponse getNoValidIntentResponse(final ConversationInformation conversationInformation) {
		conversationInformation.getIntent();
		String speechText = "Ich kann deinen Auftrag leider nicht verstehen";
		String repromptText = "Bitte sage was ich wiederhohlen soll";
		return ResponseMaker.createAskResponse(speechText, repromptText);
	}
}