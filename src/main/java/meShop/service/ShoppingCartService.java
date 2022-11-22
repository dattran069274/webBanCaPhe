package meShop.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import meShop.model.CartItem;

public interface ShoppingCartService {

	void clearCart();

	CartItem updateCart(String productId, int quantity);

	void removeCart(String id);

	int addCart(CartItem cartItem);
	int addCart(CartItem cartItem,int num);
	Collection<CartItem> getAllCartItem();
	double getTotal();
}
