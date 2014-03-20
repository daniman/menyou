package edu.mit.menyou;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

class LocuTask extends AsyncTask<String, Integer, JSONObject> {
	
	private static final String BASE_URL = "http://api.locu.com/v1_0/venue/search/?api_key=1a75b559bec4e3c7b00f0cc06d17356705599303";
	private String request_url;
	private JSONObject jsonData;
	
	@Override
	protected JSONObject doInBackground(String... params) {
		JSONObject json = null;
		request_url = BASE_URL + "&location=" + params[0] + "&radius=" + params[1];
		try {
		URL url = new URL(request_url);
          System.out.println(url.toString());
          BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream())); // read the server's output
          String serverOutput;
          String output = "";
          while ((serverOutput = in.readLine()) != null) {
              output = serverOutput;
          }
          json = new JSONObject(output);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		jsonData = result;
		System.out.println("gone through onPostExecute");
//		System.out.println(jsonData);
		super.onPostExecute(result);
	}
	
	public JSONObject getSearchResults(){
		return jsonData;
	}
}
