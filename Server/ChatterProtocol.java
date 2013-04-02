import java.net.*;
import java.util.Arrays;
import java.io.*;

public class ChatterProtocol {

	private static final int WAITING = 0;
	private static final int SENTCHAT = 1;
	private static final int SENTCLUE = 2;
	private static final int ANOTHER = 3;
	private static final int ENCRYPT = 4;

	private static final int CONFIRM = 2;


	private static final int NUMJOKES = 5;

	private int state = WAITING;
	private int currentJoke = 0;

	ChatterRSA decoder = new ChatterRSA();
	private String[] clues = { "Turnip", "Little Old Lady", "Atch", "Who", "Who"};

	private String[] answers = { "Turnip the heat, it's cold in here!",
		"I didn't know you could yodel!",
		"Bless you!",
		"Is there an owl in here?",
		"Is there an echo in here?" };

	public String processInput(byte[] theInput) throws Exception{
		
		String theOutput = null;
		String input = null;
		if(theInput != null) input = Arrays.toString(decoder.rsaDecrypt(theInput));
		if(state == WAITING) {
			theOutput = "Chatin' with Secret Chatter\n";
			state = SENTCHAT;
		} else if (state == SENTCHAT) {
			if(input.equals("Quit")) {
				theOutput = "Bye.";
			}else {
				theOutput = "Did you mean this?\n" + input;
				state = CONFIRM;
			}
		} else if (state == CONFIRM) {
			if (input.equals("y")) {
				theOutput = "Awesome!";
				state = WAITING;
			} else {
				theOutput = "Error!";
			}	
		}
		return theOutput;

	}

	/*

	public String processInput(String theInput) {
		String theOutput = null;

		if (state == WAITING) {
			theOutput = "Chatin' with Chatter!\nKnock Knock\n";
			state = SENTCHAT;
		} else if (state == SENTCHAT) {
			if (theInput.equalsIgnoreCase("Who's there?")) {
				theOutput = clues[currentJoke];
				state = SENTCLUE;
			}else if(theInput.equalsIgnoreCase("Can you decypt something?")) {
				theOutput = "Why yes I can.\nWhat would you like to encrypt?\n";
				state = ENCRYPT;
			} else {
				theOutput = "You're supposed to say \"Who's there?\"! " +
				"Try again. Knock! Knock!";
			}
		} else if (state == SENTCLUE) {
			if (theInput.equalsIgnoreCase(clues[currentJoke] + " who?")) {
				theOutput = answers[currentJoke] + " Want another? (y/n)";
				state = ANOTHER;
			} else {
				theOutput = "You're supposed to say \"" + 
				clues[currentJoke] + " who?\"" + "! Try again. Knock! Knock!";
				state = SENTCHAT;
			}
		} else if (state == ENCRYPT) {
			System.out.println(theInput.getBytes());
			state = WAITING;
				
		} else if (state == ANOTHER) {
			if (theInput.equalsIgnoreCase("y")) {
				theOutput = "Knock! Knock!";
				if (currentJoke == (NUMJOKES - 1)) {
					currentJoke = 0;
				} else {
					currentJoke++;
					state = SENTCHAT;
				}
			} else {
				theOutput = "Bye.";
				state = WAITING;
			}
		}
		return theOutput;
	}
	*/
}
