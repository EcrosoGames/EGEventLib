package ecrosogames.eventlib.testing.examples.example0;

import ecrosogames.eventlib.main.EventHandler;
import ecrosogames.eventlib.main.EventListener;

public class Example0EventListener implements EventListener {

	@EventHandler
	public void userInput(UserInputEvent event) {
		if (event.getText().equals("LOL")) {
			System.out.println("No reason to laugh that hard...");
			event.setCancelled(true);
		}
		
		event.setText(event.getText().replace("?", "!"));
	}
}
