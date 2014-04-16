package edu.mit.menyou;

import com.parse.ParseObject;

import edu.mit.menyou.home.Home;
import edu.mit.menyou.search.Search;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class MyMood extends Activity {
	
	private SeekBar costBar;
	private SeekBar spiceBar;
	private SeekBar denseBar;
	private SeekBar discoverBar;
	private SeekBar healthBar;
	private Button moods;
	
	private int costInt;
	private int spiceInt;
	private int denseInt;
	private int discoverInt;
	private int healthInt;
	
	final static String costM = "edu.mit.menyou.cost";
	final static String spiceM = "edu.mit.menyou.spice";
	final static String healthM = "edu.mit.menyou.health";
	final static String denseM = "edu.mit.menyou.dense";
	final static String discoverM = "edu.mit.menyou.discover";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_mood);
		getActionBar().setDisplayShowTitleEnabled(false);
		
		final SharedPreferences prefs = this.getBaseContext().getSharedPreferences("edu.mit.menyou", Context.MODE_PRIVATE);
		
		moods = (Button) findViewById(R.id.moodsButton);
		costBar = (SeekBar) findViewById(R.id.cost_seekBar);
		spiceBar = (SeekBar) findViewById(R.id.spicy_seekBar);
		denseBar = (SeekBar) findViewById(R.id.dense_seekBar);
		discoverBar = (SeekBar) findViewById(R.id.discover_seekBar);
		healthBar = (SeekBar) findViewById(R.id.health_seekBar);
		
		costBar.setMax(39);
		spiceBar.setMax(39);
		denseBar.setMax(39);
		discoverBar.setMax(39);
		healthBar.setMax(39);
		
		costInt=prefs.getInt(costM, 0);
		spiceInt=prefs.getInt(spiceM, 0);
		denseInt=prefs.getInt(denseM, 0);
		discoverInt=prefs.getInt(discoverM, 0);
		healthInt=prefs.getInt(healthM, 0);
		
		costBar.setProgress(costInt);
		spiceBar.setProgress(spiceInt);
		denseBar.setProgress(denseInt);
		discoverBar.setProgress(discoverInt);
		healthBar.setProgress(healthInt);
		
		moods.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	prefs.edit().putInt(costM,costBar.getProgress()).commit();
            	prefs.edit().putInt(denseM,denseBar.getProgress()).commit();
            	prefs.edit().putInt(spiceM,spiceBar.getProgress()).commit();
            	prefs.edit().putInt(healthM,healthBar.getProgress()).commit();
            	prefs.edit().putInt(discoverM,discoverBar.getProgress()).commit();
            	
            	String first = "edu.mit.menyou.first";
        		String last = "edu.mit.menyou.last";
        		String number = "edu.mit.menyou.number";
        		String firstname = prefs.getString(first, "Ben");
        		String lastname = prefs.getString(last, "*no last name*");
        		String username = firstname+" "+lastname;
        		String mNumber = prefs.getString(number, "none");
        		String moodIntegers = "Cost:"+String.valueOf(costBar.getProgress())+" Dense:"+String.valueOf(denseBar.getProgress())+" Spice:"+String.valueOf(spiceBar.getProgress())+" Health:"+String.valueOf(healthBar.getProgress())+" Discover:"+String.valueOf(discoverBar.getProgress());
        		ParseObject Moods = new ParseObject("Moods");
        		Moods.put("username", username);
        		Moods.put("number", mNumber);
        		Moods.put("locationInApp","Profile edit");
        		Moods.put("moodIntegers", moodIntegers);
        		Moods.saveInBackground();
            	
            	Intent nextScreen = new Intent(getApplicationContext(), Home.class);
                startActivity(nextScreen);
                }
        });
		/*
		costBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener());{
            int progressChanged = 0;
 
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                progressChanged = progress;
            }
 
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
 
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MyMood.this,String.valueOf(costBar.getProgress()), 
                        Toast.LENGTH_SHORT).show();
            }
        }); */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_home:
	        	Intent nextScreen = new Intent(getApplicationContext(), Home.class);
                startActivity(nextScreen);
	            return true;
	        case R.id.action_search:
	        	Intent nextScreen1 = new Intent(getApplicationContext(), Search.class);
                startActivity(nextScreen1);
	            return true;
	        case R.id.action_profile:
	        	Intent nextScreen2 = new Intent(getApplicationContext(), Profile.class);
	        	startActivity(nextScreen2);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}
