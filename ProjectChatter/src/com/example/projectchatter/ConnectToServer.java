package com.example.projectchatter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;


public class ConnectToServer{

	int content = R.layout.settings;
	Socket socket;
	DataOutputStream DOS;
	
    public ConnectToServer() {
        
        // create socket connection
		try {
			socket = new Socket("sslab14.cs.purdue.edu", 4444);
			DOS = new DataOutputStream(socket.getOutputStream());;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (socket != null && DOS != null){
			try {
				DOS.writeBytes("SUPPPP\n");
				DOS.close();
				socket.close();
			}
			catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
    }
    
}