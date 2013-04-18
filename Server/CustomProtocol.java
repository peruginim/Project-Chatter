import java.io.*;
import java.lang.*;


public class CustomProtocol{
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
			//THe user refrenced lights

			if(input.matches("[^\n\t]*(a|Ar|Re|E)[^\n\t]*")) {
				//Are the lights on or off?
				if(input.matches("[^\n\t]*(o|On|N)[^\n\t]*")) {
						
				}else if (input.matches("[^\t\n]*(o|Of|Ff|F)[^\n\t]*")) {
					
				}
				
			}else if (input.matches("[^\t\n]*(t|Tu|Ur|Rn|N)|(m|Ma|Ak|Ke|E)[^\n\t]*")) {
				//turn the lights on
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
		return "Did not understand the command.";

	}
	public static void main(String args[]) {
		ChatterServerMain server = new ChatterServerMain();
		try {
			int port = 4444;
			server.startServer(port);
		}catch (Exception e) {
			System.out.print(e);
		}
	}
}
