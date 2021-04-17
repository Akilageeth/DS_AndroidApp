package com.comsats.restaurantandroid.activity;

import java.util.ArrayList;

import com.comsats.restaurantandroid.OrderHistory;
import com.comsats.restaurantandroid.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OrderHistoryListBaseAdapter extends BaseAdapter {
	private static ArrayList<OrderHistory> itemDetailsArrayList;
	
	
	private LayoutInflater l_Inflater;

	public OrderHistoryListBaseAdapter(Context context, ArrayList<OrderHistory> results) {
		itemDetailsArrayList = results;
		l_Inflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return itemDetailsArrayList.size();
	}

	public Object getItem(int position) {
		return itemDetailsArrayList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = l_Inflater.inflate(R.layout.orderhistorylist, null);
			holder = new ViewHolder();
			holder.orderNo = (TextView) convertView.findViewById(R.id.orderNo);
			holder.orderDate = (TextView) convertView.findViewById(R.id.orderDate);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.orderNo.setText(String.valueOf(itemDetailsArrayList.get(position).getOrderID()));
		holder.orderDate.setText(itemDetailsArrayList.get(position).getDateTime());


		return convertView;
	}

	static class ViewHolder {
		TextView orderNo;
		TextView orderDate;
	}
	

	
}
