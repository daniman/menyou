package edu.mit.menyou;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;

public class Username extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_username);
		
		EditText first_name = (EditText) findViewById(R.id.edit_first);
		EditText last_name = (EditText) findViewById(R.id.edit_last);
		Button user_button = (Button) findViewById(R.id.user_button);
		user_button.setVisibility(TRIM_MEMORY_UI_HIDDEN);
 		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.username, menu);
		return true;
	}

}
