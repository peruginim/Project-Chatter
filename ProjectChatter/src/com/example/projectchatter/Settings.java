package com.example.projectchatter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class Settings extends Activity implements OnClickListener {

	int content = R.layout.settings;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// show the 'Settings' screen
		setContentView(content);
		
		// set up click listener for the save button
		View save = findViewById(R.id.button_connect);
		save.setOnClickListener(this);
		
		View back = findViewById(R.id.button_back);
		back.setOnClickListener(this);
		
		// save persistent application data in SharedPreferences structure
		SharedPreferences pref = getSharedPreferences("serverPrefs", Context.MODE_PRIVATE);

		// create edit-able text fields
		EditText directory = (EditText) findViewById(R.id.editDirectory);
		EditText port = (EditText) findViewById(R.id.editPort);
		
		// set text of the text fields
		directory.setText(pref.getString("Directory", "lore.cs.purdue.edu"));
		port.setText(pref.getString("Port", "3459"));
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.button_connect:
				// create edit-able text fields
				EditText directory = (EditText) findViewById(R.id.editDirectory);
				EditText port = (EditText) findViewById(R.id.editPort);
			
				// save the strings from the text fields
				SharedPreferences pref = getSharedPreferences("serverPrefs", Context.MODE_PRIVATE);
				if(directory.getText().toString().length() != 0) pref.edit().putString("Directory", directory.getText().toString()).commit();
				if(port.getText().toString().length() != 0) pref.edit().putString("Port", port.getText().toString()).commit();
			
				// connect to server
				String serv=directory.getText().toString();
				int p=Integer.parseInt(port.getText().toString());
				String clientid=pref.getString("client_id", "clientid");
				
				Log.i("NEW CONNECT TO", "SERVER: "+serv+" || PORT: "+p+" || KEY: "+clientid);
				
				// show the 'MainActivity' screen again
				MainActivity.io.close();
				MainActivity.io=new ConnectToServer(serv, p, clientid);
				MainActivity.io.start();
				
				Intent i = 	new Intent(this, MainActivity.class);
				startActivity(i);
				break;
			case R.id.button_back:
				// send user back to home screen, don't save input
				Intent j = new Intent(this, MainActivity.class);
				startActivity(j);
				break;
			}
	}	

}
