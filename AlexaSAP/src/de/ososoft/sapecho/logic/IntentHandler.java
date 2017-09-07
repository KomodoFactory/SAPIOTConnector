package de.ososoft.sapecho.logic;

import java.io.IOException;
import java.util.Arrays;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.google.gson.Gson;

import de.ososoft.sapecho.container.Bestellanforderung;
import de.ososoft.sapecho.container.MaterialInformation;
import de.ososoft.sapecho.utils.connection.SAPConnector;
import de.ososoft.sapecho.utils.response.ResponseMaker;

public class IntentHandler {
	private static final Logger log = LoggerFactory.getLogger(IntentHandler.class);
	private String testeSAP = "Du kannst versuchen Teste bitte S A P zu mir zu sagen";

	private String speechText;
	private String repromptText;
	private String cardContent;
	private String cardTitle;

	protected SpeechletResponse getValidateSAPConnectionResponse() {
		try {
			SAPConnector connector = new SAPConnector();
			boolean stableConnection = connector.testConnection();

			if (stableConnection) {
				speechText = "Ich konnte die verbindung bestätigen. Was kann ich für dich bestellen?";
				repromptText = "Wie kann ich dir helfen?";
				cardContent = "Die verbindung wurde erfolgreich hergestellt.";
			} else {
				speechText = "Ich konnte keine Vebrindung herstellen. Bitte versuch es später noch mal";
				repromptText = "Wie kann ich dir helfen?";
				cardContent = "Die Verbindung kann nicht genutzt werden.";
			}
			cardTitle = "SAP - Verbindungstest";

			return ResponseMaker.createAskResponse(speechText, repromptText, cardTitle, cardContent);
		} catch (IOException e) {
			log.warn(Arrays.toString(e.getStackTrace()));
			return ResponseMaker.createErrorResponse(e);
		}
	}

	protected SpeechletResponse getTestOrderResponse() {
		try {
			Gson gson = new Gson();
			String json = gson.toJson(new Bestellanforderung.BestellanforderungBuilder("Test", 1).build());
			HttpResponse response = createAndSendRequest(json);

			if (response.getStatusLine().getStatusCode() == 201) {
				speechText = "Die Bestellanforderung wurde erfolgreich aufgesetzt.";
				cardContent = "Die Bestellanforderung wurde erfolgreich hergestellt.";
			} else {
				speechText = "Ich konnte keine Bestellanforderung erstellen. "
						+ response.getStatusLine().getReasonPhrase();
				cardContent = "Es ist ein Fehler aufgetreten: " + response.getStatusLine().getReasonPhrase();
			}
			cardTitle = "SAP - Testbestellung";

			return ResponseMaker.createTellResponse(speechText, cardTitle, cardContent);
		} catch (Exception e) {
			log.warn(Arrays.toString(e.getStackTrace()));
			return ResponseMaker.createErrorResponse(e);
		}
	}

	protected SpeechletResponse getSAPOrderResponse(Slot slot) {
		String artikel = slot.getValue();
		if (MaterialInformation.getInstance().validateMaterial(artikel)) {
			try {
				Gson gson = new Gson();
				String json = gson.toJson(new Bestellanforderung.BestellanforderungBuilder(artikel, 1).build());

				HttpResponse response = createAndSendRequest(json);
				if (response.getStatusLine().getStatusCode() == 201) {
					speechText = "Die Bestellanforderung für " + artikel + "wurde erfolgreich aufgesetzt.";
					cardContent = "Sie haben erfolgreich " + artikel + " bestellt.";
				} else {
					speechText = "Ich konnte keine Bestellanforderung erstellen. "
							+ response.getStatusLine().getReasonPhrase();
					cardContent = "Es ist ein Fehler aufgetreten: " + response.getStatusLine().getReasonPhrase();
				}
				cardTitle = "SAP - Bestellung";

				return ResponseMaker.createTellResponse(speechText, cardTitle, cardContent);

			} catch (Exception e) {
				log.warn(Arrays.toString(e.getStackTrace()));
				return ResponseMaker.createErrorResponse(e);
			}

		} else {
			speechText = "Ich habe verstanden " + artikel + ".. war das nah dran?";
			repromptText = testeSAP;
			return ResponseMaker.createSimpleAskResponse(speechText, repromptText);
		}
	}

	protected SpeechletResponse getWelcomeResponse() {
		speechText = "Guten Tag was kann ich für Sie bestellen";
		repromptText = testeSAP;
		cardTitle = "SAP - Start";
		cardContent = speechText;
		return ResponseMaker.createAskResponse(speechText, repromptText, cardTitle, cardContent);
	}

	protected SpeechletResponse getHelpResponse() {
		speechText = testeSAP;
		cardTitle = "SAP - Hilfe";
		cardContent = "Wenn sie nicht wissen was sie Sagen sollen, probieren sie \"Teste bitte SAP Für mich.\"";
		return ResponseMaker.createAskResponse(speechText, speechText, cardTitle, cardContent);
	}

	protected SpeechletResponse getCancelResponse() {
		return this.getWelcomeResponse();
	}

	protected SpeechletResponse getStopResponse() {
		return ResponseMaker.createSimpleResponse("");
	}

	protected SpeechletResponse noValideIntent(final IntentRequest request) {
		speechText = "Ich kann deinen Auftrag leider nicht verstehen.";
		repromptText = testeSAP;
		cardTitle = "SAP - Unbekannter Intent";
		cardContent = request.toString();
		return ResponseMaker.createAskResponse(speechText, repromptText, cardTitle, cardContent);
	}

	private HttpResponse createAndSendRequest(String json) throws IOException {
		SAPConnector connector = new SAPConnector();
		HttpPost request = connector.createPostRequest(json);
		return connector.executeRequest(request);
	}
}