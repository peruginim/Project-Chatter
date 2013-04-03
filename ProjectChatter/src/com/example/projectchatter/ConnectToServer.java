package com.example.projectchatter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.util.Log;


public class ConnectToServer extends Thread{

	int content = R.layout.settings;
	Socket socket;
	DataOutputStream DOS;
	String data="";
	boolean running=true;
	String server;
	int port;
	
	public void sendData(String string){
		data=string;
	}
	
	public void close(){
		running=false;
	}
	
	public ConnectToServer(){
		server="moore07.cs.purdue.edu";
		port=4444;
	}
	
	public ConnectToServer(String serv, int port){
		this.port=port;
		server=serv;
	}
	
    public void run() {
        
        // create socket connection
		try {
			Log.i("NEW CONNECT TO", "SERVER: "+server+" || PORT: "+port);
			socket = new Socket(server, port);
			socket.setKeepAlive(true);
			Log.i("HELLO","GOODBYE");
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
				DOS.writeBytes("Phone is connected!\n");
				while(running){
					if(!data.equals("")){
						DOS.writeBytes(data+"\n");
						//DOS.flush();
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