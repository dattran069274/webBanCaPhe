package meShop.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CartItemList implements Serializable{
//	List<CartItem> cartItems;
//	public CartItemList(List<CartItem> cartItems){
//		this.cartItems=cartItems;
//	}
//	public List<CartItem> getCartItems() {
//		return cartItems;
//	}
//	public void setCartItems(List<CartItem> cartItems) {
//		this.cartItems = cartItems;
//	}
	List<String> listId;
	List<Integer> listQuantity;
	
	public CartItemList(List<String> listId,List<Integer> listQuantity) {
		this.listId=listId;
		this.listQuantity=listQuantity;
	}
	@Override
	public String toString() {
		StringBuilder result=new StringBuilder();
		for (int i = 0; i < listId.size(); i++) {
			result.append("Id: "+listId.get(i)+" q:"+String.valueOf(listQuantity.get(i)));
		}
		return "size: "+listId.size()+" "+result.toString();
	}
	
	public List<CartItem> getCartItems(CartItemList cartItemList){
		List<CartItem> cartItems=new ArrayList<>();
		for (int i = 0; i < listId.size(); i++) {
			cartItems.add(new CartItem(listId.get(i),listQuantity.get(i)));
		}
		return cartItems;
	}
}
