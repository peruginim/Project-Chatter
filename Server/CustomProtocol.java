import java.io.*;
import java.lang.*;
import java.util.Scanner;


public class CustomProtocol{

	String usage = 	"\n\t\tChatter Server Help Menu\n" +
			"Accepted Inputs:\n" +
			"\texit or quit\tThis will disconnect you from the server.\n" +
			"\thelp\tBrings up the help menu.\n" +
			"\tconnection\tReturns the address that you are connected to.";

	static ChatterServerMain server = new ChatterServerMain();
	//static TwoWaySerialComm tw = new TwoWaySerialComm();
	static boolean light = false;
	/*
	   processInput(String input):
	   Takes input from the app and will send an appropriate response and/or complete an appropriate 
	   action.
	   Add your own ifs for cool things! Use the current examples to make your own! Regex is useful!
	   http://lmgtfy.com/?q=java+regex
	 */	
	public String processInput(String input){

		if(input==null){
			return "Hello, you are chattin with chatter!";
		}

		if(input.equals("hello")){
			return "[say]Hello there!";
		}

		if(input.equals("who are you")){
			return "[say]I am the chatter server. \n You can send me commands and I will respond.";
		}

		if(input.matches("read number [\\d]+")){
			input = input.replaceAll("[^\\d]","");//replaces any non-numeric characters with nothing leaving only the number
			System.out.println(input);
			try{
				int i=Integer.parseInt(input);
				return "The number you entered was " + i + ".";
			}catch(Exception e){
				return "Invalid parse: " + input;
			}
		}
		/*
		 * Checking or changin the status of the lights
		 * Possible make these into some sort of method for easy modding?
		 *
		 */
		if(input.matches("[^\t\n]*(light)[^\n\t]*")) {
			//The user refrenced lights

			if(input.matches("[^\n\t]*(are)[^\n\t]*")) {
				//Are the lights on or off?
				if(input.matches("[^\n\t]*(on)[^\n\t]*")) {
					if(light) return "Yes the light is on.\n";
					else return "No the light is off.\n";

				}else if (input.matches("[^\t\n]*(off)[^\n\t]*")) {
					if(!light) return "Yes the light is on.\n";
					else return "No the light is off.\n";
				}

			}else if (input.matches("[^\t\n]*(turn)[^\n\t]*")) {
				//turn the lights on
				if(input.matches("[^\n\t]*(on)[^\n\t]*")) {
					if(!light) {
						try {
							//tw.connect("/dev/tty.usbmodemfa131", "1");
							light = true;
						}catch (Exception e) {
							System.err.println("Serial Connection error\n");
							return "I can't find the light.\n";
						}
						return "The lights are now on.\n";
					}else return "The light is already on.\n";
				}else if (input.matches("[^\t\n]*(off)[^\n\t]*")) {
					if(light) {
						try {
							//tw.connect("/dev/tty.usbmodemfa131", "2");
							light = false;
						}catch (Exception e) {
							System.err.println("Serial Connection error\n");
							return "I can't find the light.\n";
						}
						return "The lights are now off.\n";
					}else return "The light is already off.\n";
				}
			}

		}

		if(input.equals("exit") || input.equals("quit") || input.equals(":q")){
			return("Goodbye.\n");
		}
		if(input.equals("help")) {
			return usage;
		}
		if(input.equals("connection")) {
			return server.theToString();
		}
		return "Did not understand the command.\n";

	}

	public static void main(String args[]) {
		try {
			int port = 4444;
			System.out.print("Run Server on port: ");
			Scanner in = new Scanner(System.in);
			port = in.nextInt();
			server.startServer(port);
		}catch (Exception e) {
			System.out.print(e);
		}
	}
}
