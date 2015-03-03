package ecrosogames.eventlib.testing;

import ecrosogames.eventlib.main.EventHandler;
import ecrosogames.eventlib.main.EventListener;

public class TestingListener implements EventListener {

	@EventHandler
	public void test(TestingEvent event) {
		System.out.println("Event Successful!");
	}
	
	@EventHandler
	public void testParametered(TestingParameteredEvent event) {
		event.setCancelled(true);
	}
}
