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
		
		final EditText first_name = (EditText) findViewById(R.id.edit_first);
		final EditText last_name = (EditText) findViewById(R.id.edit_last);
		Button user_button = (Button) findViewById(R.id.user_button);

		final SharedPreferences prefs = this.getSharedPreferences("edu.mit.menyou", Context.MODE_PRIVATE);
		final String first = "edu.mit.menyou.first";
		final String last = "edu.mit.menyou.last";
		
		
        user_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	if(first_name.getText().toString()!=null && last_name.getText().toString()!=null){
            	prefs.edit().putString(first, first_name.getText().toString()).commit();
        		prefs.edit().putString(last, last_name.getText().toString()).commit();
        		
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
		getMenuInflater().inflate(R.menu.username, menu);
		return true;
	}

}
