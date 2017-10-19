package de.ososoft.sapecho.intent;

public final class IntentName {
	public static final String AMAZON_HELP = "AMAZON.HelpIntent";
	public static final String AMAZON_CANCEL = "AMAZON.CancelIntent";
	public static final String AMAZON_STOP = "AMAZON.StopIntent";
	public static final String POSITIV_CONFIRMATION = "AMAZON.YesIntent";
	public static final String NEGATIV_CONFIRMATION = "AMAZON.NoIntent";
	public static final String SAP_CONNECTION_TEST = "SAPValidateIntent";
	public static final String CREATE_BANF = "SAPOrderStartIntent";
	public static final String FIELDS = "FillingFieldsIntent";
	public static final String REPEAT_ARTICLE = "RepeatArticleIntent";
	public static final String DEFAULT = "Default";

	private IntentName() {
	}
}
