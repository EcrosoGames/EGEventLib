package ecrosogames.eventlib.testing.examples.example0;

import java.util.Scanner;

import ecrosogames.eventlib.main.EventManager;

public class UserInputManager {

	private boolean isRunning = true;
	private Scanner in = new Scanner(System.in);

	public void start() {
		System.out.println("Please enter some text");
		while (isRunning) {
			System.out.print("> ");
			String text = in.nextLine();

			long lastMillis = System.currentTimeMillis();
			EventManager.call(event -> {
				System.out.println(System.currentTimeMillis() - lastMillis);
				if (event.getText().equalsIgnoreCase("exit")) exit();
				
				System.out.println(event.getText());
			}, UserInputEvent.class, text);
		}
	}

	public boolean isRunning() {
		return true;
	}

	public void exit() {
		System.out.println("\nThank you for participating.");
		isRunning = false;
		System.exit(0);
	}
}
