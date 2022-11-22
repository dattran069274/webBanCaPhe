package meShop.dto;

import java.util.Date;

public class OrderDTO extends AbstractDTO<ProductDTO>{
	String userId;
	private Date orderDate;
	int state;
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getUserId(){
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
}
