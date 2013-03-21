package com.example.projectchatter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        //setup Record button that goes to the record xml
        View record = findViewById(R.id.button_record);
        record.setOnClickListener(this);
        
        //setup Settings button that goes to the setting xml
        View settings = findViewById(R.id.button_settings);
        settings.setOnClickListener(this);
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
				// print to logcat
				Log.d("btn event", "record button pressed");
				break;
			
			// if settings button pressed, open settings screen
    		case R.id.button_settings:
    			Intent i2 = new Intent(this, Settings.class);
    			startActivity(i2);
    			break;
    		}
	}
	
}
