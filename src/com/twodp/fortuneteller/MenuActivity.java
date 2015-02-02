package com.twodp.fortuneteller;

import com.appnext.appnextsdk.Appnext;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;

public class MenuActivity extends Activity {

	private AudioManager audio;
	Appnext appnext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		audio = (AudioManager)getSystemService(this.AUDIO_SERVICE);
		appnext = new Appnext(this);
		appnext.addMoreGamesRight("93e2278c-1055-40c0-9751-50d06dc93c90");
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		appnext.setAppID("63c9222b-97db-43b4-8d37-adf4fdf77bb6");
		appnext.showBubble();
	};
	
	@Override
	public void onBackPressed() {
		if(appnext.isBubbleVisible()){
			appnext.hideBubble();
		}
		else{
			quitDialogue();
		}
	};
	
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

	
	public void playClick(View v){
		Intent intent = new Intent(MenuActivity.this, MainActivity.class);
		startActivity(intent);
	}
	
	public void helpClick(View v){
		AlertDialog.Builder playBox  = new AlertDialog.Builder(this);
		playBox.setMessage("Just ask me a yes or no question and shake for a response. " + 
				"The answer will appear in the crystal ball and I will say it. " +
				"If I do not say anything, don't be scared, sometimes I just don't feel like talking.");
		playBox.setTitle("What To Do");
		playBox.setPositiveButton("OK", null);
		playBox.setCancelable(true);
		playBox.create().show();
	}
	
	public void quitClick(View v){
		quitDialogue();
	}
	
	public void quitDialogue()
    {
        final CharSequence[] options = {"Yes", "No"};
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are You Sure?");

        builder.setItems(options, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int position)
            {
            	
            	if (position == 0)//Yes
               {
            	   System.gc();
                   dialog.dismiss();
                   System.exit(0);
               }
               else if (position == 1)//No
               {
            	   System.gc();
            	   dialog.dismiss();
               }
            
            } 
        });
        
        AlertDialog dlg = builder.create();
        dlg.show();
    }

}
