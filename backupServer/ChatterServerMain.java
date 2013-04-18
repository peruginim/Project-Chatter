import java.net.*;
import java.io.*;

public class ChatterServerMain {
	public static void startServer(int port) throws IOException {
		ServerSocket serverSocket = null;
		boolean listening = true;
		try {
		    serverSocket = new ServerSocket(port);
		} catch (IOException e) {
		    System.out.println("Could not listen on port: "+port);
		    System.exit(1);
		}
		while(listening)
		new ChatterServer(serverSocket.accept()).start();
       serverSocket.close();
	}
}
