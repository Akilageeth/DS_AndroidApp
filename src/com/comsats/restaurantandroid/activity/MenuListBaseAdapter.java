package com.comsats.restaurantandroid.activity;

import java.util.ArrayList;

import com.comsats.restaurantandroid.MenuItemDetails;
import com.comsats.restaurantandroid.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuListBaseAdapter extends BaseAdapter {
private static ArrayList<MenuItemDetails> itemDetailsrrayList;
	
	
	private LayoutInflater l_Inflater;

	public MenuListBaseAdapter(Context context, ArrayList<MenuItemDetails> results) {
		itemDetailsrrayList = results;
		l_Inflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return itemDetailsrrayList.size();
	}

	public Object getItem(int position) {
		return itemDetailsrrayList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = l_Inflater.inflate(R.layout.main_menu, null);
			holder = new ViewHolder();
			holder.txt_menuName = (TextView) convertView.findViewById(R.id.menuName);
			holder.iconName = (ImageView) convertView.findViewById(R.id.iconName);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txt_menuName.setText(itemDetailsrrayList.get(position).getMenuItemName());
		switch (position) {
		case 0:
			holder.iconName.setImageResource(R.drawable.menu1);	
			break;
case 1:
	holder.iconName.setImageResource(R.drawable.menu2);
			break;
case 2:
	holder.iconName.setImageResource(R.drawable.menu3);
	break;
case 3:
	holder.iconName.setImageResource(R.drawable.menu4);
	break;
case 4:
	holder.iconName.setImageResource(R.drawable.menu5);
	break;
case 5:
	holder.iconName.setImageResource(R.drawable.menu6);
	break;
	case 6:
		holder.iconName.setImageResource(R.drawable.menu7);
		break;

		default:
			break;
		}
				
		
		return convertView;
	}

	static class ViewHolder {
		TextView txt_menuName;
		ImageView iconName;
	}
}
