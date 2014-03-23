package edu.mit.menyou;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RestaurantAdapter extends ArrayAdapter<Restaurant> {

	private List<Restaurant> itemList;
	private Context context;

	public RestaurantAdapter(List<Restaurant> itemList, Context ctx) {
		super(ctx, android.R.layout.simple_list_item_1, itemList);
		this.itemList = itemList;
		this.context = ctx;		
	}

	public int getCount() {
		if (itemList != null)
			return itemList.size();
		return 0;
	}

	public Restaurant getItem(int position) {
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

		Restaurant r = itemList.get(position);
		TextView text = (TextView) v.findViewById(R.id.restaurantName);
		text.setText(r.getName());

		TextView text1 = (TextView) v.findViewById(R.id.restaurantDescription);
		text1.setText(r.getDescription());

		return v;

	}

	public List<Restaurant> getItemList() {
		return itemList;
	}

	public void setItemList(List<Restaurant> itemList) {
		this.itemList = itemList;
	}


}
