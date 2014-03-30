package edu.mit.menyou.search;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.mit.menyou.Home;
import edu.mit.menyou.Profile;
import edu.mit.menyou.R;
import edu.mit.menyou.menu.RestaurantMenu;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class Search extends Activity implements LocationListener {

	private RestaurantAdapter adpt;
	private ListView lView;
	private LocationManager locationManager;
	private String provider;
	private TextView latitudeField;
	private TextView longitudeField;
	private AutoCompleteTextView search_input;
	//private ImageButton searchButton;
	private Location location;
	private LocationListener ctx = this;
	//private Button update;
	private String gps;
	private String network;
	private String passive;
	private Location oldLocation;
	private static final int TWO_MINUTES = 1000 * 60 * 2;


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		getActionBar().setDisplayShowTitleEnabled(false);
		
		System.out.println("RAWR-1");
		oldLocation=null;
		search_input = (AutoCompleteTextView) findViewById(R.id.search_input);
		//searchButton = (ImageButton) findViewById(R.id.search_button);
		//ImageButton updateButton = (ImageButton) findViewById(R.id.gps_button);
		//update = (Button) findViewById(R.id.update);
		latitudeField = (TextView) findViewById(R.id.latitude);
		longitudeField = (TextView) findViewById(R.id.longitude);

		
		System.out.println("RAWR-2");

		adpt = new RestaurantAdapter(new ArrayList<RestaurantObject>(), this);
		lView = (ListView) findViewById(R.id.restaurantListView);
		lView.setAdapter(adpt);

		System.out.println("RAWR-3");
		
		// Get the location manager
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		gps = LocationManager.GPS_PROVIDER;
		network = LocationManager.NETWORK_PROVIDER;
		passive = LocationManager.PASSIVE_PROVIDER;
		
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		location = locationManager.getLastKnownLocation(provider);
		//locationManager.requestLocationUpdates(provider, 0, 0, this);
		onLocationChanged(location);
		
		locationManager.requestLocationUpdates(provider, 0, 0, this);
		
		
		if(locationManager.getLastKnownLocation(passive)!=null){
			location = locationManager.getLastKnownLocation(passive);
			onLocationChanged(location);
			
		}
		

		List<String> all =locationManager.getAllProviders();
		//Toast.makeText(Search.this, String.valueOf(all), Toast.LENGTH_SHORT).show();

		/*
		if(all.contains(passive)){
			locationManager.requestLocationUpdates(passive, 0, 0, this);
		}
		if(all.contains(network)){
			locationManager.requestLocationUpdates(network, 0, 0, this);
		}
		if(all.contains(gps)){
			locationManager.requestLocationUpdates(network, 0, 0, this);
		}
		*/
		
		
		
		// Initialize the location fields
		if (location != null) {
			System.out.println("Provider " + provider + " has been selected.");
			String coords = String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude());
			//String location1 = "42.364270,-71.102991";
			System.out.println("LOCATION: " + coords);
			(new AsyncListViewLoader()).execute(coords);
		} 
		if (location == null){
			Toast.makeText(Search.this, "we couldn't find your location :( ", Toast.LENGTH_SHORT).show();
			latitudeField.setText("Location not available");
			longitudeField.setText("Location not available");
		}

		if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			String displayThis = "Please enable your GPS";
			Toast.makeText(Search.this, displayThis, Toast.LENGTH_SHORT).show();
		}
		
		
		/*
	searchButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View arg0) {

          if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
         String coords = String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude());
         //String location = "42.364270,-71.102991";
         System.out.println("LOCATION: " + coords);
             (new AsyncListViewLoader()).execute(coords);
             } else {
             String displayThis = "Please enable your GPS";
              Toast.makeText(Search.this, displayThis, Toast.LENGTH_SHORT).show();
             }
        }
    });
	updateButton.setClickable(true);
	 update.setOnClickListener(new View.OnClickListener() {
		public void onClick(View arg0) {
			String displayThis = "Please enable your GPS";
            Toast.makeText(Search.this, displayThis, Toast.LENGTH_SHORT).show();
		}
	});
		 */

	}

	////////////////////////////////////////////
	@Override
	protected void onResume() {
		super.onResume();
	}

	/* Remove the locationlistener updates when Activity is paused */
	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {
		
		
		//if recieved location is more than 2 minutes newer than previous
		//often the first location is from PASSIVE and out dated
		if(oldLocation!=null && location!=null){
			// Check whether the new location fix is newer or older
			long timeDelta = location.getTime() - oldLocation.getTime();
			boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;

			if(isSignificantlyNewer){
				oldLocation=location;
				locationManager.removeUpdates(ctx);
				Toast.makeText(Search.this, "recieved a newer location", Toast.LENGTH_SHORT).show();
			}
		}

		//if this is the first location received
		if(oldLocation==null && location!=null){
			Toast.makeText(Search.this, "recieved a location", Toast.LENGTH_SHORT).show();
			oldLocation=location;
			double lat = oldLocation.getLatitude();
			double lng = oldLocation.getLongitude();
			String latString=new DecimalFormat("#.#####").format(lat);
			String lngString=new DecimalFormat("#.#####").format(lng);
			latitudeField.setText("Latitude: "+latString);
			longitudeField.setText("Longitude: "+lngString);

			String coords = String.valueOf(oldLocation.getLatitude()) + "," + String.valueOf(oldLocation.getLongitude());
			
			System.out.println("RAWR HERE?");

			(new AsyncListViewLoader()).execute(coords);
		}
/*
		double lat = oldLocation.getLatitude();
		double lng = oldLocation.getLongitude();
		String latString=new DecimalFormat("#.#####").format(lat);
		String lngString=new DecimalFormat("#.#####").format(lng);
		latitudeField.setText("Latitude: "+latString);
		longitudeField.setText("Longitude: "+lngString);

		String coords = String.valueOf(oldLocation.getLatitude()) + "," + String.valueOf(oldLocation.getLongitude());
		
		System.out.println("RAWR HERE?");

		(new AsyncListViewLoader()).execute(coords);
		
		System.out.println("RAWR HERE");
		*/

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(this, "Enabled new provider " + provider,
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(this, "Disabled provider " + provider,
				Toast.LENGTH_SHORT).show();
	}
	////////////////////////////////////////////


	private class AsyncListViewLoader extends AsyncTask<String, Void, List<RestaurantObject>> {
		private final ProgressDialog dialog = new ProgressDialog(Search.this);
		private final String BASE_URL = "http://api.locu.com/v1_0/venue/search/?api_key=1a75b559bec4e3c7b00f0cc06d17356705599303";
		private String request_url;

		@Override
		protected void onPostExecute(final List<RestaurantObject> result) {	
			super.onPostExecute(result);
			dialog.dismiss();
			adpt.setItemList(result);
			adpt.notifyDataSetChanged();

			lView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Intent intent = new Intent(Search.this, RestaurantMenu.class);
					intent.putExtra("restID",result.get(position).getId());
					intent.putExtra("restName",result.get(position).getName());
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
		protected List<RestaurantObject> doInBackground(String... params) {
			JSONObject json = null;
			List<RestaurantObject> result = new ArrayList<RestaurantObject>();
			request_url = BASE_URL + "&location=" + params[0] + "&radius=450&category=restaurant";

			try {
				//JSONArray restaurants = json.getJSONArray("objects");
				//for (int i=0; i<restaurants.length() ;i++) {
				//JSONObject rest = restaurants.getJSONObject(i);
				//System.out.println(rest.get("name"));
				//local.add(rest.getString("name"));


				//search_history.add(search_input.getText().toString());
				//Toast.makeText(Search.this, "searching", Toast.LENGTH_SHORT).show();
				//adapter.notifyDataSetChanged();
				//adapter1.notifyDataSetChanged();

				URL u = new URL(request_url);
				//read the server's output
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

		private RestaurantObject convertRestaurant(JSONObject obj) throws JSONException {
			String name = obj.getString("name");
			String description = obj.getString("street_address");
			String id = obj.getString("id");
			return new RestaurantObject(name, description, id);
		}

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
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
		case R.id.action_update_search:
			Intent nextScreen2 = new Intent(getApplicationContext(), Search.class);
			startActivity(nextScreen2);
			return true;
		case R.id.action_profile:
			Intent nextScreen1 = new Intent(getApplicationContext(), Profile.class);
			startActivity(nextScreen1);	           
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}