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
	List<ProductModel> getProductByListLimit4();
	void deleteProductByCategoryId(long id);
	List<ProductDTO> getProductByCateId(long capheHat);
	List<ProductDTO> getProductByCateIdPage(long capheHat,int pageNo,int pageSize,String sortBy);
	void save(ProductModel productModel);
	List<ProductDTO> getProductsByKey(String key,int pageNo,int pageSize,String sortBy);
	List<ProductDTO> getProductsByKey(String key);
	int getTotalPage();
	int getTotalPage(int pageSize);
	int getTotalPage(int pageSize,String key);
	int getTotalPage(int pageSize,long cateId);
	List<ProductDTO> getProducts(int pageNum, int pageSize, String sortBy);
}
