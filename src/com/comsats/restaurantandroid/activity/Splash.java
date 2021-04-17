package com.comsats.restaurantandroid.activity;


import java.util.ArrayList;

import com.comsats.restaurantandroid.Ad;
import com.comsats.restaurantandroid.Customer;
import com.comsats.restaurantandroid.Greeting;
import com.comsats.restaurantandroid.R;
import com.comsats.restaurantandroid.TableNumbers;
import com.comsats.restaurantandroid.Tax;
import com.comsats.restaurantandroid.util.Installation;
import com.comsats.restaurantandroid.util.URLConnectionReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;

public class Splash extends Activity{
	NotificationManager greetingManager;    // greetings Notification Manager
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		boolean isNet = CheckInternet();
		if(!isNet)
		{
			  AlertDialog.Builder builder = new AlertDialog.Builder(this);
		        builder.setMessage("Turn on mobile data or use Wi-Fi to access data");
		        builder.setTitle("Mobile Data is Turned Off");
		        
		        builder.setPositiveButton("Settings",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								Splash.this.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
									}
						});

				builder.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// Canceled.
								dialog.cancel();
							}
						});


		        builder.show();
		}
		else
		{
		greetingManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        initializeGreetingUIElements();
        
		getTableNumbers();
		getAds();
		GetTax();
		Thread timer = new Thread()
		{
			public void run()
			{
				try
				{
					String uuid = Installation.id(Splash.this);
					Customer.setUuid(uuid);
					sleep(3000);
				}catch(InterruptedException ex){
					ex.printStackTrace();
				}finally{
					Intent ourIntent = new Intent(Splash.this, Home.class);
					Splash.this.startActivity(ourIntent);
				}
				
			}
		};
		timer.start();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	private void GetTax(){
    	
    	
    	
    	String content = null;
		try {
			content = URLConnectionReader.getText(URLConnectionReader.getIP()+"get_tax.jsp");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			Tax.setTaxRate(Integer.parseInt(content));
    	
    }
private void getTableNumbers(){
    	
    	
    	
    	String content = null;
		try {
			content = URLConnectionReader.getText(URLConnectionReader.getIP()+"get_all_tables.jsp");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TableNumbers.tableNo = content.split(",");
		
    }

private void initializeGreetingUIElements()
{
//get all greetings messages to server
    
    ArrayList<Greeting> showGreeting = getGreetingMessages();
    
    if(showGreeting.size() > 0)
    {
    
	for (Greeting var : showGreeting) {
		// Prepare intent which is triggered if the
	    // notification is selected
		Bundle basket = new Bundle();
	    Intent intent = new Intent(this, com.comsats.restaurantandroid.activity.Greeting.class);
	    basket.putString("title", var.getGreetingTitle());
	    basket.putString("description", var.getGreetingDescription());
	    basket.putString("banner", var.getBanner());
	    intent.putExtras(basket);
	    
	    PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
	    String body = var.getGreetingDescription();
    	String title = var.getGreetingTitle();
    	Notification greetingNotification = new Notification(R.drawable.notification, body, System.currentTimeMillis());
    	greetingNotification.setLatestEventInfo(this, title, body, pIntent);
    	greetingNotification.defaults = Notification.DEFAULT_ALL;
	     // Hide the notification after its selected
    	greetingNotification.flags |= Notification.FLAG_AUTO_CANCEL;

    	greetingManager.notify(var.getGreetingId(), greetingNotification);
		}
    }
}

private ArrayList<Greeting> getGreetingMessages(){
	ArrayList<Greeting> results = new ArrayList<Greeting>();
	
	
	String content = null;
	try {
		content = URLConnectionReader.getText(URLConnectionReader.getIP()+"get_all_greetings.jsp");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	if (content != null && content.trim().length() > 0)  {
		String [] allURLs = content.split(";");
		Greeting item_details;
	    for (int i=0; i<allURLs.length; i++) {
	    	item_details = new Greeting();
	    	String [] detail = allURLs[i].split("---");
	    	item_details.setGreetingId(Integer.parseInt(detail[0]));
	    	item_details.setGreetingTitle(detail[1]);
	    	item_details.setGreetingDescription(detail[2]);
	    	item_details.setBanner(detail[3]);
	    	results.add(item_details);
	    }
	}
	
	return results;
}
private void getAds(){
	//ArrayList<Ad> results = new ArrayList<Ad>();
	
	Ad.list.clear();
	String content = null;
	try {
		content = URLConnectionReader.getText(URLConnectionReader.getIP()+"get_all_ads.jsp");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	if (content != null && content.trim().length() > 0)  {
		String [] allURLs = content.split(";");
		Ad item_details;
	    for (int i=0; i<allURLs.length; i++) {
	    	item_details = new Ad();
	    	String [] detail = allURLs[i].split(",");
	    	item_details.setAdId(Integer.parseInt(detail[0]));
	    	item_details.setAdName(detail[1]);
	    	item_details.setAdDescription(detail[2]);
	    	item_details.setBannerName(detail[3]);
	    	Ad.list.add(item_details);
	    }
	}
	
}
public boolean CheckInternet() 
{
    ConnectivityManager connec = (ConnectivityManager) this.getSystemService(Splash.CONNECTIVITY_SERVICE);
    android.net.NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    android.net.NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

    // Here if condition check for wifi and mobile network is available or not.
    // If anyone of them is available or connected then it will return true, otherwise false;

    if (wifi.isConnected()) {
        return true;
    } else if (mobile.isConnected()) {
        return true;
    }
    return false;
}

}
