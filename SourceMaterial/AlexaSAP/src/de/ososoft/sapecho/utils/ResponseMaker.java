package de.ososoft.sapecho.utils;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

public final class ResponseMaker {

	private static PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
	private static PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
	private static Reprompt reprompt = new Reprompt();
	private static SimpleCard simpleCard = new SimpleCard();

	private ResponseMaker() {
	}

	/**
	 * Creates a TellIntent based on the given Parameters. Use this when you need to
	 * tell the User something and don't expect a follow up.
	 */
	public static SpeechletResponse createTellResponse(String speechText, String cardTitle, String cardContent) {
		outputSpeech.setText(speechText);
		simpleCard.setTitle(cardTitle);
		simpleCard.setContent(cardContent);
		return SpeechletResponse.newTellResponse(outputSpeech, simpleCard);
	}

	/**
	 * Creates an AskIntent based on the given Parameters. Use this when you need to
	 * ask the User something. Reprompt Text will be repeated until Answer is given
	 * or Program Terminates.
	 */
	public static SpeechletResponse createAskResponse(String speechText, String repromptText, String cardTitle,
			String cardContent) {
		outputSpeech.setText(speechText);
		repromptSpeech.setText(repromptText);
		reprompt.setOutputSpeech(repromptSpeech);
		simpleCard.setTitle(cardTitle);
		simpleCard.setContent(cardContent);
		return SpeechletResponse.newAskResponse(outputSpeech, reprompt, simpleCard);
	}

	/**
	 * Creates an AskIntent based on the given Parameters. Use this when you need to
	 * ask the User something but don't want to show a card. Reprompt Text will be
	 * repeated until Answer is given or Program Terminates.
	 */
	public static SpeechletResponse createAskResponse(String speechText, String repromptText) {
		outputSpeech.setText(speechText);
		repromptSpeech.setText(repromptText);
		reprompt.setOutputSpeech(repromptSpeech);
		return SpeechletResponse.newAskResponse(outputSpeech, reprompt);
	}

	/**
	 * Creates a TellIntent that Informs about one thing. Session is terminated
	 * afterwards.
	 */
	public static SpeechletResponse createTellResponse(String speechText) {
		outputSpeech.setText(speechText);
		return SpeechletResponse.newTellResponse(outputSpeech);
	}

	/**
	 * Creates a TellIntent that Informs about the given Error.Session is terminated
	 * afterwards.
	 */
	public static SpeechletResponse createErrorResponse(Exception e) {
		return createTellResponse("Es ist ein schwerer Fehler aufgetereten", "Schwerer Fehler aufgetreten",
				e.getMessage());
	}

}
