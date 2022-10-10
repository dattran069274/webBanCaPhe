package meShop.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import meShop.model.CartItem;

public class CartItemsDTO {
	List<CartItem> cartItems=new ArrayList<>();

	public CartItemsDTO(Collection<CartItem> CartItem) {
		CartItem.forEach(item->{cartItems.add(item);});
		System.out.println("CartItemsDTO Size: "+this.cartItems.size());
	}
	
	public CartItemsDTO() {
		super();
	}

	public Collection<CartItem> getCartItems() {
		return this.cartItems;
	}

	public void setCartItems(Collection<CartItem> cartItems) {
		this.cartItems = (List<CartItem>) cartItems;
	}	
}
