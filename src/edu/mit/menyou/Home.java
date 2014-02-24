package edu.mit.menyou;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;

public class Home extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		Parse.initialize(this, "4EPEC8gdyy1UVP4yC0pRpfM30zpgGMGkoMdeu9p7", "1DxRG10TudyhJwAR4jildKVne8q3PjqNHVvpzIlY");
		
		final String hello = getResources().getString(R.string.hello_world);
        final String goodbye = getResources().getString(R.string.goodbye_world);
        final TextView tv = (TextView) findViewById(R.id.hello_text);
        final Button button = (Button) findViewById(R.id.button);
        final Button button2 = (Button) findViewById(R.id.button2);
        
        // test that menyou is connected to Parse
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
        
        // set up the font
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/bubblebo.ttf");
        tv.setTypeface(type);
        button.setTypeface(type);
        button2.setTypeface(type);
        
        button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(tv.getText().equals(hello)) {
					tv.setText(goodbye);
				} else {
					tv.setText(hello);
				}
			}
		});
        
        button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(tv.getText().equals(hello)) {
					tv.setText(goodbye);
				} else {
					tv.setText(hello);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
