package de.ososoft.sapecho.intent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.amazon.speech.speechlet.SpeechletResponse;

import de.ososoft.sapecho.intent.handler.ClosestArticleIntentHandler;
import de.ososoft.sapecho.intent.handler.DefaultIntentHandler;
import de.ososoft.sapecho.intent.handler.FieldIntentHandler;
import de.ososoft.sapecho.intent.handler.IntentHandler;
import de.ososoft.sapecho.intent.handler.NewOrderIntentHandler;
import de.ososoft.sapecho.intent.handler.RepeatIntentHandler;
import de.ososoft.sapecho.utils.container.ConversationInformation;
import de.ososoft.sapecho.utils.container.SessionAttributeName;

public final class IntentHandlerProvider {
	protected static final Map<String, IntentHandler> INTENT_HANDLER_MAP = new HashMap<>();

	static {
		INTENT_HANDLER_MAP.put(SessionAttributeName.NEXT_INTENT_DEFAULT, new DefaultIntentHandler());
		INTENT_HANDLER_MAP.put(SessionAttributeName.NEXT_INTENT_MATCH_ARTICLE, new ClosestArticleIntentHandler());
		INTENT_HANDLER_MAP.put(SessionAttributeName.NEXT_INTENT_REPEAT, new RepeatIntentHandler());
		INTENT_HANDLER_MAP.put(SessionAttributeName.NEXT_INTENT_FIELDS, new FieldIntentHandler());
		INTENT_HANDLER_MAP.put(SessionAttributeName.NEXT_INTENT_NEW_ORDER, new NewOrderIntentHandler());
	}

	public static Function<ConversationInformation, SpeechletResponse> get(String intentName, String state) {
		if (INTENT_HANDLER_MAP.containsKey(state)) {
			return INTENT_HANDLER_MAP.get(state).get(intentName);
		}
		return INTENT_HANDLER_MAP.get(SessionAttributeName.NEXT_INTENT_DEFAULT).get(intentName);
	}

	private IntentHandlerProvider() {
	}
}
