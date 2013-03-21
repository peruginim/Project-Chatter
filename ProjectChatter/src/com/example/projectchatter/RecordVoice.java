package com.example.projectchatter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;


public class RecordVoice extends Activity {

	int content = R.layout.activity_main;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(content);

    }
	
	
	
    public void buttonHandler(View v) { 
        System.out.println("Button Clicked"); 

    }
    
}
