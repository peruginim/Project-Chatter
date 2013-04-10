package com.example.projectchatter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
	static ConnectToServer io;
	private static String client_identifier = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        //setup Record button that goes to the record xml 
        View record = findViewById(R.id.button_record);
        record.setBackgroundColor(Color.TRANSPARENT);
        record.setOnClickListener(this);
        
        //setup Settings button that goes to the setting xml
        View settings = findViewById(R.id.button_settings);
        settings.setBackgroundColor(Color.TRANSPARENT);
        settings.setOnClickListener(this);
        
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0) {
            record.setOnClickListener(this);
        } else {
            record.setEnabled(false);
            //record.setText("Recognizer not present");
        }
        
        Log.i("DEBUG", "io: "+io);
        
      
        
        
        if(io==null){
        	
        	
        	SharedPreferences pref = getSharedPreferences("serverPrefs", Context.MODE_PRIVATE);
        	
            if(!pref.contains("client_id")){
            	pref.edit().putString("client_id", getKey()).commit();
            }
            
        	Log.i("client id", pref.getString("client_id", "default"));
        	
        	String serv=pref.getString("Directory", "data.cs.purdue.edu");
			int p=Integer.parseInt(pref.getString("Port", "3456"));
			String clientid=pref.getString("client_id", "clientid");
			
        	io=new ConnectToServer(serv, p, clientid);
        	io.start();
        }
        
        Log.i("DEBUG", "io.isAlive(): "+io.isAlive());
 
        io.sendData("Main activity has been created");
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
		// determine which button is pressed
    	switch(v.getId()){
    	
    		// if record button pressed, add functionality...  
			case R.id.button_record:
				// Start voice recording
				io.sendData("You hit the record button");
				startVoiceRecognitionActivity();
				break;
			
			// if settings button pressed, open settings screen
    		case R.id.button_settings:
    			io.sendData("You hit the settings button");
    			Intent i2 = new Intent(this, Settings.class);
    			startActivity(i2);
    			break;
    		}
	}
	
	private void startVoiceRecognitionActivity() 
	{
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition demo");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }
	
	/**
     * Handle the results from the recognition activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) 
    {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) 
        {
        	// Create an arraylist of speech results
        	ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

        	// Set textfield to first result
        	TextView speech_results = (TextView)findViewById(R.id.textView1);
			speech_results.setText(matches.get(0));
			io.sendData("Voice match: "+matches.get(0));

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    
    
	static String getKey(){
		String key="";
		//File f = new File("key.txt");
		
		if( client_identifier.equals("") ){
			Log.i("cliend id", "DOES NOT EXIST");
			int random;
			char randChar;
			for(int i = 0; i < 124; i++){ //Generates random string
				random = (int)(Math.random()*126);
				if(random < 33) random = random + 33;
				key = key+(char)(random);
			}

			Log.i("the key", key);
			
		}
		return key;

	}
    
    
}
