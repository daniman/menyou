package edu.mit.menyou.home;

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

public class HistoryMenuAdaptor extends ArrayAdapter<HistoryMenuItem> {

	private List<HistoryMenuItem> itemList;
	private Context context;

	public HistoryMenuAdaptor(List<HistoryMenuItem> itemList, Context ctx) {
		super(ctx, android.R.layout.simple_list_item_2, itemList);
		this.itemList = itemList;
		this.context = ctx;		
	}

	public int getCount() {
		if (itemList != null)
			return itemList.size();
		return 0;
	}

	public HistoryMenuItem getItem(int position) {
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
			v = inflater.inflate(R.layout.history_list_item, null);
		}

		HistoryMenuItem item = itemList.get(position);
		TextView text = (TextView) v.findViewById(R.id.historyRestName);
		text.setText(item.getRestName());
		
		TextView text2 = (TextView) v.findViewById(R.id.historyDishName);
		text2.setText(item.getDishName());
		
		TextView text3 = (TextView) v.findViewById(R.id.historyDishRating);
		text3.setText(item.getRating());

		TextView text4 = (TextView) v.findViewById(R.id.historyDishDescription);
		text4.setText(item.getDescription());

		return v;

	}

	public List<HistoryMenuItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<HistoryMenuItem> itemList) {
		this.itemList = itemList;
	}
};