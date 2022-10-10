package meShop.service;

import java.util.List;

import meShop.dto.ProductDTO;
import meShop.model.CategoryModel;
import meShop.model.ProductModel;

public interface ProductService {
	List<ProductDTO> getAllProducts();
	void saveProduct(ProductDTO productDTO);
	ProductModel getProductById(long id); 
	void deleteProductById(long id);
	List<ProductModel> getProductByListId(List<Long> ids);
}
