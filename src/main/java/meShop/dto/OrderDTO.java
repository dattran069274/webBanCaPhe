package meShop.dto;

import java.util.Date;

import javax.persistence.Column;

import meShop.model.UserModel;

public class OrderDTO extends AbstractDTO<ProductDTO>{
	String userId;
	private Date orderDate;
	int state;
	String stateName;
	String messageError;
	public String getMessageError() {
		return messageError;
	}
	public void setMessageError(String messageError) {
		this.messageError = messageError;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	private String receiverAddress;
	private String receiverName;
	private UserModel shipper;
	public UserModel getShipper() {
		return shipper;
	}
	boolean isPaid;
	public void setIsPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
	public boolean getIsPaid() {
		return isPaid;
	}
	public void setShipper(UserModel userModel) {
		this.shipper = userModel;
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
