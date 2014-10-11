package com.example.projectchatter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
//import java.io.PrintWriter;
import java.net.Socket;
//import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ActivityNotFoundException;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity<MyTextToSpeech> extends Activity implements
		OnClickListener, OnInitListener {

	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
	static TextView marquee;
	static String connection_status = "Connect to a server through Settings screen...";
	static TextView speech_results;
	boolean onResumeCalled = false;
	static int i = 0;

	// global variables for text to speech
	// This is a test line. Purpose: to check which repository I'm pushing to (personal or ajay's)
	private int MY_DATA_CHECK_CODE = 0;
	private static TextToSpeech tts;
	static String latest_command;
	static String server_response;

	// ConnectToServer global variable
	public static ConnectToServer io;
	static SharedPreferences pref;
	static String server_string;
	static int port_number;
	static String client_identification = "";

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

		pref = getSharedPreferences("serverPrefs", Context.MODE_PRIVATE);
		if(!pref.contains("client_id")){
			pref.edit().putString("client_id", getKey()).commit();
		}
		
		
		// create async ConnectToServer object
		if(pref.contains("Directory")){
			server_string=pref.getString("Directory", "");
			port_number=Integer.parseInt(pref.getString("Port", ""));
			client_identification=pref.getString("client_id","");
			io = (ConnectToServer) new ConnectToServer().execute(new String[]{"t","does not matter"});
		}

	}

	@Override
	public void onResume() {
		super.onResume();

		marquee.setText(connection_status);

		// speech_results.append("\nappending...["+i+"]");
		// i++;

		onResumeCalled = true;
	}

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
			startVoiceRecognitionActivity();
			break;

		// if settings button pressed, open settings screen
		case R.id.button_settings:
			try {
				// Log.i("clicked settings", "clicked settings");
				Intent i2 = new Intent(this, Settings.class);
				startActivity(i2);
			} catch (ActivityNotFoundException e) {
				e.printStackTrace();
			}
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
			latest_command = matches.get(0);

			//String temp = "";
			// while(io.isconnected && (temp=io.getResult()).equals(""));
			// speech_results.append("\nServer:"+temp);

			//sayString(temp);
			
			io = (ConnectToServer) new ConnectToServer().execute(new String[] { "f", matches.get(0) });
		}

		// ** text to speech activity result
		if (requestCode == MY_DATA_CHECK_CODE) {
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				// success, create the TTS instance
				tts = new TextToSpeech(this, this);
			} else {
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

		if (client_identification.equals("")) {
			int random;
			for (int i = 0; i < 124; i++) {
				random = (int) (Math.random() * 126);
				if (random < 33)
					random = random + 33;
				key = key + (char) (random);
			}
			// Log.i("getkey", "returning key: "+key);
			return key;
		}
		// Log.i("getkey",
		// "returning client_identification: "+client_identification);
		return client_identification;

	}

	public static void sayString(String speak) {
		// String text = inputText.getText().toString();
		if (speak != null && speak.length() > 0) {
			// Toast.makeText(MainActivity.this, "Saying: " + speak,
			// Toast.LENGTH_LONG).show();
			tts.speak(speak, TextToSpeech.QUEUE_ADD, null);
		}
	}

	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			Toast.makeText(MainActivity.this,
					"Text-To-Speech engine is initialized", Toast.LENGTH_SHORT)
					.show();
		} else if (status == TextToSpeech.ERROR) {
			Toast.makeText(MainActivity.this,
					"Error occurred while initializing Text-To-Speech engine",
					Toast.LENGTH_SHORT).show();
		}
	}

	/*
	 * 
	 * AsyncTask class to connect to server
	 */
	public static class ConnectToServer extends
			AsyncTask<String, Integer, String> {
		static DataOutputStream DOS;
		static DataInputStream DIS;
		static boolean isConnected = false;
		static String outData, inData;
		static Socket socket;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		String tryconnect() {
			try {
				// Create a new connection to server_string
				isConnected = false;
				// create sockets
				Log.i("creating sockets", "creatings scokets");
				socket = new Socket(server_string, port_number);
				socket.setKeepAlive(true);
				socket.setSoTimeout(10000);
				DOS = new DataOutputStream(socket.getOutputStream());
				DIS = new DataInputStream(socket.getInputStream());
				StringBuilder build = new StringBuilder();
				Log.i("trying to connect to: ", server_string + "  "
						+ port_number);

				// send client id
				DOS.writeBytes(client_identification + "\n");
				// read in server response
				int c;

				try {
					while ((c = DIS.read()) != 0) {
						if (c == -1) {
							// socket closed, now break
							Log.i("read EOF", "read eof");
							connection_status = "Connection to server closed. Connection to server closed. Connection to server closed.";
							return "";
						}
						build.append((char) c);
					}

					server_response = build.toString();
					Log.i("SERVER_RESPONSE", server_response);
					connection_status = "Chatting with server " + server_string
							+ " on port " + port_number;
					isConnected = true;
					return server_response;

				} catch (SocketTimeoutException e) {
					Log.i("socket time out", "socket time out exception");
					connection_status = "Connection timed out! Connection timed out! Connection timed out!";
					isConnected = false;
					e.printStackTrace();
					return "";
				}

			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				// Log.i("unknownhostexception","unknownhostexception");
				isConnected = false;
				connection_status = "Server not found! Server not found! Server not found!";
				e.printStackTrace();
				return "";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// Log.i("ioexecption","ioexecption");
				isConnected = false;
				connection_status = "Server not found! Server not found! Server not found!";
				e.printStackTrace();
				return "";
			}

		}

		String sendData(String data) {
			try {
				// Create a new connection to server_string
				// create sockets
				Log.i("sending data", "sending data");
				DOS.writeBytes(data + "\n");

				StringBuilder build = new StringBuilder();
				int c;
				// read in server response
				try {
					while ((c = DIS.read()) != 0) {
						if (c == -1) {
							// socket closed, now break
							Log.i("read EOF", "read eof");
							connection_status = "Connection to server closed. Connection to server closed. Connection to server closed.";
							return "";
						}
						build.append((char) c);
					}

					server_response = build.toString();
					Log.i("SERVER_RESPONSE", server_response);
					isConnected = true;
					return server_response;

				} catch (SocketTimeoutException e) {
					Log.i("socket time out", "socket time out exception");
					connection_status = "Connection timed out! Connection timed out! Connection timed out!";
					isConnected = false;
					e.printStackTrace();
					return "";
				}

			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				// Log.i("unknownhostexception","unknownhostexception");
				isConnected = false;
				connection_status = "Server not found! Server not found! Server not found!";
				e.printStackTrace();
				return "";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// Log.i("ioexecption","ioexecption");
				isConnected = false;
				connection_status = "Server not found! Server not found! Server not found!";
				e.printStackTrace();
				return "";
			}

		}

		@Override
		protected String doInBackground(String... params) {
			// Log.i("ASYNC HERE I COME", "I AM ASYNC");

			// get params, whether you wana connect to a new server or send a
			// string
			boolean newserver;
			String toSend=params[1];
			if (params[0].equals("t")) {
				newserver = true;
			} else {
				newserver = false;
			}

			Log.i("Params", params[0] + " , " + params[1]);

			// APP THINKS ITS NOT CONNECTED, SO CONNECT!
			if (newserver) { // Want to connect to a new server
				return tryconnect();
			}else{
				if(isConnected){
					return sendData(toSend);
				}else{
					if(!tryconnect().equals("")){
						return sendData(toSend);
					}else{
						return "";
					}
				}
			}
			// SERVER THINKS IT IS CONNECTED, SEND DATA

			//return "All Done!";
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (!result.equals("")) {
				if(result.startsWith("[say]")){
					tts.speak(result.substring(5,result.length()), TextToSpeech.QUEUE_ADD, null);
					speech_results.append("\n" + result.substring(5,result.length()));
				}else{
					speech_results.append("\n" + result);
				}
			}
			/*
			 * if (isConnected){ connection_status =
			 * "Chatting with server "+server_string+" on port "+port_number;
			 * //Log.i("connection status from on post execute",
			 * connection_status); } else{ connection_status =
			 * "No connection to server!";
			 * //Log.i("connection status from on post execute",
			 * connection_status); }
			 */
			marquee.setText(connection_status);
		}
	}

	/*
	 * 
	 * Settings class to show settings screen
	 */
	public static class Settings extends Activity implements OnClickListener {

		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.settings);

			// set up click listener for the save button
			View save = findViewById(R.id.button_connect);
			save.setOnClickListener(this);

			View back = findViewById(R.id.button_back);
			back.setOnClickListener(this);

			// save persistent application data in SharedPreferences structure
			pref = getSharedPreferences("serverPrefs", Context.MODE_PRIVATE);

			// create edit-able text fields
			EditText directory = (EditText) findViewById(R.id.editDirectory);
			EditText port = (EditText) findViewById(R.id.editPort);

			// set text of the text fields
			directory.setText(pref.getString("Directory",
					""));
			port.setText(pref.getString("Port", ""));
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.button_connect:
				// create edit-able text fields
				EditText directory = (EditText) findViewById(R.id.editDirectory);
				EditText port = (EditText) findViewById(R.id.editPort);

				// save the strings from the text fields
				if (directory.getText().toString().length() != 0)
					pref.edit()
							.putString("Directory",
									directory.getText().toString()).commit();
				if (port.getText().toString().length() != 0)
					pref.edit().putString("Port", port.getText().toString())
							.commit();

				// connect to server
				server_string = directory.getText().toString();
				port_number = Integer.parseInt(port.getText().toString());
				client_identification = pref.getString("client_id",
						MainActivity.getKey
						());

				// Log.i("strings from settings",
				// "SERVER: "+server_string+" || PORT: "+port_number+" || KEY: "+client_identification);

				io = (ConnectToServer) new ConnectToServer().execute(new String[] { "t", "this doesn't matter" });

				finish();
				break;

			case R.id.button_back:
				// send user back to home screen, don't save input
				finish();
				break;
			}
		}

	}

}
