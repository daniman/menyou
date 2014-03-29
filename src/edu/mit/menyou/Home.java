package edu.mit.menyou;

import edu.mit.menyou.search.Search;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		SharedPreferences prefs = this.getSharedPreferences("edu.mit.menyou", Context.MODE_PRIVATE);
		String firstTime = "edu.mit.menyou.firstTime";
		String first = "edu.mit.menyou.first";
		
		// use a default value using new Date()
		int firstCheck = prefs.getInt(firstTime, 0);
		
		if(firstCheck==0){
			Intent nextScreen = new Intent(getApplicationContext(), First.class);
            startActivity(nextScreen);
		}
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		getActionBar().setDisplayShowTitleEnabled(false);
		
		final Button home_button = (Button) findViewById(R.id.home_button);
		TextView name = (TextView) findViewById(R.id.home_username);
		name.setText("Welcome "+prefs.getString(first, "Ben"));
		//Listening to button event
        home_button.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), Search.class);
                startActivity(nextScreen);
                }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu1) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu1);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_home:
	        	Toast.makeText(Home.this, "home", Toast.LENGTH_SHORT).show();
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

