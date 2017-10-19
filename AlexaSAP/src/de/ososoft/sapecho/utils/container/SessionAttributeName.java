package de.ososoft.sapecho.utils.container;

public final class SessionAttributeName {
	public static final String NEXT_INTENT = "NextIntent";
	public static final String FILLED_FIELDS = "FilledFields";
	public static final String ARTICEL = "Articel";
	public static final String QUANTITY = "Quantitiy";

	public static final String NEXT_INTENT_DEFAULT = "Default";
	public static final String NEXT_INTENT_NEW_ORDER = "NewOrder";
	public static final String NEXT_INTENT_CLOSEST_ARTICLE = "ClostestArticle";
	public static final String NEXT_INTENT_REPEAT = "Repeat";
	public static final String NEXT_INTENT_FIELDS = "FillingFields";

	public static final String FILLED_FIELDS_DEFAULT = "";
	public static final String FILLED_FIELDS_ARTICLE = "Article";
	public static final String FILLED_FIELDS_QUANTITIY = "Quantity";
	public static final String FILLED_FIELDS_ALL = "Article,Quantity";

	private SessionAttributeName() {
	}
}
