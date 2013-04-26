import java.net.*;
import java.io.*;

public class ChatterServerMain {
	static ServerSocket serverSocket = null;
	/*
	startServer(int port):
		will start a Chatter Server that will continually listen for input from the app to be sent 
		through the CustomProtocol
	*/
	public static void startServer(int port) throws IOException {
		boolean listening = true;
		try {
		    serverSocket = new ServerSocket(port);
		} catch (IOException e) {
		    System.out.println("Could not listen on port: "+port);
		    System.exit(1);
		}
		while(listening) {
			new ChatterServer(serverSocket.accept()).start();
		}
       serverSocket.close();
	}
	/* 
	theToString():
		Function to return the address of the server. Intended to tell the app user what they are 
		connected to.
	*/
	public String theToString() {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			String hostname = addr.getHostName();
			String connection = addr.getHostName();
			return connection;
		}catch(UnknownHostException e) {
			return "\nUnreal Address?\n";
		}
	}	
}
