package com.example.projectchatter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;


public class ConnectToServer extends Thread{

	int content = R.layout.settings;
	Socket socket;
	DataOutputStream DOS;
	String data="";
	boolean running=true;
	
	public void sendData(String string){
		data=string;
	}
	
	
    public void run() {
        
        // create socket connection
		try {
			socket = new Socket("moore07.cs.purdue.edu", 4444);
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
				while(running){
					if(!data.equals("")){
						DOS.writeBytes(data+"\n");
						DOS.flush();
						data="";
					}
				}
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