package com.comsats.restaurantandroid.activity;

import java.util.ArrayList;

import com.comsats.restaurantandroid.Customer;
import com.comsats.restaurantandroid.DishDetails;
import com.comsats.restaurantandroid.OrderHistory;
import com.comsats.restaurantandroid.R;
import com.comsats.restaurantandroid.util.URLConnectionReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class OrderHistoryTab extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orderhistory);
		
		ArrayList<OrderHistory> orderHistoryList = prepareOrderHistoryList();
		
		 final ListView orderhistorylistview = (ListView) findViewById(R.id.orderhistorylistview);
	        if(orderHistoryList.size() > 0)
	        {
	        	  
	        	orderhistorylistview.setAdapter(new OrderHistoryListBaseAdapter(this, orderHistoryList));
	        }
	        else
	        {
	        	Toast.makeText(OrderHistoryTab.this,"History Not Found!", Toast.LENGTH_LONG).show();
	        }
	       
	        
	        
	        //click on order history to view details
	        orderhistorylistview.setOnItemClickListener(new OnItemClickListener() {
	        	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
	        		Object o = orderhistorylistview.getItemAtPosition(position);
	            	OrderHistory obj_itemDetails = (OrderHistory)o;
	            	
	            	ArrayList<DishDetails> orderHistoryDetail = prepareOrderHistoryDetails(obj_itemDetails.getOrderID());

	            	final AlertDialog.Builder historyDetail = new AlertDialog.Builder(OrderHistoryTab.this);

					historyDetail.setTitle("Total Bill: "+obj_itemDetails.getOrderTotal()+"PKR");
					ScrollView scrollView = new ScrollView(OrderHistoryTab.this);
					TableLayout layout = new TableLayout(OrderHistoryTab.this);
					layout.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT));
					layout.setStretchAllColumns(true);
			 
			        //Create the first row and add two text views
			        TableRow row1 = new TableRow(OrderHistoryTab.this);
								    
				    TextView dishName1 = new TextView(OrderHistoryTab.this);
				    dishName1.setTypeface(null, Typeface.BOLD);
				    dishName1.setText("Name");
				    
				    
				    TextView dishQuantity1 = new TextView(OrderHistoryTab.this);
				    dishQuantity1.setTypeface(null, Typeface.BOLD);
				    dishQuantity1.setText("Quantity");
				    
				    TextView dishPrice1 = new TextView(OrderHistoryTab.this);
				    dishPrice1.setTypeface(null, Typeface.BOLD);
				    dishPrice1.setText("Price");
				    
				    row1.addView(dishName1);
				    row1.addView(dishQuantity1);
				    row1.addView(dishPrice1);
				    
				    layout.addView(row1);
				    
				    for (DishDetails varOrderHistoryDetail : orderHistoryDetail) {
				    	// create dynamic rows
				        TableRow row = new TableRow(OrderHistoryTab.this);
					    
					    TextView dishName = new TextView(OrderHistoryTab.this);
					    dishName.setText(varOrderHistoryDetail.getDishName());
					    
					    
					    TextView dishQuantity = new TextView(OrderHistoryTab.this);
					    dishQuantity.setText(varOrderHistoryDetail.getQuantity().toString());
					    
					    TextView dishPrice = new TextView(OrderHistoryTab.this);
					    dishPrice.setText(varOrderHistoryDetail.getDishPrice());
					    
					    row.addView(dishName);
					    row.addView(dishQuantity);
					    row.addView(dishPrice);
					    
					    layout.addView(row);
					}
				    scrollView.addView(layout);
				    
				    //final EditText table = new EditText(Order.this);
				    //table.setHint("Enter table no");
				    
				    historyDetail.setView(scrollView);
					
					historyDetail.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									dialog.cancel();
										}
							});
							historyDetail.show();
	            	
	        	}  
	        });        //end listener
	        
	        
	}
	
	private ArrayList<OrderHistory> prepareOrderHistoryList() {
		// TODO Auto-generated method stub
		ArrayList<OrderHistory> results = new ArrayList<OrderHistory>();
		
		String content = null;
		try {
			content = URLConnectionReader.getText(URLConnectionReader.getIP()+"get_order_history.jsp?customerID="+Customer.getUuid());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(content.length() > 0)
		{
		String [] allURLs = content.split(";");
		OrderHistory item_details;
        for (int i=0; i<allURLs.length; i++) {
        	item_details = new OrderHistory();
        	String [] detail = allURLs[i].split(",");
        	item_details.setOrderID(Integer.parseInt(detail[0]));
        	item_details.setDateTime(detail[1]);
        	item_details.setOrderTotal(Float.parseFloat(detail[2]));
        	
        	results.add(item_details);
        }
		}
		else
		{
			
		}
    	return results;
		
		
	}
	
	private ArrayList<DishDetails> prepareOrderHistoryDetails(Integer id) {
		// TODO Auto-generated method stub
		ArrayList<DishDetails> results = new ArrayList<DishDetails>();
		
		String content = null;
		try {
			content = URLConnectionReader.getText(URLConnectionReader.getIP()+"get_order_history_detail.jsp?order_ID="+id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String [] allURLs = content.split(";");
		DishDetails item_details;
        for (int i=0; i<allURLs.length; i++) {
        	item_details = new DishDetails();
        	String [] detail = allURLs[i].split(",");
        	item_details.setDishName(detail[0]);
        	item_details.setDishPrice(detail[1]);
        	item_details.setQuantity(Float.parseFloat(detail[2]));
        	
        	results.add(item_details);
        }
		
    	return results;
		
		
	}
	
	 @Override
		public void onBackPressed() {
			// TODO Auto-generated method stub
			 finish();
				Intent ourIntent = new Intent(OrderHistoryTab.this, Home.class);
	 		OrderHistoryTab.this.startActivity(ourIntent);
		}

		
}
