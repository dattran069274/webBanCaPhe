package meShop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class UserModel extends BaseModel{
	@Column(name="userName",nullable = false)
	String userName;
	@Column(name="password",nullable = false)
	String password;
	@Column(name="fullName")
	String fullName;
	@Column(name="address")
	String address;
	@Column(name="phone")
	String phone;
	@Column(name="email")
	String email;
	public String getEmail() {
		return email;
	}
	public UserModel(){
		super();	
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name="status")
	Integer status;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "userName: "+ userName+" id "+id+" password "+password+ " fullName "+fullName;
	}
	
}
