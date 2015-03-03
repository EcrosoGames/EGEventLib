package ecrosogames.eventlib.testing;

import ecrosogames.eventlib.main.Event;

public class TestingParameteredEvent extends Event {

	public TestingParameteredEvent(String par0) {
		System.out.println(par0);
	}
}
