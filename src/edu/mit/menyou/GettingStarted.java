package edu.mit.menyou;

import com.parse.ParseObject;

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
		final Button button1 = (Button) findViewById(R.id.gs_button);
		final Button button2 = (Button) findViewById(R.id.gs_button2);
		
		//Listening to button event
        button1.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), DisplayFavorites.class);
               
                //Sending data to another Activity
                nextScreen.putExtra("favfood",inputFood.getText().toString());
 
                Log.e("n", "Your favorite food is "+inputFood.getText()+" ?? That's so weird!");
 				
                startActivity(nextScreen);
                }
        });
        
        ///////////////////////////////////////////////////////////////////////////
        // mess with Danielle and the Parse server
        
        final ParseObject testObject = new ParseObject("MoreObject");
        
        button2.setOnClickListener(new View.OnClickListener() {
        	 
            public void onClick(View arg0) {
            	testObject.put("foo", inputFood.getText().toString());
                }
        });

        testObject.saveInBackground();
        ///////////////////////////////////////////////////////////////////////////
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.getting_started, menu);
		return true;
	}

}
