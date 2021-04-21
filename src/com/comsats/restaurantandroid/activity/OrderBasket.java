package com.comsats.restaurantandroid.activity;

import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Map;

import com.comsats.restaurantandroid.Customer;
import com.comsats.restaurantandroid.DishDetails;
import com.comsats.restaurantandroid.OrderDetail;
import com.comsats.restaurantandroid.R;
import com.comsats.restaurantandroid.TableNumbers;
import com.comsats.restaurantandroid.Tax;
import com.comsats.restaurantandroid.R.color;
import com.comsats.restaurantandroid.store.OrderStore;
import com.comsats.restaurantandroid.util.URLConnectionReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class OrderBasket extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orderbasket);
		
		 //  top bar for total order bill
        TextView totalBill = (TextView) findViewById(R.id.total_bill);
        final TextView totalAmount = (TextView) findViewById(R.id.total_amount);
        
        final TableLayout currentOrder = (TableLayout) findViewById(R.id.current_order);
        ScrollView current_order_scroll = (ScrollView) findViewById(R.id.current_order_scroll);
        final RelativeLayout total_order_bar = (RelativeLayout) findViewById(R.id.total_order_bar);
        final RelativeLayout buttons_order = (RelativeLayout) findViewById(R.id.buttons_order);
        final TextView detail = (TextView) findViewById(R.id.detail);
        
        
        if(OrderStore.getCart().isEmpty())
        {
        	Toast.makeText(OrderBasket.this,"No Order Found!", Toast.LENGTH_LONG).show();
        }
        else
        {
        	
        	//Float total = (float) 0.0;
        	totalBill.setText("Total order: (Tax "+Tax.getTaxRate()+"%)");
        	currentOrder.removeAllViews();
        	for (Map.Entry<Integer, DishDetails> entry : OrderStore.cart.entrySet()) {
        	    final TableRow row = new TableRow(this);    	
        	    row.setBackgroundColor(Color.WHITE);
        	    row.setPadding(5, 0, 0, 0);
        	    
        	    final DishDetails dish = entry.getValue();
        	  //  total+=(Float.parseFloat(dish.getDishPrice())*dish.getQuantity());
    				TextView dish_name = new TextView(this);
    				dish_name.setText(dish.getDishName());
    				dish_name.setPadding(5, 5, 5, 5);
    				//dish_name.setWidth(250);
    				dish_name.setTextSize(16);
    				dish_name.setWidth(600);
    				dish_name.setGravity(Gravity.LEFT);
    				dish_name.setTypeface(null, Typeface.BOLD);
    				dish_name.setTextColor(Color.BLACK);
    				
    				
        	        TextView dish_quantity = new TextView(this);
        	        dish_quantity.setText(String.valueOf(dish.getQuantity()));
        	        dish_quantity.setPadding(5, 5, 5, 5);
        	        dish_quantity.setWidth(90);
					dish_name.setTextSize(16);
        	        //dish_quantity.setWidth(150);
        	        dish_quantity.setGravity(Gravity.LEFT);
        	        dish_quantity.setTextColor(Color.BLACK);
        	        
        	        TextView dish_price = new TextView(this);
        	        dish_price.setText(dish.getDishPrice()+"$");
        	        dish_price.setPadding(5, 5, 5, 5);
        	        //dish_price.setWidth(90);
					dish_name.setTextSize(16);
        	        dish_price.setWidth(150);
        	        dish_price.setGravity(Gravity.LEFT);
        	        dish_price.setTextColor(Color.BLACK);
        	        
        	        ImageView delete_btn = new ImageView(this);
        	        delete_btn.setImageResource(R.drawable.delete_button);
        	        delete_btn.setOnClickListener(new OnClickListener() {
						
						public void onClick(View v) {
							// TODO Auto-generated method stub
							OrderStore.deleteOrderItem(dish.getDishID());
							currentOrder.removeView(row);
							totalAmount.setText(new DecimalFormat(".##").format(getTotalBill())+"$");
							if(OrderStore.getCart().size() == 0)
							{
								buttons_order.setVisibility(View.GONE);
			    	        	detail.setVisibility(View.GONE);
			    	        	total_order_bar.setVisibility(View.GONE);
							}
						}
					});
        	       
        	        
        			
    			row.addView(dish_name);
    			row.addView(dish_quantity);
    			row.addView(dish_price);
    			row.addView(delete_btn);
    			
    			row.setClickable(true);
    			row.setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						AlertDialog.Builder alert = new AlertDialog.Builder(OrderBasket.this);

						alert.setTitle("Update Quantity "+dish.getDishName());
						alert.setMessage("How Many " + dish.getDishUnit());

						// Set an EditText view to get user input
						final EditText input = new EditText(OrderBasket.this);
						input.setInputType(0x00002002);
						input.setText(String.valueOf(dish.getQuantity()));
						alert.setView(input);

						alert.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										
										Float quantity = Float.parseFloat(input
												.getText().toString());
										dish.setQuantity(quantity);
										
										Toast.makeText(
												OrderBasket.this,
												"Item " + dish.getDishName()
														+ " quantity updated!",
												Toast.LENGTH_LONG).show();
										restartActivity();
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
				});
        	    
        
    			currentOrder.addView(row,new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    			
    		}  
        	 
        	totalAmount.setText(new DecimalFormat(".##").format(getTotalBill())+"$");
        	
        	
        	ImageView clear_all_btn = (ImageView) findViewById(R.id.clear_all_btn);
        	ImageView confirm_order_btn = (ImageView) findViewById(R.id.confirm_order_btn);
        	
        	clear_all_btn.setOnClickListener(new OnClickListener() {
        		
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				OrderStore.deleteAllOrderItems();
    				currentOrder.removeAllViews();
    				totalAmount.setText(new DecimalFormat(".##").format(getTotalBill())+"$");
    				buttons_order.setVisibility(View.GONE);
    	        	detail.setVisibility(View.GONE);
    	        	total_order_bar.setVisibility(View.GONE);
    				
    			}
    		});
        	
        	confirm_order_btn.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					final AlertDialog.Builder alert = new AlertDialog.Builder(OrderBasket.this);

					alert.setTitle("Confirm order");
					LinearLayout layout = new LinearLayout(OrderBasket.this);
				    layout.setOrientation(LinearLayout.VERTICAL);
				    alert.setMessage("Select Room No.");
				    
				    //final EditText table = new EditText(Order.this);
				    //table.setHint("Enter table no");
				    
				    
				    final ArrayAdapter<String> adp = new ArrayAdapter<String>(OrderBasket.this,
				            android.R.layout.simple_spinner_item, TableNumbers.tableNo);
				    adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				    final Spinner tableNo = new Spinner(OrderBasket.this);
				    tableNo.setAdapter(adp);
				    
				    final EditText instructions = new EditText(OrderBasket.this);
				    instructions.setHint("Special Instructions");

				    layout.addView(tableNo);
				    layout.addView(instructions);

				    alert.setView(layout);
					
					alert.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									
									
										OrderDetail.setTableNo(Integer.parseInt(String.valueOf(tableNo.getSelectedItem())));
										String uri = null;
										uri = URLEncoder.encode(instructions.getText().toString());
										OrderDetail.setSpecialInstructions(uri.toString());
										OrderDetail.setCountTime(Integer.parseInt(String.valueOf(Math.round(estimatedTime())))*60*1000);
										String result = confirmOrder();
										
										if(result == null || result == ""){
											
										}
										else{
											//Toast.makeText(Order.this,"Order Sent!", Toast.LENGTH_LONG).show();
											OrderStore.deleteAllOrderItems();
						    				currentOrder.removeAllViews();
						    				totalAmount.setText(new DecimalFormat(".##").format(getTotalBill())+"$");
						    				buttons_order.setVisibility(View.GONE);
						    	        	detail.setVisibility(View.GONE);
						    	        	//OrderDetail.setCountTime(Integer.parseInt(String.valueOf(Math.round(estimatedTime()))));
						    	        	startCount();
						    	        	//Intent ourIntent = new Intent(Order.this, Order.class);
						            		//Order.this.startActivity(ourIntent);
										}
										
										
									
									
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
			});

        	
        	total_order_bar.setVisibility(0);
        	buttons_order.setVisibility(0);
        	detail.setVisibility(0);
        	current_order_scroll.setVisibility(0);
        }
	}
	
	public Float getTotalBill()
	{
		Float total = (float) 0.0;
		Float discount = (float) 0.0;
		for (Map.Entry<Integer, DishDetails> entry : OrderStore.cart.entrySet()) {
			DishDetails dish = entry.getValue();
			discount = (((float) Float.parseFloat(dish.getDishDiscount())/100 )*Float.parseFloat(dish.getDishPrice()))*dish.getQuantity();
			total += Float.parseFloat(dish.getDishPrice())*dish.getQuantity()-discount;
			
		}
		float tax;
		
		tax = ((float) Tax.getTaxRate()/100)* total;
		total = total+tax;

		return total;		
	}
	
	public Float estimatedTime(){
		Float estimatedTime = (float) 0.0;
		for (Map.Entry<Integer, DishDetails> entry : OrderStore.cart.entrySet()) {
			DishDetails dish = entry.getValue();
			
			estimatedTime +=Float.parseFloat(dish.getDishCookTime());
		}
		estimatedTime = estimatedTime/OrderStore.cart.size();
		//Log.d("check", String.valueOf(estimatedTime));
		return estimatedTime;
	}
	
	public void startCount()
	{
		
		Toast.makeText(OrderBasket.this,"Your order will be delivered in "+OrderDetail.getCountTime()/1000/60+" mins", Toast.LENGTH_LONG).show();
		finish();
		Intent ourIntent = new Intent(OrderBasket.this, Home.class);
		OrderBasket.this.startActivity(ourIntent);
		
	}
	
	public String confirmOrder() {
		String result = null;
		String content = null;
		try {
			content = URLConnectionReader.getText(URLConnectionReader.getIP()
					+ "confirm_order.jsp?cart="+ OrderStore.getCartInStringFormat()+"&tableNo="+OrderDetail.getTableNo()+"&instruction="+OrderDetail.getSpecialInstructions()+"&time="+String.valueOf(estimatedTime())+"&customerID="+Customer.getUuid());
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
		Intent ourIntent = new Intent(OrderBasket.this, Order.class);
		OrderBasket.this.startActivity(ourIntent);
	 }
	 @Override
		public void onBackPressed() {
			// TODO Auto-generated method stub
			 finish();
				Intent ourIntent = new Intent(OrderBasket.this, Home.class);
	 		OrderBasket.this.startActivity(ourIntent);
		}
	
}
