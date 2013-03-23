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
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(
	                socket.getInputStream()));
	        String inputLine, outputLine;
	        ChatterProtocol cp = new ChatterProtocol();
	        outputLine = cp.processInput(null);
	        out.println(outputLine);
	 
	        while ((inputLine = in.readLine()) != null) {
	             outputLine = cp.processInput(inputLine);
	             out.println(outputLine);
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
}
