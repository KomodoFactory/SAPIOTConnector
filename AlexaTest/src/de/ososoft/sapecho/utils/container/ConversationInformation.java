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

	public Slot getSlotZahl() {
		return intent.getSlot(SlotName.NUMBER);
	}
	
	public Session getSession() {
		return session;
	}
}
