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
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class Search extends Activity {
	private RestaurantAdapter adpt;
	private ListView lView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
		final AutoCompleteTextView search_input = (AutoCompleteTextView) findViewById(R.id.search_input);
		final ImageButton searchButton = (ImageButton) findViewById(R.id.search_button);
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        
        adpt  = new RestaurantAdapter(new ArrayList<Restaurant>(), this);
        lView = (ListView) findViewById(R.id.restaurantListView);
        lView.setAdapter(adpt);
        
        LocationManager locMan=null;  
        LatLongListener locList;  
        locMan = (LocationManager)getSystemService(Context.LOCATION_SERVICE);  
        locList = new LatLongListener();  
        locMan.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, locList);  
         
               if (locMan.isProviderEnabled(LocationManager.GPS_PROVIDER)) {  
//                	   String location = LatLongListener.latitude + "," + LatLongListener.longitude;
            	   String location = "42.364270,-71.102991";
            	   System.out.println("LOCATION: " + location);
                	   (new AsyncListViewLoader()).execute(location);
                 } else {  
                	String displayThis = "Please enable your GPS";
                 	Toast.makeText(Search.this, displayThis, Toast.LENGTH_SHORT).show(); 
                 }  
    }


    private class AsyncListViewLoader extends AsyncTask<String, Void, List<Restaurant>> {
    	private final ProgressDialog dialog = new ProgressDialog(Search.this);
    	private final String BASE_URL = "http://api.locu.com/v1_0/venue/search/?api_key=1a75b559bec4e3c7b00f0cc06d17356705599303";
		private String request_url;
    	
		@Override
		protected void onPostExecute(final List<Restaurant> result) {			
			super.onPostExecute(result);
			dialog.dismiss();
			adpt.setItemList(result);
			adpt.notifyDataSetChanged();
			
			lView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(Search.this, MenuList.class);
                    intent.putExtra("restID",result.get(position).getId());
                    startActivity(intent);
                }
            });	
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
				URL u = new URL(request_url);
				 // read the server's output
				BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));
		        String serverOutput;
		        String output = "";
		        while ((serverOutput = in.readLine()) != null) {
		        	output = serverOutput;
		        }
		        json = new JSONObject(output);
				JSONArray arr = json.getJSONArray("objects");
				for (int i=0; i < arr.length(); i++) {
					JSONObject rest = arr.getJSONObject(i);
					result.add(convertRestaurant(rest));
				}
				return result;
			}
			catch(Throwable t) {
				System.out.println("Caught Error");
				t.printStackTrace();
			}
			return null;
		}
		
		private Restaurant convertRestaurant(JSONObject obj) throws JSONException {
			String name = obj.getString("name");
			String description = obj.getString("street_address");
			String id = obj.getString("id");
			return new Restaurant(name, description, id);
		}
    	
    }
    
}
