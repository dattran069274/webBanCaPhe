package meShop.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name = "products")
public class ProductModel extends BaseModel{
	@ManyToOne
	@JoinColumn(name="category_id")
	private CategoryModel category;
	@Column(name = "title", length = 255, nullable = false)
	private String title;
	@Column(name = "price",nullable = false)
	Double price;
	@Lob
	@Column(name = "image", length = Integer.MAX_VALUE, nullable = true)
	private byte[] image;
	@Column(name="description")
	String description;
	public CategoryModel getCategory() {
		return category;
	}
	public void setCategory(CategoryModel category) {
		this.category = category;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public  byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image2) {
		image = image2;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "ProductModel [category=" + category + ", title=" + title + ", price=" + price + ", image="
				+ Arrays.toString(image) + ", description=" + description + "]";
	}
	
}
