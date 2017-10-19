package de.ososoft.sapecho.main;

import java.util.HashSet;
import java.util.Set;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

/**
 * This class is the Main Handler for an AWS Lambda function powering an Alexa
 * Skill.
 */
public final class SAPSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
	private static final Set<String> supportedApplicationIds = new HashSet<>();
	static {
		supportedApplicationIds.add("amzn1.ask.skill.4bf3bff7-b67a-4311-9b41-6446f14026b6");
	}

	public SAPSpeechletRequestStreamHandler() {
		super(new SAPSpeechlet(), supportedApplicationIds);
	}

}
