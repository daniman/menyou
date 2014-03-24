package edu.mit.menyou;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Username extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_username);

		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(false);
		
		final EditText first_name = (EditText) findViewById(R.id.edit_first);
		final EditText last_name = (EditText) findViewById(R.id.edit_last);
		Button user_button = (Button) findViewById(R.id.user_button);

		final SharedPreferences prefs = this.getSharedPreferences("edu.mit.menyou", Context.MODE_PRIVATE);
		final String first = "edu.mit.menyou.first";
		final String last = "edu.mit.menyou.last";
		
		
        user_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	String firstName=first_name.getText().toString();
            	String lastName=last_name.getText().toString();
            	firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
            	lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
            	
            	if(!firstName.matches("") && !lastName.matches("")){
            	prefs.edit().putString(first, firstName).commit();
        		prefs.edit().putString(last, lastName).commit();
        		
                Intent nextScreen = new Intent(getApplicationContext(), Home.class);
                startActivity(nextScreen);
                }
            	else{}
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
