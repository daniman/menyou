package edu.mit.menyou;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.signpost.http.HttpResponse;

public class Home extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		Parse.initialize(this, "4EPEC8gdyy1UVP4yC0pRpfM30zpgGMGkoMdeu9p7", "1DxRG10TudyhJwAR4jildKVne8q3PjqNHVvpzIlY");
		
        final TextView tv1 = (TextView) findViewById(R.id.title1);
        final TextView tv2 = (TextView) findViewById(R.id.sub_title1);
        final Button button = (Button) findViewById(R.id.button_getStart);
        final Button button2 = (Button) findViewById(R.id.button2);
        
        // set up the font
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/bubblebo.ttf");
        tv1.setTypeface(type);
        tv2.setTypeface(type);
        button.setTypeface(type);
        button2.setTypeface(type);
        
        //Listening to button event
        button.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), GettingStarted.class);
                /*
                //Sending data to another Activity
                nextScreen.putExtra("name", inputName.getText().toString());
                nextScreen.putExtra("email", inputEmail.getText().toString());
 
                Log.e("n", inputName.getText()+"."+ inputEmail.getText());
 				*/
                startActivity(nextScreen);
                }
        });
        
        //Listening to button2 event
        button2.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), ScrollableStuff.class);
                
                startActivity(nextScreen);
                }
        });
     
        
        //////////////////////////////////////////////////////////////////////
        // test that menyou is connected to Parse
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
        //////////////////////////////////////////////////////////////////////
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
