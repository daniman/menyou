package edu.mit.menyou.menu;

import java.util.List;

import edu.mit.menyou.R;
import edu.mit.menyou.R.id;
import edu.mit.menyou.R.layout;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RestaurantMenuAdapter extends ArrayAdapter<RestaurantMenuItem> {
	
	private int count=0;

	private List<RestaurantMenuItem> itemList;
	private Context context;

	public RestaurantMenuAdapter(List<RestaurantMenuItem> itemList, Context ctx) {
		super(ctx, android.R.layout.simple_list_item_1, itemList);
		this.itemList = itemList;
		this.context = ctx;		
	}

	public int getCount() {
		if (itemList != null)
			return itemList.size();
		return 0;
	}

	public RestaurantMenuItem getItem(int position) {
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
			v = inflater.inflate(R.layout.menu_list_item, null);
		}
		    ImageView imageView = (ImageView) v.findViewById(R.id.icon);
		    
		RestaurantMenuItem d = itemList.get(position);
		TextView text = (TextView) v.findViewById(R.id.dishName);
		text.setText(d.getName());

		TextView text1 = (TextView) v.findViewById(R.id.dishDescription);
		text1.setText(d.getDescription());
		
		TextView text4 = (TextView) v.findViewById(R.id.dishRank);
		text4.setText(String.valueOf(d.getRank()));
		
		TextView text2 = (TextView) v.findViewById(R.id.dishPrice);
		text2.setText("$"+d.getPrice());
		
		if(d.getName().toString().contains("-Allergies-")){
			text4.setText("");
			text2.setText("");
			text.setTextColor(Color.RED);
			
		}
		else if(!d.getName().toString().contains("-Allergies-")){
			text.setTextColor(Color.parseColor("#638e22"));
		}
		
		if (d.getRank()==-99){
		      imageView.setImageResource(R.drawable.logo_x);
		    } 
		else if(d.getRank()>102){
		      imageView.setImageResource(R.drawable.green);
		}
		else if(d.getRank()>98){
		      imageView.setImageResource(R.drawable.green_yellow);
		}
		else if(d.getRank()>94){
		      imageView.setImageResource(R.drawable.yellow);
		}
		else if(d.getRank()>89){
		      imageView.setImageResource(R.drawable.orange);
		}
		else if(d.getRank()<90){
		      imageView.setImageResource(R.drawable.red);
		}
		else{
			imageView.setImageResource(R.drawable.logo_x);		}
		
		
		return v;

	}

	public List<RestaurantMenuItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<RestaurantMenuItem> itemList) {
		this.itemList = itemList;
	}
};