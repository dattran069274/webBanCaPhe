package meShop.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "orderState")
public class OrderStateModel extends BaseModel{
	@OneToMany(mappedBy ="state")
	private List<OrderModel> orders=new ArrayList<>();
	@Column(name="code",nullable = false)
	int code;
	@Column(name="name",nullable = false)
	String name;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
