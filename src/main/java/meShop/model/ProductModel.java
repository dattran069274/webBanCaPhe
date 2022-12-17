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
	@Lob
	@Column(name = "image2", length = Integer.MAX_VALUE, nullable = true)
	private byte[] image2;
	@Lob
	@Column(name = "image3", length = Integer.MAX_VALUE, nullable = true)
	private byte[] image3;
	@Column(name="description",length = 2000, nullable = true)
	String description;
	@Column(name="saled")
	private int saled;
	public CategoryModel getCategory() {
		return category;
	}
	public int getSaled(){return saled;}
	public void setSaled(int saled){ this.saled=saled;}
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
	public  byte[] getImage2() {
		return image2;
	}
	public void setImage2(byte[] image3) {
		image2 = image3;
	}
	public  byte[] getImage3() {
		return image3;
	}
	public void setImage3(byte[] image4) {
		image3 = image4;
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
