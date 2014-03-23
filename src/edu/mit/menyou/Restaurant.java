package edu.mit.menyou;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class Restaurant extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant);
		
		TextView name = (TextView) findViewById(R.id.rest_textView1);
		
		Intent i = getIntent();
        //Receiving the Data
        String restaurantName = i.getStringExtra("name");
        Log.e("Restaurant", restaurantName);
        // Displaying Received data
        name.setText("This page will show the menu for "+restaurantName);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.restaurant, menu);
		return true;
	}
}

