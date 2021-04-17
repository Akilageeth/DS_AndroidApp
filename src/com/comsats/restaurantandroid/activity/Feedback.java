package com.comsats.restaurantandroid.activity;

import com.comsats.restaurantandroid.R;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class Feedback extends TabActivity{
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
		 
		RelativeLayout backBtn = (RelativeLayout) findViewById(R.id.backBtn);
        ImageView homeBtn = (ImageView) findViewById(R.id.homeBtn);
		
TextView headerTxt = (TextView) findViewById(R.id.headertext); 
        
        headerTxt.setText("FeedBack");
        
       
        backBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent ourIntent = new Intent(Feedback.this, Home.class);
				Feedback.this.startActivity(ourIntent);
			}
		});
        homeBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent ourIntent = new Intent(Feedback.this, Home.class);
				Feedback.this.startActivity(ourIntent);
			}
		});
        
        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost(); // The activity TabHost
        TabHost.TabSpec spec; // Reusable TabSpec for each tab
        Intent intent; // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, PostFeedback.class);
        spec = tabHost.newTabSpec("postfeedback")
        .setIndicator("Post Feedback", res.getDrawable(R.drawable.rate_me))
        .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs

        intent = new Intent().setClass(this, FeedbackHistoryTab.class);
        spec = tabHost.newTabSpec("feedbackhistory")
        .setIndicator("Feedback History", res.getDrawable(R.drawable.history_icon))
        .setContent(intent);
        tabHost.addTab(spec);
       

        //set tab which one you want open first time 0 or 1 or 2
        tabHost.setCurrentTab(0);

	}
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.menu, menu);
	        return true;
	    }

	
}
