package com.comsats.restaurantandroid.activity;

import java.util.ArrayList;

import com.comsats.restaurantandroid.CurrentOrders;
import com.comsats.restaurantandroid.Customer;
import com.comsats.restaurantandroid.R;
import com.comsats.restaurantandroid.util.URLConnectionReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RunningOrder extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.currentorder);
		
	     //   current orders
        ArrayList<CurrentOrders> currentOrderList = currentOrdersList();
        ListView current_order_list = (ListView) findViewById(R.id.current_order_list);
        TextView current_order_txt = (TextView) findViewById(R.id.current_order_txt);
        if(currentOrderList.size() > 0)
        {
        	current_order_list.setVisibility(0);
        	current_order_txt.setVisibility(0);
        	current_order_list.setAdapter(new CurrentOrderListBaseAdapter(this, currentOrderList));
        }
        else
        {
        	Toast.makeText(RunningOrder.this,"No Running Order Found!", Toast.LENGTH_LONG).show();
        	current_order_txt.setVisibility(View.GONE);
        	current_order_list.setVisibility(View.GONE);
        }
        //   end
	}
	
	private ArrayList<CurrentOrders> currentOrdersList() {
		// TODO Auto-generated method stub
		ArrayList<CurrentOrders> results = new ArrayList<CurrentOrders>();
		
		String content = null;
		try {
			content = URLConnectionReader.getText(URLConnectionReader.getIP()+"current_orders.jsp?customerID="+Customer.getUuid());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(content.length() > 0)
		{
		String [] allURLs = content.split(",");
		CurrentOrders current_order_details;
        for (int i=0; i<allURLs.length; i++) {
        	current_order_details = new CurrentOrders();
        	current_order_details.setOrderID(Integer.parseInt(allURLs[i]));
        	
        	results.add(current_order_details);
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
				Intent ourIntent = new Intent(RunningOrder.this, Home.class);
	 		RunningOrder.this.startActivity(ourIntent);
		}
	
}
