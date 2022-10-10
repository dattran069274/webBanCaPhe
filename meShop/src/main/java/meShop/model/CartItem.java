package meShop.model;

import java.io.Serializable;

public class CartItem implements Serializable{
	private String title;
	private String productId;
	private double price;
	private byte[] imgae;
	private int quantity=1;
	public String getTitle() {
		return title;
	}
	public CartItem(String productId,int quantity) {
		this.productId=productId;
		this.quantity=quantity;
	}
	public CartItem() {
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public byte[] getImgae() {
		return imgae;
	}
	public void setImgae(byte[] imgae) {
		this.imgae = imgae;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
