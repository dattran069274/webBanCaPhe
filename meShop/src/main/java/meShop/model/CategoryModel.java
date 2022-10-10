package meShop.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name = "category")
public class CategoryModel extends BaseModel{
	@OneToMany(mappedBy ="category")
	private List<ProductModel> products=new ArrayList<>();
	private String name;
	public List<ProductModel> getProducts() {
		return products;
	}
	public void setProducts(List<ProductModel> products) {
		this.products = products;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CategoryModel() {
		super();
	}
	
}
