package com.comsats.restaurantandroid.activity;

import java.net.URLEncoder;

import com.comsats.restaurantandroid.Customer;
import com.comsats.restaurantandroid.R;
import com.comsats.restaurantandroid.util.URLConnectionReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

public class PostFeedback extends Activity{
	private RatingBar ratingBar;
	private EditText remarks;
	private ImageView btnSubmit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.postfeedback);
		
		addListenerOnButton();
	}
	

	public void addListenerOnButton() {
	 
		ratingBar = (RatingBar) findViewById(R.id.ratingBar);
		btnSubmit = (ImageView) findViewById(R.id.btnSubmit);
		remarks = (EditText) findViewById(R.id.remarks);
		//if click on me, then display the current rating value.
		btnSubmit.setOnClickListener(new OnClickListener() {
	 
			public void onClick(View v) {

				String uri = null;
				uri = URLEncoder.encode(remarks.getText().toString());
				String response = postFeedback(ratingBar.getRating(), uri.toString(), Customer.getUuid());
				if(response.equals("true"))
				{
				Toast.makeText(PostFeedback.this, "Feedback Added Successfully", Toast.LENGTH_SHORT).show();
				restartActivity();
				}
			}
	 
		});
	 
	  }
	  
		public String postFeedback(Float rating, String remarks, String customerID) {
			String result = null;
			String content = null;
			try {
				
				content = URLConnectionReader.getText(URLConnectionReader.getIP()
						+ "post_feedback.jsp?rating="+rating+"&remarks="+remarks+"&customerID="+customerID);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

						result = content;
			return result;
		}
		private void restartActivity()
		 {
			finish();
			Intent ourIntent = new Intent(PostFeedback.this, Feedback.class);
			PostFeedback.this.startActivity(ourIntent);
		 }
		 @Override
			public void onBackPressed() {
				// TODO Auto-generated method stub
				 finish();
					Intent ourIntent = new Intent(PostFeedback.this, Home.class);
					PostFeedback.this.startActivity(ourIntent);
			}
}
