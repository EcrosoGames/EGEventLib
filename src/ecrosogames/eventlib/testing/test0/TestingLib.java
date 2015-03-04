package ecrosogames.eventlib.testing.test0;

import ecrosogames.eventlib.main.EventManager;

public class TestingLib {

	public TestingLib() throws Exception {
		EventManager.registerEventClass(TestingEvent.class);
//		EventManager.registerEventClass(TestingParameteredEvent.class);
		EventManager.registerEventListener(new TestingListener());
		
//		EventManager.call(TestingEvent.class);
		EventManager.call(() -> {
			/**
			 * CODE HERE TO DO IF EVENT ISN'T CANCELLED
			 */
		}, TestingParameteredEvent.class, "Testing This!");
	}

	public static void main(String... args) throws Exception {
		new TestingLib();
	}
}