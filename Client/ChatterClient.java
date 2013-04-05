import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.StringBuilder;

public class ChatterClient {
	private static void printout(DataOutputStream out, byte[] output) throws IOException {
		int len = output.length;
		out.write(len);
		out.write(output, 0, len);
		out.flush();
	}
	public static void main(String[] args) throws Exception {
		boolean encryptMode = true;
		ChatterClientRSA encoder = new ChatterClientRSA();
		boolean needHost = true;
		String  host = "pod5-5.cs.purdue.edu";
		Socket ccSocket = null;
		//PrintWriter out = null;
		PrintWriter out = null;
		BufferedReader in = null;
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		//Get the host name from the user
		//This well need to be edit when ported to the phone

		//This establishes a connection
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
				System.err.println("Couldn't get I/O.");
				System.exit(1);
			}
			if(ccSocket != null) break;
		}
	
		System.out.println(ccSocket.getInetAddress()+" : "+ccSocket.getLocalAddress());

		//connection verified with server
		//generating string
//		File f = new File("/Users/nathanwisniewski/Desktop/Project-Chatter/Client/key.txt");
		
		//Tries to open file key.txt and read out a string
		String key=getKey();
		out.println(key);

		String fromServer;
		String fromUser;
		StringBuilder build=new StringBuilder();
		int c;

		while(true){
			build.delete(0, build.capacity());
			build.trimToSize();
			build.ensureCapacity(20);
			while ((c = in.read()) != -1 && c!=0){ //This is to make sure that the client reads until the buffer is empty. Otherwise syncing issues occures.
				build.append((char)c);  
			}
			fromServer=build.toString();

			System.out.println("Server: "+fromServer);

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

	static String getKey() throws Exception{
		String key="";
		File f = new File("key.txt");
		if(!f.exists()){
			System.out.println("DOESNTEXIST");
			int random;
			char randChar;
			for(int i = 0; i < 124; i++){ //Generates random string
				random = (int)(Math.random()*126);
				if(random < 33) random = random + 33;
				key = key+(char)(random);
			}

			PrintWriter outFile = new PrintWriter(new FileWriter("key.txt"));
			System.out.println(key);
			System.out.println("adding this to key");
			outFile.println(key);
			outFile.flush();
			outFile.close();
		}else{ //If keyfile exists, then get key from file
			Scanner inputFile = new Scanner(f);
			key = inputFile.nextLine();
			System.out.println(key);
		}
		return key;

	}
}
