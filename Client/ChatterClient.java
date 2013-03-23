import java.io.*;
import java.net.*;
 
public class ChatterClient {
    public static void main(String[] args) throws IOException {
    	boolean needHost = true;
        String  host = "pod5-5.cs.purdue.edu";
        Socket ccSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
	//Get the host name from the user
	//This well need to be edit when ported to the phone
	
	while(true) {
			
		if(needHost) {
			System.out.println("What Host would you like to connect to?");
			host = stdIn.readLine();
		}
		
		try {
		    ccSocket = new Socket(host, 4444);
		    out = new PrintWriter(ccSocket.getOutputStream(), true);
		    in = new BufferedReader(new InputStreamReader(ccSocket.getInputStream()));
		} catch (UnknownHostException e) {
		    System.err.println("\nDon't know about host: "+host+".");
		    System.err.println("Would you like to:\n[1] retry current host\n[2] enter new host\n[3] exit Chatter\n");
		    int reply = Integer.valueOf(stdIn.readLine());
		    switch(reply) {
			case 1:	
				needHost = false;
				break;
			case 2:
				needHost = true;	
				break;
			default:
				System.err.println("Bye.");
				System.exit(1);
		    }
		} catch (IOException e) {
		    System.err.println("Couldn't get I/O for the connection to: taranis.");
		    System.exit(1);
		}
		if(ccSocket != null) break;
	}
 
        String fromServer;
        String fromUser;
 
        while ((fromServer = in.readLine()) != null) {
            System.out.println("Server: " + fromServer);
            if (fromServer.equals("Bye."))
                break;
             
            fromUser = stdIn.readLine();
        if (fromUser != null) {
                System.out.println("Client: " + fromUser);
                out.println(fromUser);
        }
        }
 
        out.close();
        in.close();
        stdIn.close();
        ccSocket.close();
    }
}
