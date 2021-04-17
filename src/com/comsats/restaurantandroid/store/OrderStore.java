package com.comsats.restaurantandroid.store;

import java.util.HashMap;
import java.util.Map;

import com.comsats.restaurantandroid.DishDetails;

public class OrderStore {

	public static HashMap<Integer, DishDetails> cart = new HashMap<Integer, DishDetails>();
	
	// example  call: //OrderStore.saveOrderItem(obj_itemDetails.getDishID(), obj_itemDetails);
	public static void saveOrderItem(Integer itemId, DishDetails dish) {
		cart.put(itemId, dish);
		//Log.d("yes", String.valueOf(getCart().size()));
	}
	
	public static HashMap<Integer, DishDetails> getCart() {
		//List<DishDetails> orderItemsList = new ArrayList<DishDetails>(cart.values());
		return cart;
	}
	
	public static void deleteOrderItem(Integer itemId) {
		cart.remove(itemId);
	}
	
	public static void deleteAllOrderItems () {
		cart = new HashMap<Integer, DishDetails>();
	}
	
	public static String getCartInStringFormat() {
		String cartString = new String();
		for (Map.Entry<Integer, DishDetails> entry : cart.entrySet()) {
			DishDetails dish = entry.getValue();
			cartString = cartString + dish.getDishID()+":"+dish.getQuantity()+",";
		}  
		return cartString;
	}

}
