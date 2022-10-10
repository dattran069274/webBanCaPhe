package meShop.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import meShop.model.CartItem;
import net.bytebuddy.asm.Advice.This;
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService{
	
	public Map<String, CartItem> getCartItems() {
		return cartItems;
	}
	Map<String,CartItem> cartItems=new HashMap<String, CartItem>();
	@Override
	public int addCart(CartItem cartItem) {
		CartItem c=this.cartItems.get(cartItem.getProductId());
		if(c!=null) {
			c.setQuantity(c.getQuantity()+1);
			return c.getQuantity();
		}
		else this.cartItems.put(cartItem.getProductId(), cartItem);
		return 1;
	}
	@Override
	public void removeCart(String id) {
		System.out.println("xoa " +id);
		if(this.cartItems.get(id)!=null) {
			System.out.println("xoa roi" +id);
			this.cartItems.remove(id);
		}
		System.out.println("co chua "+id+" kh?: "+ this.cartItems.containsKey(id));
		System.out.println("size " +this.cartItems.size());
	}
	@Override
	public CartItem updateCart(String productId,int quantity) {
		CartItem c=this.cartItems.get(productId);
		if(c!=null) c.setQuantity(quantity);
		return c;
	}
	@Override
	public void clearCart() {
		this.cartItems.clear();
	}
	@Override
	public Collection<CartItem> getAllCartItem() {
		return this.cartItems.values();
		
	}
	@Override
	public double getTotal() {
		return this.cartItems.values().stream().mapToDouble(cartItem->cartItem.getPrice()*cartItem.getQuantity()).sum();
	}
}
