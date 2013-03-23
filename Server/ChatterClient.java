import java.io.*;
import java.net.*;
 
public class ChatterClient {
    public static void main(String[] args) throws IOException {
 
        Socket ccSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
 
        try {
            ccSocket = new Socket("pod4-3", 4444);
            out = new PrintWriter(ccSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(ccSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: taranis.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: taranis.");
            System.exit(1);
        }
 
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
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