package meShop.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name = "role")
public class RoleModel extends BaseModel{
	@Column(name = "name")
	String name;
	@Column(name = "code")
	int code;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	@OneToMany(mappedBy ="role")
	private List<UserModel> users=new ArrayList<>();
	public List<UserModel> getUsers() {
		return null;
	}
	public void setUsers(List<UserModel> users) {
		this.users = users;
	}
	@Override
	public String toString() {
		return "RoleModel [name=" + name + " code=" + code + "]";
	}
}
