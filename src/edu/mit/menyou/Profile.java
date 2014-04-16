package edu.mit.menyou;

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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		getActionBar().setDisplayShowTitleEnabled(false);
		
		final ImageButton TasteButton = (ImageButton) findViewById(R.id.taste_button);
		final ImageButton MoodButton = (ImageButton) findViewById(R.id.mood_button);
		final ImageButton DietaryButton = (ImageButton) findViewById(R.id.dietary_button);
		final ImageButton FriendsButton = (ImageButton) findViewById(R.id.friends_button);
		final ImageButton HistoryButton = (ImageButton) findViewById(R.id.history_button);
		final Button ResetButton = (Button) findViewById(R.id.reset_button);
		final Button FeedbackButton = (Button) findViewById(R.id.feedback);
		final SharedPreferences prefs = this.getSharedPreferences("edu.mit.menyou", Context.MODE_PRIVATE);
		final String first = "edu.mit.menyou.first";
		final String last = "edu.mit.menyou.last";
		final String allergiesKey = "edu.mit.menyou.allergies";
		final String likes = "edu.mit.menyou.likes";
		
		String firstname = prefs.getString(first, "Ben");
		TextView first_name = (TextView) findViewById(R.id.username_first);
		first_name.setText(firstname);
		
		String lastname = prefs.getString(last, "");
		TextView last_name = (TextView) findViewById(R.id.username_last);
		last_name.setText(lastname);
	    
		if(firstname.length()>10){
			first_name.setTextSize(20);
		}
		if(firstname.length()>15){
			first_name.setTextSize(18);
		}
		if(firstname.length()>20){
			first_name.setTextSize(14);
		}
		if(lastname.length()>10){
			last_name.setTextSize(20);
		}
		if(lastname.length()>15){
			last_name.setTextSize(18);
		}
		if(lastname.length()>20){
			last_name.setTextSize(14);
		}
		TasteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent nextScreen = new Intent(getApplicationContext(), TasteProfile.class);
                startActivity(nextScreen);
                }
        });
		MoodButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent nextScreen = new Intent(getApplicationContext(), MyMood.class);
                startActivity(nextScreen);
                }
        });
		DietaryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent nextScreen = new Intent(getApplicationContext(), Dietary.class);
                startActivity(nextScreen);
                }
        });
		FriendsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent nextScreen = new Intent(getApplicationContext(), Friends.class);
                startActivity(nextScreen);
                }
        });
		HistoryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent nextScreen = new Intent(getApplicationContext(), History.class);
                startActivity(nextScreen);
                }
        });
		
		ResetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	String firstTime = "edu.mit.menyou.firstTime";
            	String first = "edu.mit.menyou.first";
            	String last = "edu.mit.menyou.last";
        		prefs.edit().putInt(firstTime, 0).commit();
        		prefs.edit().putString(first, null).commit();
        		prefs.edit().putString(last, null).commit();
        		prefs.edit().putString(allergiesKey, "").commit();
                Intent nextScreen = new Intent(getApplicationContext(), First.class);
                startActivity(nextScreen);
                }
        });  
		
		FeedbackButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent nextScreen = new Intent(getApplicationContext(), Feedback.class);
                startActivity(nextScreen);
                }
        });  
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
	        	Toast.makeText(Profile.this, "profile", Toast.LENGTH_SHORT).show();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}
