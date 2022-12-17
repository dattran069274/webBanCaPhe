package meShop.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class OrderModel extends BaseModel {
	@ManyToOne
	private UserModel userModel;
	@Column(name = "orderDate", nullable = false)
	private Date orderDate;
	
	@ManyToOne
	@JoinColumn(name="state_code")
	private OrderStateModel state;
	@ManyToOne
	@JoinColumn(name="shipper_id")
	private UserModel shipper;
		
	public UserModel getShipper() {
		return shipper;
	}
	public void setShipper(UserModel shipper) {
		this.shipper = shipper;
	}
	@Column(name = "receiverAddress", nullable = false)
	private String receiverAddress;
	@Column(name = "messageError", nullable = true)
	private String messageError;
	public String getMessageError() {
		return messageError;
	}
	public void setMessageError(String messageError) {
		this.messageError = messageError;
	}
	@Column(name = "receiverName", nullable = false)
	private String receiverName;
	@Column(name = "paypal_order_id")
    private String paypalOrderId;
    @Column(name = "paypal_order_status")
    private String paypalOrderStatus;
    
	public String getPaypalOrderId() {
		return paypalOrderId;
	}
	public void setPaypalOrderId(String paypalOrderId) {
		this.paypalOrderId = paypalOrderId;
	}
	public String getPaypalOrderStatus() {
		return paypalOrderStatus;
	}
	public void setPaypalOrderStatus(String paypalOrderStatus) {
		this.paypalOrderStatus = paypalOrderStatus;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public OrderStateModel getState() {
		return state;
	}
	public void setState(OrderStateModel state) {
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