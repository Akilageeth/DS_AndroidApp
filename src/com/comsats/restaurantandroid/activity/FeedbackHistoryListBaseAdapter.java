package com.comsats.restaurantandroid.activity;

import java.util.ArrayList;

import com.comsats.restaurantandroid.FeedbackHistory;
import com.comsats.restaurantandroid.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

public class FeedbackHistoryListBaseAdapter extends BaseAdapter {
	private static ArrayList<FeedbackHistory> itemDetailsArrayList;
	
	
	private LayoutInflater l_Inflater;

	public FeedbackHistoryListBaseAdapter(Context context, ArrayList<FeedbackHistory> results) {
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
			convertView = l_Inflater.inflate(R.layout.feedbackhistorylist, null);
			holder = new ViewHolder();
			holder.feedbackDate = (TextView) convertView.findViewById(R.id.feedbackDate);
			holder.feedbackRating = (RatingBar) convertView.findViewById(R.id.feedbackRating);
			holder.feedbackremarks = (TextView) convertView.findViewById(R.id.feedbackremarks);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.feedbackDate.setText(itemDetailsArrayList.get(position).getFeedbackDate());
		holder.feedbackRating.setEnabled(false);
		holder.feedbackRating.setRating(Float.parseFloat(String.valueOf((itemDetailsArrayList.get(position).getFeedbackRating())/20)));
		holder.feedbackremarks.setText(itemDetailsArrayList.get(position).getFeedbackRemarks());


		return convertView;
	}

	static class ViewHolder {
		TextView feedbackDate;
		RatingBar feedbackRating;
		TextView feedbackremarks;
	}
	

	
}
