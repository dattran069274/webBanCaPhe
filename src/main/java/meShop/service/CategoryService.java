package meShop.service;

import java.util.List;

import meShop.dto.CategoryDTO;
import meShop.model.CategoryModel;

public interface CategoryService {
	List<CategoryDTO> getAllCategories();
	void saveCategory(CategoryModel categoryModel);
	CategoryModel getCategoryById(long id); 
	void deletecategoryById(long id);
	void save(CategoryModel categoryModel);
	CategoryModel getCategoryByName(String name);	
}
