package com.example.projectchatter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        //setup Record button that goes to the record xml
        View record = findViewById(R.id.button_record);
        //record.setOnClickListener(this);
        
        //setup Settings button that goes to the setting xml
        View settings = findViewById(R.id.button_settings);
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
				startVoiceRecognitionActivity();
				// print to logcat
				
				break;
			
			// if settings button pressed, open settings screen
    		case R.id.button_settings:
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


        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
