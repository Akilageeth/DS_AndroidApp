package com.comsats.restaurantandroid.activity;

import java.util.ArrayList;

import com.comsats.restaurantandroid.Customer;
import com.comsats.restaurantandroid.FeedbackHistory;
import com.comsats.restaurantandroid.R;
import com.comsats.restaurantandroid.util.URLConnectionReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class FeedbackHistoryTab extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedbackhistory);
		
		ArrayList<FeedbackHistory> feedbackHistoryList = prepareFeedbackHistoryList();
		
		 ListView feedbackhistorylistview = (ListView) findViewById(R.id.feedbackhistorylistview);
	        if(feedbackHistoryList.size() > 0)
	        {
	        feedbackhistorylistview.setAdapter(new FeedbackHistoryListBaseAdapter(this, feedbackHistoryList));
	        }
	        else
	        {
	        	Toast.makeText(FeedbackHistoryTab.this,"No History Found!", Toast.LENGTH_LONG).show();
	        }
	}


	  private ArrayList<FeedbackHistory> prepareFeedbackHistoryList() {
		// TODO Auto-generated method stub
		  ArrayList<FeedbackHistory> results = new ArrayList<FeedbackHistory>();
			
			String content = null;
			try {
				content = URLConnectionReader.getText(URLConnectionReader.getIP()+"get_feedback_history.jsp?customerID="+Customer.getUuid());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(content.length() > 0)
			{
			String [] allURLs = content.split(";");
			FeedbackHistory feedback_details;
	        for (int i=0; i<allURLs.length; i++) {
	        	feedback_details = new FeedbackHistory();
	        	String [] detail = allURLs[i].split(",");
	        	feedback_details.setFeedbackDate(detail[0]);
	        	feedback_details.setFeedbackRating(Integer.parseInt(detail[1]));
	        	feedback_details.setFeedbackRemarks(detail[2]);
	        	
	        	results.add(feedback_details);
	        }
			}
			else
			{
				
			}
	    	return results;
	}
	  
	  @Override
		public void onBackPressed() {
			// TODO Auto-generated method stub
			 finish();
				Intent ourIntent = new Intent(FeedbackHistoryTab.this, Home.class);
				FeedbackHistoryTab.this.startActivity(ourIntent);
		}
		
}
