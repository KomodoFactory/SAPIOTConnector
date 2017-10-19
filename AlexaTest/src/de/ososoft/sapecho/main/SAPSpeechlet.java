package de.ososoft.sapecho.main;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;

import de.ososoft.sapecho.intent.handler.IntentHandler;
import de.ososoft.sapecho.utils.container.ConversationInformation;
import de.ososoft.sapecho.utils.container.IntentName;

/**
 * This is the core of the Skill. It handles session management and Intents.
 */
public class SAPSpeechlet implements Speechlet {

	/**
	 * Called during first initialization of a Session.
	 */
	@Override
	public void onSessionStarted(final SessionStartedRequest request, final Session session) throws SpeechletException {
		//unneceserry
	}

	/**
	 * Called when the skill is launched.
	 */
	@Override
	public SpeechletResponse onLaunch(final LaunchRequest request, final Session session) throws SpeechletException {
		return IntentHandler.getWelcomeResponse();
	}

	/**
	 * This Method is called every time an intent is called. Handling intents is the
	 * core of any Alexa Skill.
	 */
	@Override
	public SpeechletResponse onIntent(final IntentRequest request, final Session session) throws SpeechletException {
		Intent intent = request.getIntent();
		String intentName = (intent != null) ? intent.getName() : IntentName.DEFAULT;
		return IntentHandler.get(intentName).apply(new ConversationInformation(request, session));
	}

	/**
	 * Called when the Skill is terminated. Used for cleaning up.
	 */
	@Override
	public void onSessionEnded(final SessionEndedRequest request, final Session session) throws SpeechletException {
		// Only needed when the program unexpectedly ends. In this case, we simple don't
		// do anything.
	}
}
