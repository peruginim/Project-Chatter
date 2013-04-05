import java.io.*;

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
		return "Did not understand the command.";

	}
}
