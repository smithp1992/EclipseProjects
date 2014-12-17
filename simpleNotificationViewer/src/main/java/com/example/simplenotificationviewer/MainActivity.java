package com.example.simplenotificationviewer;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	Button notifierBT;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		notifierBT = (Button) findViewById(R.id.notifierBT);
		
		notifierBT.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.notifierBT:
			createNotifier();
			break;

		default:
			break;
		}
	}
	
	
	private void createNotifier() {
		 
		
		Intent intent = new Intent(this, ResultActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_NO_CREATE);
		
		// build notification
		// the addAction re-use the same intent to keep the example short
		android.support.v4.app.NotificationCompat.Builder biulder  = new NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.bitcoin_notify)
        .setContentTitle("My notification")
        .setContentText("Hello World!")
        .setContentIntent(pIntent)
        .setAutoCancel(true)
        .addAction(R.drawable.bitcoin_notify, "Go", pIntent);
		
		Notification notify = biulder.build();
		 
		NotificationManager notificationManager = 
		  (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		notificationManager.notify(0, notify); 
	}

}
