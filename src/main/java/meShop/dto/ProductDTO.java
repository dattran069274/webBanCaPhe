package meShop.dto;


import java.util.Arrays;

import meShop.model.CategoryModel;

public class ProductDTO extends AbstractDTO<ProductDTO>{
	private String categoryId;
	private String title;
	private Double price;
	private byte[] image;
	private byte[] image2;
	private byte[] image3;
	private String categoryName;
	private String strImage;
	private String strImage2;
	private String strImage3;
	public String getStrImage() {
		return strImage;
	}
	public void setStrImage(String strImage) {
		this.strImage = strImage;
	}
	public String getStrImage2() {
		return strImage2;
	}
	public void setStrImage2(String strImage2) {
		this.strImage2 = strImage2;
	}
	public String getStrImage3() {
		return strImage3;
	}
	public void setStrImage3(String strImage3) {
		this.strImage3 = strImage3;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public byte[] getImage2() {
		return image2;
	}
	public void setImage2(byte[] image2) {
		this.image2 = image2;
	}
	public byte[] getImage3() {
		return image3;
	}
	public void setImage3(byte[] image3) {
		this.image3 = image3;
	}
	private String description;
	private int saled;
	public int getSaled() {
		return saled;
	}
	public void setSaled(int saled) {
		this.saled = saled;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	
	
	@Override
	public String toString() {
		return "ProductDTO [categoryId=" + categoryId + ", title=" + title + ", price=" + price + ", image="
				+ Arrays.toString(image) + ", image2=" + Arrays.toString(image2) + ", image3=" + Arrays.toString(image3)
				+ ", categoryName=" + categoryName + ", description=" + description + ", saled=" + saled + ", getId()="
				+ getId() + "]";
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
