package edu.mit.menyou;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Profile extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		getActionBar().setDisplayShowTitleEnabled(false);
		
		final ImageButton TasteButton = (ImageButton) findViewById(R.id.taste_button);
		final ImageButton MoodButton = (ImageButton) findViewById(R.id.mood_button);
		final ImageButton DietaryButton = (ImageButton) findViewById(R.id.dietary_button);
		final ImageButton FriendsButton = (ImageButton) findViewById(R.id.friends_button);
		final ImageButton HistoryButton = (ImageButton) findViewById(R.id.history_button);
		
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
