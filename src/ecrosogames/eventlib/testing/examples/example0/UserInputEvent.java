package ecrosogames.eventlib.testing.examples.example0;

import ecrosogames.eventlib.main.Event;

public class UserInputEvent extends Event {

	private String text;

	public UserInputEvent(String text) {
		setText(text);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
