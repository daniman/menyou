package edu.mit.menyou.search;

import edu.mit.menyou.R;

import edu.mit.menyou.R.layout;
import edu.mit.menyou.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class SearchRadius extends Activity {
	
	private Button radius1;
	private Button radius2;
	private Button radius3;
	private Button radius4;
	private String radius;
	private SharedPreferences prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_radius);

		getActionBar().setDisplayShowTitleEnabled(false);
		
		radius1 = (Button) findViewById(R.id.radius1);
		radius2 = (Button) findViewById(R.id.radius2);
		radius3 = (Button) findViewById(R.id.radius3);
		radius4 = (Button) findViewById(R.id.radius4);
		
		
		prefs = this.getSharedPreferences("edu.mit.menyou", Context.MODE_PRIVATE);
		radius = "edu.mit.menyou.radius";
		
		
		radius1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	prefs.edit().putString(radius, "100").commit();
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), Search.class);
                startActivity(nextScreen);
                }
        });
		radius2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	prefs.edit().putString(radius, "200").commit();
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), Search.class);
                startActivity(nextScreen);
                }
        });
		radius3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	prefs.edit().putString(radius, "400").commit();
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), Search.class);
                startActivity(nextScreen);
                }
        });
		radius4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	prefs.edit().putString(radius, "800").commit();
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), Search.class);
                startActivity(nextScreen);
                }
        });
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.first, menu);
		return true;
	}

}
