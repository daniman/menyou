package edu.mit.menyou;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class Search extends Activity {

	
    private static final List<String> search_history = new ArrayList<String>();
    private static final List<String> local = new ArrayList<String>();

  	
  	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		getActionBar().setDisplayShowTitleEnabled(false);
		
		search_history.add("pad thai");
		local.add("pad thai");
		ListView lv = (ListView) findViewById(R.id.restaurantListView);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, local);
		lv.setAdapter(adapter);
		
		final AutoCompleteTextView search_input = (AutoCompleteTextView) findViewById(R.id.search_input);
		final ImageButton searchButton = (ImageButton) findViewById(R.id.search_button);
		
		//final ImageButton gpsButton = (ImageButton) findViewById(R.id.gps_button);
		
		
		final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, search_history);
		search_input.setAdapter(adapter1);
	    
		//LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
		//Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		//double lon = location.getLongitude();
		//double lat = location.getLatitude();
		//String locate =String.valueOf(lon)+","+String.valueOf(lat) ;
		
		String location = "42.3598,-71.0921";
		String radius = "200";
        LocuTask task = new LocuTask();
        task.execute(location, radius);		
		
		//Listening to button event
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	search_history.add(search_input.getText().toString());            	
            	search_input.setText("");
            	Toast.makeText(Search.this, "searching", Toast.LENGTH_SHORT).show();
            	//adapter.notifyDataSetChanged();
            	//adapter1.notifyDataSetChanged();
            }
        });
      //Listening to button event
        /*
        gpsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	Toast.makeText(Search.this, "update with gps", Toast.LENGTH_SHORT).show();
            }
        });
        */
        
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		      public void onItemClick(AdapterView<?> adapter, View view, int position,long id) {
		    	
		    	TextView clickedView = (TextView) view;
		  		String restaurantName = clickedView.getText().toString();
		  		
		                //Starting a new Intent
		                Intent nextScreen = new Intent(getApplicationContext(), Restaurant.class);
		                //Sending data to another Activity
		                nextScreen.putExtra("name",restaurantName);
		                Log.e("n",String.valueOf(id));

		                startActivity(nextScreen);
		     }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_home:
	        	Intent nextScreen = new Intent(getApplicationContext(), Home.class);
                startActivity(nextScreen);
	            return true;
	        case R.id.action_search:
	        	Toast.makeText(Search.this, "search", Toast.LENGTH_SHORT).show();
	            return true;
	        case R.id.action_profile:
	        	Intent nextScreen1 = new Intent(getApplicationContext(), Profile.class);
                startActivity(nextScreen1);	           
                return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	
	
	
	class LocuTask extends AsyncTask<String, Integer, JSONObject> {
		
		private static final String BASE_URL = "http://api.locu.com/v1_0/venue/search/?api_key=1a75b559bec4e3c7b00f0cc06d17356705599303";
	private SimpleAdapter adpt;

    public void onCreate(Bundle savedInstanceState) {
    	
		final AutoCompleteTextView search_input = (AutoCompleteTextView) findViewById(R.id.search_input);
		final ImageButton searchButton = (ImageButton) findViewById(R.id.search_button);
//		final ImageButton gpsButton = (ImageButton) findViewById(R.id.gps_button);
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        
        adpt  = new SimpleAdapter(new ArrayList<Restaurant>(), this);
        ListView lView = (ListView) findViewById(R.id.restaurantListView);
        
        lView.setAdapter(adpt);
        
//        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
//        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        String displayThis;
//        if (!enabled) {
//        	displayThis = "Please enable your GPS";
//        	Toast.makeText(Search.this, displayThis, Toast.LENGTH_SHORT).show();
//        } else {
//        	// Exec async load task
//            String location = "42.3598,-71.0921";
//            (new AsyncListViewLoader()).execute(location);
//        }
        
                LocationManager locMan=null;  
                LatLongListener locList;  
                locMan = (LocationManager)getSystemService(Context.LOCATION_SERVICE);  
                locList = new LatLongListener();  
                locMan.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, locList);  
         
               if (locMan.isProviderEnabled(LocationManager.GPS_PROVIDER)) {  
                	   String location = LatLongListener.latitude + "," + LatLongListener.longitude;
                	   System.out.println("LOCATION: " + location);
                	   (new AsyncListViewLoader()).execute(location);
                 } else {  
                	String displayThis = "Please enable your GPS";
                 	Toast.makeText(Search.this, displayThis, Toast.LENGTH_SHORT).show(); 
                 }  
         
         
        
//		//Listening to button event
//      searchButton.setOnClickListener(new View.OnClickListener() {
//          public void onClick(View arg0) {
//          	search_history.add(search_input.getText().toString());
//          	Toast.makeText(Search.this, "searching", Toast.LENGTH_SHORT).show();
//          	adapter.notifyDataSetChanged();
//          	adapter1.notifyDataSetChanged();
//          }
//      });
      
//    //Listening to button event
//      gpsButton.setOnClickListener(new View.OnClickListener() {
//          public void onClick(View arg0) {
//          	
//          	LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
//              boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
//              String displayThis;
//              if (enabled) {
//              	displayThis = "GPS is enabled yayy";
//              } else {
//              	displayThis = "Please enable your GPS";
//              }
//          	Toast.makeText(Search.this, displayThis, Toast.LENGTH_SHORT).show();
//          }
//      });
    }


    private class AsyncListViewLoader extends AsyncTask<String, Void, List<Restaurant>> {
    	private final ProgressDialog dialog = new ProgressDialog(Search.this);
    	private final String BASE_URL = "http://api.locu.com/v1_0/venue/search/?api_key=1a75b559bec4e3c7b00f0cc06d17356705599303";
		private String request_url;
    	
		@Override
		protected void onPostExecute(List<Restaurant> result) {			
			super.onPostExecute(result);
			dialog.dismiss();
			adpt.setItemList(result);
			adpt.notifyDataSetChanged();
		}

		@Override
		protected void onPreExecute() {		
			super.onPreExecute();
			dialog.setMessage("Retrieving nearby restaurants...");
			dialog.show();			
		}

		@Override
		protected List<Restaurant> doInBackground(String... params) {
			JSONObject json = null;
			List<Restaurant> result = new ArrayList<Restaurant>();
			request_url = BASE_URL + "&location=" + params[0] + "&radius=300&category=restaurant";

			try {
				restaurants = jsonData.getJSONArray("objects");
				for (int i=0; i<restaurants.length() ;i++) {
					JSONObject rest = restaurants.getJSONObject(i);
					System.out.println(rest.get("name"));
					local.add(rest.getString("name"));
					
					
//					search_history.add(search_input.getText().toString());
//	            	Toast.makeText(Search.this, "searching", Toast.LENGTH_SHORT).show();
//	            	adapter.notifyDataSetChanged();
//	            	adapter1.notifyDataSetChanged();
					
				URL u = new URL(request_url);
				System.out.println(u.toString());

				BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream())); // read the server's output
		        String serverOutput;
		        
		        String output = "";
		        while ((serverOutput = in.readLine()) != null) {
		        	output = serverOutput;
		        }
		        json = new JSONObject(output);
				JSONArray arr = json.getJSONArray("objects");
				for (int n=0; n < arr.length(); n++) {
					JSONObject rest = arr.getJSONObject(n);
					result.add(convertRestaurant(arr.getJSONObject(n)));
				}
				return result;
			}
			catch(Throwable t) {
				t.printStackTrace();
			}
			return null;
		}
		
		private Restaurant convertRestaurant(JSONObject obj) throws JSONException {
			String name = obj.getString("name");
			String description = obj.getString("street_address");
			return new Restaurant(name, description);
		}
    	
    }
    
}
