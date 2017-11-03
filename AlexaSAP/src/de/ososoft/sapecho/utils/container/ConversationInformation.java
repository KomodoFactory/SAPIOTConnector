package de.ososoft.sapecho.utils.container;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;

public class ConversationInformation {
	private final Intent intent;
	private final Session session;

	public ConversationInformation(final IntentRequest request, final Session session) {
		this.intent = request.getIntent();
		this.session = session;
	}

	public String getIntent() {
		return intent.getName();
	}

	public Slot getSlot(String name) {
		return intent.getSlot(name);
	}

	public Slot getSlotArticel() {
		return intent.getSlot(SlotName.ARTIKEL);
	}

	public Slot getSlotQuantity() {
		return intent.getSlot(SlotName.QUANTITIY);
	}

	public Object getAttribute(String name) {
		return session.getAttribute(name);
	}

	public String getNextIntent() {
		return (String) session.getAttribute(SessionAttributeName.STATE);
	}

	public void setNextIntent(String intent) {
		session.setAttribute(SessionAttributeName.STATE, intent);
	}

	public String getSessionArticle() {
		return (String) session.getAttribute(SessionAttributeName.ARTICEL);
	}

	public void setSessionArticle(String article) {
		session.setAttribute(SessionAttributeName.ARTICEL, article);
	}

	public String getSessionQuantity() {
		return (String) session.getAttribute(SessionAttributeName.QUANTITY);
	}

	public void setSessionQuantity(String quantity) {
		session.setAttribute(SessionAttributeName.QUANTITY, quantity);
	}

	public String getFilledFields() {
		return (String) session.getAttribute(SessionAttributeName.FILLED_FIELDS);
	}

	public void setFilledFields(String fields) {
		session.setAttribute(SessionAttributeName.FILLED_FIELDS, fields);
	}
	
}
