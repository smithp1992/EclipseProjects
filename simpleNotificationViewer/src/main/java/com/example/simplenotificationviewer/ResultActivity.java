package com.example.simplenotificationviewer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends Activity {

	TextView helloWorld;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_activity);
		
		helloWorld = (TextView) findViewById(R.id.helloworld);
		
	}
}
