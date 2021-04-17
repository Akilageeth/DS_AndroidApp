package com.comsats.restaurantandroid.activity;

import java.util.ArrayList;

import com.comsats.restaurantandroid.CurrentOrders;
import com.comsats.restaurantandroid.DishDetails;
import com.comsats.restaurantandroid.R;
import com.comsats.restaurantandroid.util.URLConnectionReader;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class CurrentOrderListBaseAdapter extends BaseAdapter {
	private static ArrayList<CurrentOrders> itemDetailsArrayList;
	
	
	private LayoutInflater l_Inflater;

	public CurrentOrderListBaseAdapter(Context context, ArrayList<CurrentOrders> results) {
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
			convertView = l_Inflater.inflate(R.layout.currentorderlist, null);
			holder = new ViewHolder();
			holder.serialNo = (TextView) convertView.findViewById(R.id.numbers);
			holder.layout = (TableLayout) convertView.findViewById(R.id.current_order_detail);
			holder.layout.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT));
			holder.layout.setStretchAllColumns(true);
	 
			 TableRow row1 = new TableRow(l_Inflater.getContext());
			    
			    TextView dishName1 = new TextView(l_Inflater.getContext());
			    dishName1.setTypeface(null, Typeface.BOLD);
			    dishName1.setText("Name");
			    
			    
			    TextView dishQuantity1 = new TextView(l_Inflater.getContext());
			    dishQuantity1.setTypeface(null, Typeface.BOLD);
			    dishQuantity1.setText("Quantity");
			    
			    TextView dishPrice1 = new TextView(l_Inflater.getContext());
			    dishPrice1.setTypeface(null, Typeface.BOLD);
			    dishPrice1.setText("Price");
			    
			    row1.addView(dishName1);
			    row1.addView(dishQuantity1);
			    row1.addView(dishPrice1);
			    
			    holder.layout.addView(row1);
			    
			    ArrayList<DishDetails> orderDetail = prepareOrderHistoryDetails(itemDetailsArrayList.get(position).getOrderID());
				for (DishDetails dishDetails : orderDetail) {
					 TableRow row = new TableRow(l_Inflater.getContext());
					    
					    TextView dishName = new TextView(l_Inflater.getContext());
					    dishName.setText(dishDetails.getDishName());
					    
					    
					    TextView dishQuantity = new TextView(l_Inflater.getContext());
					    dishQuantity.setText(dishDetails.getQuantity().toString());
					    
					    TextView dishPrice = new TextView(l_Inflater.getContext());
					    dishPrice.setText(dishDetails.getDishPrice());
					    
					    row.addView(dishName);
					    row.addView(dishQuantity);
					    row.addView(dishPrice);
					    
					    holder.layout.addView(row);
				}

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.serialNo.setText(String.valueOf(itemDetailsArrayList.get(position).getOrderID()));
		
		return convertView;
	}

	static class ViewHolder {
		TextView serialNo;
		TableLayout layout;
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

	
}
