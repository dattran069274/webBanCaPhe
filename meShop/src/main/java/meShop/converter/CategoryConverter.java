package meShop.converter;

import org.springframework.stereotype.Component;

import meShop.dto.CategoryDTO;
import meShop.dto.ProductDTO;
import meShop.model.CategoryModel;
import meShop.model.ProductModel;
@Component
public class CategoryConverter {
	public CategoryModel toModel(CategoryDTO dto) {
		CategoryModel categoryModel = new CategoryModel();
		categoryModel.setName(dto.getName());
		return categoryModel;
	}
	
	public  CategoryDTO toDTO(CategoryModel model) {
		CategoryDTO dto = new CategoryDTO();
		if (model.getId()!= null) {
			dto.setId(model.getId().toString());
		}
		dto.setName(model.getName());
		dto.setCreatedDate(model.getCreatedDate());
		dto.setCreatedBy(model.getCreatedBy());
		dto.setModifiedDate(model.getModifiedDate());
		dto.setModifiedBy(model.getModifiedBy());
		return dto;
	}

	public CategoryModel toModel(CategoryDTO categoryDTO, CategoryModel oldCategoryModel) {
		// TODO Auto-generated method stub
		oldCategoryModel.setName(categoryDTO.getName());
		return oldCategoryModel;
	}
}
