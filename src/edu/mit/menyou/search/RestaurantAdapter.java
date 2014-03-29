package edu.mit.menyou.search;

import java.util.ArrayList;
import java.util.List;

import edu.mit.menyou.R;
import edu.mit.menyou.R.id;
import edu.mit.menyou.R.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RestaurantAdapter extends ArrayAdapter<RestaurantObject> {

	private List<RestaurantObject> itemList;
	private Context context;

	public RestaurantAdapter(ArrayList<RestaurantObject> arrayList, Context ctx) {
		super(ctx, android.R.layout.simple_list_item_1);
		this.itemList = arrayList;
		this.context = ctx;		
	}

	public int getCount() {
		if (itemList != null)
			return itemList.size();
		return 0;
	}

	public RestaurantObject getItem(int position) {
		if (itemList != null)
			return itemList.get(position);
		return null;
	}

	public long getItemId(int position) {
		if (itemList != null)
			return itemList.get(position).hashCode();
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.restaurant_list_item, null);
		}

		RestaurantObject r = itemList.get(position);
		TextView text = (TextView) v.findViewById(R.id.restaurantName);
		text.setText(r.getName());

		TextView text1 = (TextView) v.findViewById(R.id.restaurantDescription);
		text1.setText(r.getDescription());

		return v;

	}

	public List<RestaurantObject> getItemList() {
		return itemList;
	}

	public void setItemList(List<RestaurantObject> itemList) {
		this.itemList = itemList;
	}


}
