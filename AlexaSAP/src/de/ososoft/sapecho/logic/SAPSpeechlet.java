package de.ososoft.sapecho.logic;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;

import de.ososoft.sapecho.container.Intents;
import de.ososoft.sapecho.container.Slots;

/**
 * This is the core of the Skill. It handles session management and Intents.
 */
public class SAPSpeechlet implements Speechlet {
	private IntentHandler intentHandler;

	/**
	 * Called during first initialization.
	 */
	@Override
	public void onSessionStarted(final SessionStartedRequest request, final Session session) throws SpeechletException {
		intentHandler = new IntentHandler();
	}

	/**
	 * Not sure what the difference between this and onSessionStarted is.
	 */
	@Override
	public SpeechletResponse onLaunch(final LaunchRequest request, final Session session) throws SpeechletException {
		return intentHandler.getWelcomeResponse();
	}

	/**
	 * This Method is called every time an intent is called. Handling intents is the
	 * core of any Alexa Skill.
	 */
	@Override
	public SpeechletResponse onIntent(final IntentRequest request, final Session session) throws SpeechletException {
		Intent intent = request.getIntent();
		String intentName = (intent != null) ? intent.getName() : "";

		switch (intentName) {
		case Intents.STOP:
			return intentHandler.getStopResponse();
		case Intents.CANCEL:
			return intentHandler.getCancelResponse();
		case Intents.HELP:
			return intentHandler.getHelpResponse();
		case Intents.SAPVALIDATION:
			return intentHandler.getValidateSAPConnectionResponse();
		case Intents.TESTORDER:
			return intentHandler.getTestOrderResponse();
		case Intents.CREATEBANF:
			return intentHandler.getSAPOrderResponse(request.getIntent().getSlot(Slots.ARTIKELS));
		default:
			return intentHandler.noValideIntent(request);
		}
	}

	/**
	 * Called when the Skill is terminated. Used for cleaning up.
	 */
	@Override
	public void onSessionEnded(final SessionEndedRequest request, final Session session) throws SpeechletException {
		//Only needed when the program unexpectedly ends. In that case we just don't do anything.
	}
}
