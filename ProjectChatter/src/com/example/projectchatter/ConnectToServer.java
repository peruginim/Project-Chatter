package com.example.projectchatter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;


public class ConnectToServer extends Activity {

	int content = R.layout.settings;
	Socket socket;
	DataOutputStream DOS;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // shows settings screen
        setContentView(content);
        
        
        // create socket connection
		try {
			socket = new Socket("sslab20.purdue.edu", 4444);
			DOS = new DataOutputStream(socket.getOutputStream());
			DOS.writeUTF("HELLO_WORLD");
			socket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
    }
    
}