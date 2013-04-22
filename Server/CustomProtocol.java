import java.io.*;
import java.lang.*;
import java.util.Scanner;


public class CustomProtocol{
	String usage = "\n\t\tChatter Server Help Menu\nAccepted Inputs:\n\t<exit/quit/:q>: This will disconnect you from the server.\n\t<help>: Brings up the help menu.\n\t<connection>: Returns the address that you are connected to.";
	
	static ChatterServerMain server = new ChatterServerMain();
	
	public String processInput(String input){
		

		if(input==null){
			return "Hello, you are chattin with chatter!";
		}

		if(input.equals("hello")){
			return "Hello there!";
		}

		if(input.equals("who are you")){
			return "I am the chatter server. \n You can send me commands and I will respond.";
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
		if(input.matches("[^\t\n]*(l|Li|Ig|Gh|Ht|T)[^\n\t]*")) {
			System.out.println("Something about lights?");
			//THe user refrenced lights

			if(input.matches("[^\n\t]*(a|Ar|Re|E)[^\n\t]*")) {
				//Are the lights on or off?
				System.out.println("Asking about?");
				if(input.matches("[^\n\t]*(o|On|N)[^\n\t]*")) {
					return "I'm not sure really...";
						
				}else if (input.matches("[^\t\n]*(o|Of|Ff|F)[^\n\t]*")) {
					return "Can't say.";
				}
				
			}else if (input.matches("[^\t\n]*(t|Tu|Ur|Rn|N)[^\n\t]*")) {
				//turn the lights on
				System.out.println("changing the lights about?");
				if(input.matches("[^\n\t]*(o|On|N)[^\n\t]*")) {
					return "I'm turning the lights ON!";	
				}else if (input.matches("[^\t\n]*(o|Of|Ff|F)[^\n\t]*")) {
					return "I'm turning the lights OFF!";
				}
				
			}else {
				//you mentioned the lights but waht about them?
			}
			
		}

		if(input.equals("exit") || input.equals("quit") || input.equals(":q")){
			return("Goodbye.");
		}
		if(input.equals("help")) {
			return usage;
		}
		if(input.equals("connection")) {
			return server.theToString();
		}
		return "Did not understand the command.";

	}
	public static void main(String args[]) {
		//ChatterServerMain server = new ChatterServerMain();
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
