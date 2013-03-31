import java.net.*;
import java.io.*;

public class ChatterServer extends Thread{
	private Socket socket = null;
	
	public ChatterServer(Socket socket) {
		super("chatterServer");
		this.socket = socket;
	}
	public void run(){
			
		try{
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String inputLine, outputLine;
			ChatterProtocol cp = new ChatterProtocol();
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
			out.close();
			in.close();
			socket.close();
		}catch (IOException e) {
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
