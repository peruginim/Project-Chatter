import java.net.*;
import java.io.*;

public class ChatterServerMain {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		boolean listening = true;
		try {
		    serverSocket = new ServerSocket(4444);
		} catch (IOException e) {
		    System.out.println("Could not listen on port: 4444");
		    System.exit(1);
		}
		while(listening)
		new chatterServer(serverSocket.accept()).start();
       serverSocket.close();
	}
}
