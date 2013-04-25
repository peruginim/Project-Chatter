package com.example.projectchatter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.text.TextUtils.TruncateAt;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity<MyTextToSpeech> extends Activity implements OnClickListener, OnInitListener {

	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
	//static ConnectToServer io;
	static TextView marquee;
	static String connection_status = "Connect to a server through Settings screen...";
	static TextView speech_results;
	
	//** global variables for TTS
    private int MY_DATA_CHECK_CODE = 0;
    private TextToSpeech tts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		marquee = (TextView) findViewById(R.id.connection_status);
		marquee.setSelected(true);
		marquee.setEllipsize(TruncateAt.MARQUEE);
		marquee.setSingleLine(true);
		marquee.setText(connection_status);

		// setup Record button that goes to the record xml
		View record = findViewById(R.id.button_record);
		record.setBackgroundColor(Color.TRANSPARENT);
		record.setOnClickListener(this);

		// setup Settings button that goes to the setting xml
		View settings = findViewById(R.id.button_settings);
		settings.setBackgroundColor(Color.TRANSPARENT);
		settings.setOnClickListener(this);
		
		speech_results = (TextView) findViewById(R.id.textView1);

		PackageManager pm = getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities.size() != 0) {
			record.setOnClickListener(this);
		} else {
			record.setEnabled(false);
		}

		// initialize text to speech
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);


        // create async ConnectToServer object
        new ConnectToServer().execute();
        
        
	}
	
	
	/*
	@Override
	public void onResume(){
		String temp="";
		while (io.isconnected && (temp=io.getResult()).equals(""));
		speech_results.append(temp);
	}
	 */	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			// if record button pressed, add functionality...
			case R.id.button_record:
				// Start voice recording
				// io.sendData("You hit the record button");
				startVoiceRecognitionActivity();
				break;
	
			// if settings button pressed, open settings screen
			case R.id.button_settings:
				//io.sendData("You hit the settings button");
				Intent i2 = new Intent(this, Settings.class);
				startActivity(i2);
				break;
			}
	}

	
	private void startVoiceRecognitionActivity() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
				"Speech recognition demo");
		startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
	}

	
	/**
	 * Handle the results from the recognition activity.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE
				&& resultCode == RESULT_OK) {
			// Create an arraylist of speech results
			ArrayList<String> matches = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

			// Set textfield to first result
			speech_results.setMovementMethod(new ScrollingMovementMethod());
			speech_results.append("\n" + matches.get(0));
			//io.sendData(matches.get(0));
			
			String temp="";
			//while(io.isconnected && (temp=io.getResult()).equals(""));
			//speech_results.append("\nServer:"+temp);
			
			sayString(temp);
		}
		
		//** text to speech activity result
	    if (requestCode == MY_DATA_CHECK_CODE) {
	        if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
	            // success, create the TTS instance
	            tts = new TextToSpeech(this, this);
	        }
	        else {
	            // missing data, install it
	            Intent installIntent = new Intent();
	            installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
	            startActivity(installIntent);
	        }
	     }
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	

	static String getKey() {
		String key = "";
		int random;
		
		for (int i = 0; i < 124; i++) {
			random = (int) (Math.random() * 126);
			if (random < 33)
				random = random + 33;
			key = key + (char) (random);
		}
		return key;
	}

	
	public void sayString(String speak){
		//String text = inputText.getText().toString();
        if (speak!=null && speak.length()>0) {
         //Toast.makeText(MainActivity.this, "Saying: " + speak, Toast.LENGTH_LONG).show();
         tts.speak(speak, TextToSpeech.QUEUE_ADD, null);
        }		
	}

	
	public void onInit(int status) {       
	      if (status == TextToSpeech.SUCCESS) {
	        Toast.makeText(MainActivity.this, "Text-To-Speech engine is initialized", Toast.LENGTH_SHORT).show();
	      }
	      else if (status == TextToSpeech.ERROR) {
	        Toast.makeText(MainActivity.this, "Error occurred while initializing Text-To-Speech engine", Toast.LENGTH_SHORT).show();
	      }
	}
	
	
	/*
	 * 
	 * AsyncTask class to connect to server
	 *  
	 *  
	 */
	private class ConnectToServer extends AsyncTask<String, Integer, String> {
		Socket socket;
		DataOutputStream DOS;
		DataInputStream DIS;
		boolean isConnected = false;
		
		SharedPreferences pref = getSharedPreferences("serverPrefs", Context.MODE_PRIVATE);
		final String SERVER = pref.getString("Directory", "sslab10.cs.purdue.edu");
		final int PORT = Integer.parseInt(pref.getString("Port", "5555"));
		
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		    //displayProgressBar("Downloading...");
		}
		 
		   
		    
		@Override
		protected String doInBackground(String... params) {
			Log.i("ASYNC HERE I COME", "I AM COMING");
			
			try {
				socket = new Socket(SERVER, PORT);
				socket.setKeepAlive(true);
				DOS = new DataOutputStream(socket.getOutputStream());
				DIS = new DataInputStream(socket.getInputStream());
				isConnected = true;
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Log.i("conneted to: ", "server "+SERVER+" on port "+PORT);
			
			return "All Done!";
		}
		 
		   
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}
			 
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (isConnected){
				marquee.setText("Chatting with server "+SERVER+" on port "+PORT);
			}
			else{
				marquee.setText("No server connection!");
			}
		}
	}
	

	
}
