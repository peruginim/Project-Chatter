import java.net.*;
import java.util.*;
import java.io.*;

public class ChatterServer extends Thread{
	private Socket socket = null;

	public ChatterServer(Socket socket) {
		super("chatterServer");
		this.socket = socket;
	}

	private boolean checkKey(String key) throws Exception{ //true means that either that the server already has the key for that client or the server added the key, false means that the server doesn't have the key and the user declined the connection.
		File f = new File("keylist.txt");
		if(f.exists()){
			Scanner inFile = new Scanner(f);
			while(inFile.hasNextLine()){
				String compare=inFile.nextLine();
				if(compare.equals(key)){
					System.out.println("Key already exists");
					return true;
				}
			}
		}

		boolean b = confirm();
		if(b){
			PrintWriter outFile = new PrintWriter(new FileWriter("keylist.txt",true));
			outFile.println(key);
			outFile.close();
			System.out.println("Key added to file");
			return true;
		}
		else return false;

		//}
	
	}
	/*
	confirm()
		Allows the server to dictate whether it will allow a fresh connection. It also saves the key 
		for future reference.
	*/
	private boolean confirm(){
		Scanner in = new Scanner(System.in);
		System.out.println("Do you want to allow " + socket.getInetAddress() + " to connect? (y/n)");
		String line = in.nextLine();
		if(line.equals("y"))return true;
		else return false;
	}
	
	public void run(){

		try{
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String inputLine, outputLine;
			CustomProtocol cp = new CustomProtocol();

			String key = in.readLine();
			if(checkKey(key)){
				//Start IO
				outputLine = cp.processInput(null);
				System.out.println(outputLine);
				printout(out, outputLine);

				while ((inputLine = in.readLine()) != null) {
					outputLine = cp.processInput(inputLine);
					System.out.println(inputLine+" : "+ outputLine+"\n");
					printout(out, outputLine);
					if (outputLine.equals("Bye."))
						break;
				}
			}
			out.close();
			in.close();
			socket.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Use this instead of out.println, this way we can write multiple newlines to the client,
	//instead of the message being \n terminated, it is terminated by a 0
	private void printout(PrintWriter out, String string){
		out.print(string);
		out.write(0);
		out.flush();
	}
}
