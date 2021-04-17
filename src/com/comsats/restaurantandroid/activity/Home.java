package com.comsats.restaurantandroid.activity;

import java.util.ArrayList;
import java.util.Random;


import com.comsats.restaurantandroid.Ad;
import com.comsats.restaurantandroid.Customer;
import com.comsats.restaurantandroid.MenuItemDetails;
import com.comsats.restaurantandroid.OrderDetail;
import com.comsats.restaurantandroid.R;
import com.comsats.restaurantandroid.TableNumbers;
import com.comsats.restaurantandroid.util.ImageLoader;
import com.comsats.restaurantandroid.util.URLConnectionReader;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends ApplicationMenu {

	String[] classes = {"HotDishesList","CategoriesList","Order","Favourite","CallWaiter","Feedback"};
	
	CountDownTimer countDownTimer;          // built in android class CountDownTimer
	long totalTimeCountInMilliseconds;      // total count down time in milliseconds
    long timeBlinkInMilliseconds;           // start time of start blinking
    boolean blink;                          // controls the blinking .. on
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
    	super.onCreate(savedInstanceState);
       
        setContentView(R.layout.app_main);
        
        ImageView contactBtn = (ImageView) findViewById(R.id.contactBtn);
        ImageView aboutBtn = (ImageView) findViewById(R.id.aboutBtn);
        
        RelativeLayout countdownlayout = (RelativeLayout) findViewById(R.id.countdownlayout);
        final TextView countdown = (TextView) findViewById(R.id.countdown);
        
        
        if(OrderDetail.getCountTime() == 0)
        {
        	
        }
        else
        {
        	countdownlayout.setVisibility(0);
            
            totalTimeCountInMilliseconds = OrderDetail.getCountTime();      // time count for 3 minutes = 180 seconds
            timeBlinkInMilliseconds = 30 * 1000;            // blink starts at 1 minutes = 60 seconds
       
     

            countdown.setTextAppearance(getApplicationContext(), R.style.normalText);
                    countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 500) {
                        // 500 means, onTick function will be called at every 500 milliseconds
     
                        @Override
                        public void onTick(long leftTimeInMilliseconds) {
                            long seconds = leftTimeInMilliseconds / 1000;
     
                            
                            if ( leftTimeInMilliseconds < timeBlinkInMilliseconds ) {
                            	countdown.setTextAppearance(getApplicationContext(), R.style.blinkText);
                                // change the style of the textview .. giving a red alert style
     
                                if ( blink ) {
                                	countdown.setVisibility(View.VISIBLE);
                                    // if blink is true, textview will be visible
                                } else {
                                	countdown.setVisibility(View.INVISIBLE);
                                }
     
                                blink = !blink;         // toggle the value of blink
                            }
     
                            countdown.setText("Estimated Time Remaining: "+String.format("%02d", seconds / 60) + ":" + String.format("%02d", seconds % 60));
                            // format the textview to show the easily readable format
                            OrderDetail.setCountTime(Integer.parseInt(String.valueOf(leftTimeInMilliseconds)));
                        }
     
                        @Override
                        public void onFinish() {
                            // this function will be called when the timecount is finished
                        	countdown.setText("Estimated Time Remaining: 00:00");
                        	countdown.setVisibility(View.VISIBLE);
                        	OrderDetail.setCountTime(0);
                        }
     
                    }.start();
        }
        
        contactBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent ourIntent = new Intent(Home.this, Contact.class);
        		Home.this.startActivity(ourIntent);
			}
		});

        aboutBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			finish();
				Intent ourIntent = new Intent(Home.this, About.class);
        		Home.this.startActivity(ourIntent);
			}
		});
        ArrayList<MenuItemDetails> menu_details = GetMenuResults();
        final ListView lv1 = (ListView) findViewById(R.id.MenuList);
        lv1.setAdapter(new MenuListBaseAdapter(this, menu_details));
    
        lv1.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
        		Object o = lv1.getItemAtPosition(position);
        		MenuItemDetails obj_itemDetails = (MenuItemDetails)o;
        		if(obj_itemDetails.getMenuItemID() == 4)
        		{
        			doCall();
        		}
        		else if(obj_itemDetails.getMenuItemID() == 3)
        		{
        			finish();
        			Intent ourIntent = new Intent(Home.this, Favourite.class);
	        		Home.this.startActivity(ourIntent);
        		}
        		else
        		{
        		final String className = classes[obj_itemDetails.getMenuItemID()];
        		Thread timer = new Thread()
        		{
        			public void run(){
        					try{
        						Intent ourIntent = new Intent(Home.this, Loading.class);
        		        		Home.this.startActivity(ourIntent);
        		        		sleep(1000);
        						
        					}catch (Exception e) {
								// TODO: handle exception
							}finally{
								try {
									@SuppressWarnings("rawtypes")
									Class ourClass = Class.forName("com.comsats.restaurantandroid.activity." + className);
									finish();
									Intent ourIntent = new Intent(Home.this, ourClass);
					        		Home.this.startActivity(ourIntent);
								} catch (ClassNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} 
							}
        			}
        		};
        		
        	timer.start();
        		}
        	}  
        });
        
        int size = Ad.list.size();
        //random index
        int index = new Random().nextInt(size);
        Ad adbanner = Ad.list.get(index);
        
        ImageView ads_banner = (ImageView) findViewById(R.id.ads_banner);
        String thumburl = URLConnectionReader.getMediaIP()
				+ "uploads/ads/"
				+ adbanner.getBannerName();
        ImageLoader imageLoader = new ImageLoader(this);
        imageLoader.DisplayImage(thumburl, ads_banner);
        
		//Drawable drawable = LoadImageFromWebOperations(thumburl);
		
		//ads_banner.setImageDrawable(drawable);
		LayoutParams params = (LayoutParams) ads_banner.getLayoutParams();
		Display mDisplay= this.getWindowManager().getDefaultDisplay();
		//params.width = 480;
		params.width = mDisplay.getWidth();
		Log.w("width", String.valueOf(mDisplay.getWidth()));
		//params.height = (30/100)*mDisplay.getHeight();
		//params.height = 300;
		if(mDisplay.getWidth() > 480)
		{
		params.height = 800;
		}else
		{
			params.height = 800;
		}

		// existing height is ok as is, no need to edit it
		ads_banner.setLayoutParams(params);

		ads_banner.setScaleType(ScaleType.FIT_XY);
		
       
    }
    
    private void doCall()
    {
		final AlertDialog.Builder alert = new AlertDialog.Builder(Home.this);

		alert.setTitle("Call Waiter");
		LinearLayout layout = new LinearLayout(Home.this);
	    layout.setOrientation(LinearLayout.VERTICAL);
	    alert.setMessage("Select Table No.");
	    
	    //final EditText table = new EditText(Order.this);
	    //table.setHint("Enter table no");
	    
	    
	    final ArrayAdapter<String> adp = new ArrayAdapter<String>(Home.this,
	            android.R.layout.simple_spinner_item, TableNumbers.tableNo);
	    adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    
	    final Spinner tableNo = new Spinner(Home.this);
	    tableNo.setAdapter(adp);

	    layout.addView(tableNo);

	    alert.setView(layout);
		
		alert.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int whichButton) {
						Thread timer = new Thread()
		        		{
		        			public void run(){
		        					try{
		        						sendCallToServer(String.valueOf(tableNo.getSelectedItem()), Customer.getUuid());
		        						Intent ourIntent = new Intent(Home.this, Loading.class);
		        		        		Home.this.startActivity(ourIntent);
		        		        		sleep(2000);
		        						
		        					}catch (Exception e) {
										// TODO: handle exception
									}finally{
										finish();
										Intent ourIntent = new Intent(Home.this, Home.class);
										Home.this.startActivity(ourIntent);
									}
		        			}
		        		};
		        		
		        	timer.start();
		        	Toast.makeText(Home.this, "Please wait waiter should be Here in any minute!", Toast.LENGTH_LONG).show();
							}
				});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int whichButton) {
						// Canceled.
						dialog.cancel();
					}
				});

		alert.show();
    }

    private String sendCallToServer(String tableNo, String customerID)
    {
		String content = null;
		try {
			
			content = URLConnectionReader.getText(URLConnectionReader.getIP()
					+ "do_call_waiter.jsp?tableNo="+tableNo+"&customerID="+customerID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return content;

    }
    	
	private ArrayList<MenuItemDetails> GetMenuResults(){
    	ArrayList<MenuItemDetails> results = new ArrayList<MenuItemDetails>();
    
		MenuItemDetails item_details0 = new MenuItemDetails();
		MenuItemDetails item_details1 = new MenuItemDetails();
		MenuItemDetails item_details2 = new MenuItemDetails();
		MenuItemDetails item_details3 = new MenuItemDetails();
		MenuItemDetails item_details4 = new MenuItemDetails();
		MenuItemDetails item_details5 = new MenuItemDetails();
		
		
		item_details0.setMenuItemID(0);
    	item_details0.setMenuItemName("Our Hotels");
    	results.add(item_details0);
		item_details1.setMenuItemID(1);
        	item_details1.setMenuItemName("Menu List");
        	results.add(item_details1);
        	item_details2.setMenuItemID(2);
        	item_details2.setMenuItemName("My Orders");
        	results.add(item_details2);
        	item_details3.setMenuItemID(3);
        	item_details3.setMenuItemName("Favourite Food");
        	results.add(item_details3);
        	item_details4.setMenuItemID(4);
        	item_details4.setMenuItemName("Request Room Service");
        	results.add(item_details4);
        	item_details5.setMenuItemID(5);
        	item_details5.setMenuItemName("Ratings and Feedback");
        	results.add(item_details5);
        	
		
    	return results;
    }
	public void onBackPressed() {
	    finish();
	}
	
}
