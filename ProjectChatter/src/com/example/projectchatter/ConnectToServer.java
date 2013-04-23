package com.example.projectchatter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;


public class ConnectToServer extends Thread{

	int content = R.layout.settings;
	Socket socket;
	DataOutputStream DOS;
	DataInputStream DIS;
	String data="";
	boolean running=true;
	String server;
	int port;
	String clientid;
	
	public void sendData(String string){
		data=string;
	}
	
	public void close(){
		running=false;
	}
	
	public ConnectToServer(){
		server="sslab10.cs.purdue.edu";
		port=4444;
	}
	
	public ConnectToServer(String serv, int port, String clientid){
		this.port=port;
		server=serv;
		this.clientid = clientid;
	}
	
    public void run() {
        
        // create socket connection
		try {
			//Log.i("NEW CONNECT TO", "SERVER: "+server+" || PORT: "+port+" || KEY: "+clientid);
			socket = new Socket(server, port);
			socket.setKeepAlive(true);
			//Log.i("HELLO","GOODBYE");
			DOS = new DataOutputStream(socket.getOutputStream());
			DIS = new DataInputStream(socket.getInputStream());
			
			MainActivity.connection_status="Now connected to: "+server+"  on port: "+port;
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (socket != null && DOS != null){
			StringBuilder build=new StringBuilder();
			int c;
			try {
				socket.setSoTimeout(7000);
				DOS.writeBytes(clientid+"\n");
				
				while(running){
					if(!data.equals("")){
						DOS.writeBytes(data+"\n");
						//DOS.flush();
						data="";
						
						build.delete(0, build.capacity());
						build.trimToSize();
						build.ensureCapacity(20);
						
						while((c=DIS.read())!=0){
							build.append((char)c);
						}
						
						Log.i("READIN","THE STRING READ IN IS: "+build.toString());
						
						MainActivity.speech_results.append(build.toString()+"\n");
					}
				}
				
				DOS.close();
				socket.close();
				MainActivity.connection_status="No server connection!";
			} catch (Exception e){
				e.printStackTrace();
			}/*catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
		}
		
		
    }
    
}