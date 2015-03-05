package ecrosogames.eventlib.testing.examples.example0;

import ecrosogames.eventlib.main.EventManager;

public class EGEventLibExample0 {
	
	private UserInputManager userInput;

	public EGEventLibExample0() {
		EventManager.registerEventClass(UserInputEvent.class);
		EventManager.registerEventListener(new Example0EventListener());
		
		userInput = new UserInputManager();
		userInput.start();
	}
	
	public static void main(String... args) {
		new EGEventLibExample0();
	}
}
