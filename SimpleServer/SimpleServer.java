import java.net.*;
import java.util.*;
import java.io.*;

public class SimpleServer{
	public static void main(String[] args){
		ServerSocket serverSocket = null;
		try{
			serverSocket = new ServerSocket(4444);
			ioblah(serverSocket.accept());
		}catch(IOException e){
			System.out.println("Could not listen on port: 4444");
			System.exit(1);
		}

	}

	public static void ioblah(Socket socket){
		try{
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}



