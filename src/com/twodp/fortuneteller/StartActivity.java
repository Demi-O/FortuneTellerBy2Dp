package com.twodp.fortuneteller;

import java.util.Timer;
import java.util.TimerTask;

import android.media.AudioManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;

public class StartActivity extends Activity {

	private AudioManager audio;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		audio = (AudioManager)getSystemService(this.AUDIO_SERVICE);
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

		   public void run() {
			   Intent intent = new Intent(StartActivity.this, MenuActivity.class);
			   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			   startActivity(intent);
			   finish();
		   }
		}, 1500);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    switch (keyCode) {
	    case KeyEvent.KEYCODE_VOLUME_UP:
	        audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
	                AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
	        return true;
	    case KeyEvent.KEYCODE_VOLUME_DOWN:
	        audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
	                AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
	        return true;
	    default:
	        return super.onKeyDown(keyCode, event);
	    }
	}

}
