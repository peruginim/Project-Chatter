import java.io.*;
import java.lang.*;
import java.util.Scanner;


public class CustomProtocol{
<<<<<<< HEAD
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
  if(input.matches("[^\t\n]*(light)[^\n\t]*")) {
   TwoWaySerialComm tw = new TwoWaySerialComm();
   System.out.println("Something about lights?");
   //THe user refrenced lights

   if(input.matches("[^\n\t]*(are)[^\n\t]*")) {
    //Are the lights on or off?
    //tw.out.write("11111");
    System.out.println("Asking about?");
    if(input.matches("[^\n\t]*(on)[^\n\t]*")) {
     return "I'm not sure really...";

    }else if (input.matches("[^\t\n]*(off)[^\n\t]*")) {
     return "Can't say.";
    }

   }else if (input.matches("[^\t\n]*(turn)[^\n\t]*")) {
    //turn the lights on
    System.out.println("changing the lights about?");
    if(input.matches("[^\n\t]*(on)[^\n\t]*")) {
     try {
      tw.connect("/dev/tty.usbmodemfa131", "1");
     }catch (Exception e) {
      System.out.println("Serial Connection error\n");
     }

     return "I'm turning the lights ON!"; 
    }else if (input.matches("[^\t\n]*(of)[^\n\t]*")) {
     try {
      tw.connect("/dev/tty.usbmodemfa131", "2");
     }catch (Exception e) {
      System.out.println("Serial Connection error\n");
     }
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
=======
	String usage = "\n\t\tChatter Server Help Menu\nAccepted Inputs:\n\texit or quit\tThis will disconnect you from the server.\n\thelp\tBrings up the help menu.\n\tconnection\tReturns the address that you are connected to.";
	
	static ChatterServerMain server = new ChatterServerMain();
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
		if(input.matches("[^\t\n]*(light)[^\n\t]*")) {
			System.out.println("Something about lights?");
			//The user refrenced lights

			if(input.matches("[^\n\t]*(are)|(is)[^\n\t]*")) {
				//Are the lights on or off?
				System.out.println("Asking about?");
				if(input.matches("[^\n\t]*(on)[^\n\t]*")) {
					return "I'm not sure really...";
						
				}else if (input.matches("[^\t\n]*(off)[^\n\t]*")) {
					return "Can't say.";
				}
				
			}else if (input.matches("[^\t\n]*(turn)[^\n\t]*")) {
				//turn the lights on
				System.out.println("changing the lights about?");
				if(input.matches("[^\n\t]*(on)[^\n\t]*")) {
					return "I'm turning the lights ON!";	
				}else if (input.matches("[^\t\n]*(of)[^\n\t]*")) {
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
>>>>>>> 5b1063c081bcc7223e0ba9d0bd78a3520c43c773
}
