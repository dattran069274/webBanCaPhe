package meShop.dto;


import java.util.Arrays;

import meShop.model.CategoryModel;

public class ProductDTO extends AbstractDTO<ProductDTO>{
	private String categoryId;
	private String title;
	private Double price;
	private byte[] image;
	private String description;
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	@Override
	public String toString() {
		return "ProductDTO [categoryId=" + categoryId + ", title=" + title + ", price=" + price + ", image="
				+ Arrays.toString(image) + ", description=" + description + "]";
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
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
