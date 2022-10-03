package meShop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Orders extends BaseModel {
	@ManyToOne
	private UserModel userModel;
	@Column(name = "orderDate", nullable = false)
	private String orderDate;
	@Column(name = "odrerNum", nullable = false)
	private String odrerNum;


	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOdrerNum() {
		return odrerNum;
	}

	public void setOdrerNum(String odrerNum) {
		this.odrerNum = odrerNum;
	}

}
