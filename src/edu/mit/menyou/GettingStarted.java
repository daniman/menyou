package edu.mit.menyou;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GettingStarted extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_getting_started);
		
		final EditText inputFood = (EditText) findViewById(R.id.editFood);
		final Button button = (Button) findViewById(R.id.gs_button);
		
		//Listening to button event
        button.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), DisplayFavorites.class);
               
                //Sending data to another Activity
                nextScreen.putExtra("favfood",inputFood.getText().toString());
 
                Log.e("n", "Your favorite food is "+inputFood.getText()+" ?? That's so weird!");
 				
                startActivity(nextScreen);
                }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.getting_started, menu);
		return true;
	}

}
