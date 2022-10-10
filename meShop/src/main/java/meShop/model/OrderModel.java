package meShop.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class OrderModel extends BaseModel {
	@ManyToOne
	private UserModel userModel;
	@Column(name = "orderDate", nullable = false)
	private Date orderDate;
	private int state=0;
	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public UserModel getUserModel() {
		return userModel;
	}
	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	


	

}
