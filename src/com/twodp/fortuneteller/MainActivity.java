package com.twodp.fortuneteller;

import java.util.Random;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.appnext.appnextsdk.Appnext;
import com.twodp.fortuneteller.ShakeDetector.OnShakeListener;

public class MainActivity extends Activity {

	private CrystalBall crystalBall;
	private TextView answerLabel;
	private String[] answers; 
	private MediaPlayer[] sounds;
	private SensorManager sensorManager;
	private Sensor accelerometer;
	private ShakeDetector shakeDetector;
	private MediaPlayer playPredict;
	private AudioManager audio;
	Appnext appnext;
	
	private boolean isPlaying;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		crystalBall = new CrystalBall();
		answerLabel = (TextView) findViewById(R.id.textView1);
		answers = crystalBall.getAnswers();
		appnext = new Appnext(this);
		appnext.addMoreGamesRight("93e2278c-1055-40c0-9751-50d06dc93c90");
		
		isPlaying = false;
		sounds = new MediaPlayer[26];
		setSounds();
		playPredict = new MediaPlayer();
		
		audio = (AudioManager)getSystemService(this.AUDIO_SERVICE);
		
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    	accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    	shakeDetector = new ShakeDetector(new OnShakeListener() {
			
			@Override
			public void onShake() {
				if(!isPlaying){
					handleNewAnswer();
				}
			}
		});
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

	private void setSounds() {
		MediaPlayer zero = MediaPlayer.create(this, R.raw.itisscertain);
		sounds[0] = zero;
		
		MediaPlayer one = MediaPlayer.create(this, R.raw.itisdecidedlyso);
		sounds[1] = one;
		
		MediaPlayer two = MediaPlayer.create(this, R.raw.allsignssayyes);
		sounds[2] = two;
		
		MediaPlayer three = MediaPlayer.create(this, R.raw.myreplyisno);
		sounds[3] = three;
		
		MediaPlayer four = MediaPlayer.create(this, R.raw.itisdoubtul);
		sounds[4] = four;
		
		MediaPlayer five = MediaPlayer.create(this, R.raw.betternottotellyounow);
		sounds[5] = five;

		MediaPlayer six = MediaPlayer.create(this, R.raw.concentrateandaskagain);
		sounds[6] = six;

		MediaPlayer seven = MediaPlayer.create(this, R.raw.unabletoanswernow);
		sounds[7] = seven;

		MediaPlayer eight = MediaPlayer.create(this, R.raw.allsignssayno);
		sounds[8] = eight;

		MediaPlayer nine = MediaPlayer.create(this, R.raw.thisisabadidea);
		sounds[9] = nine;

		MediaPlayer ten = MediaPlayer.create(this, R.raw.thereisafiftyfiftychance);
		sounds[10] = ten;

		MediaPlayer eleven = MediaPlayer.create(this, R.raw.idoubtthat);
		sounds[11] = eleven;

		MediaPlayer twelve = MediaPlayer.create(this, R.raw.besttoforgetaboutit);
		sounds[12] = twelve;

		MediaPlayer thirteen = MediaPlayer.create(this, R.raw.youalreadyknowtheanswer);
		sounds[13] = thirteen;

		MediaPlayer fourteen = MediaPlayer.create(this, R.raw.itwillbeamiracle);
		sounds[14] = fourteen;

		MediaPlayer fifteen = MediaPlayer.create(this, R.raw.juststopthinkingaboutit);
		sounds[15] = fifteen;

		MediaPlayer sixteen = MediaPlayer.create(this, R.raw.itisnotgoingtohappen);
		sounds[16] = sixteen;

		MediaPlayer seventeen = MediaPlayer.create(this, R.raw.keepdreaming);
		sounds[17] = seventeen;

		MediaPlayer eighteen = MediaPlayer.create(this, R.raw.iampercentsure);
		sounds[18] = eighteen;

		MediaPlayer nineteen = MediaPlayer.create(this, R.raw.thatislaughable);
		sounds[19] = nineteen;
		
		MediaPlayer twenty = MediaPlayer.create(this, R.raw.besureofyourself);
		sounds[20] = twenty;

		MediaPlayer twentyone = MediaPlayer.create(this, R.raw.howshouldiknowthat);
		sounds[21] = twentyone;

		MediaPlayer twentytwo = MediaPlayer.create(this, R.raw.thechancesareslim);
		sounds[22] = twentytwo;

		MediaPlayer twentythree = MediaPlayer.create(this, R.raw.youmostlikelywill);
		sounds[23] = twentythree;

		MediaPlayer twentyfour = MediaPlayer.create(this, R.raw.askthepersontoyourright);
		sounds[24] = twentyfour;

		MediaPlayer twentyfive = MediaPlayer.create(this, R.raw.withoutadoubt);
		sounds[25] = twentyfive;
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
    public void onResume() {
    	super.onResume();
    	sensorManager.registerListener(shakeDetector, accelerometer, 
    			SensorManager.SENSOR_DELAY_UI);
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    	sensorManager.unregisterListener(shakeDetector);
    }
	
	private void animateAnswer() {
    	AlphaAnimation fadeInAnimation = new AlphaAnimation(0, 1);
    	fadeInAnimation.setDuration(1500);
    	fadeInAnimation.setFillAfter(true);
    	
    	answerLabel.setAnimation(fadeInAnimation);
    }
	
	private void handleNewAnswer() {
		Random random = new Random();
		int randomNumber = random.nextInt(answers.length);
		
		String prediction = answers[randomNumber];
		if(playPredict != null){
			playPredict = sounds[randomNumber];
			playPredict.start();
		}
			
		answerLabel.setText(prediction); 
		animateAnswer();		
	
		isPlaying = true;
	
		playPredict.setOnCompletionListener(new OnCompletionListener() {
		
			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.reset();
				isPlaying = false;
			}
		});
	}
	
}
