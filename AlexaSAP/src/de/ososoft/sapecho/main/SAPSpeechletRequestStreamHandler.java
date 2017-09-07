package de.ososoft.sapecho.main;

import java.util.HashSet;
import java.util.Set;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import de.ososoft.sapecho.logic.SAPSpeechlet;

/**
 * This class is the Main Handler for an AWS Lambda function powering an Alexa
 * Skill. Set the handler field in the AWS Lambda console to the corresponding
 * Class.
 */
public final class SAPSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
	private static final Set<String> supportedApplicationIds = new HashSet<>();
	static {
		supportedApplicationIds.add("amzn1.ask.skill.14bd8d3c-63ee-4ff2-bd73-101a6ee05d04");
	}

	public SAPSpeechletRequestStreamHandler() {
		super(new SAPSpeechlet(), supportedApplicationIds);
	}

}
