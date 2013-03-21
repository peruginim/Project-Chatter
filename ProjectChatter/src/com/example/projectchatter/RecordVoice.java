package com.example.projectchatter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


/* 
 * 1. collect voice data
 * 2. verify voice data with user
 * 3. connect with server
 * 4. send voice data to server
 * 
 * to do this we may need a new screen or at least more buttons on the home screen
 * 
 */

public class RecordVoice extends Activity {

	int content = R.layout.activity_main;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(content);

    }

    
}
