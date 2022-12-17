package meShop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import meShop.converter.CategoryConverter;
import meShop.dto.CategoryDTO;
import meShop.model.CategoryModel;
import meShop.model.UserModel;
import meShop.repository.CategoryRepository;
import meShop.repository.UserRepository;
@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired CategoryConverter categoryConverter;
	@Autowired CategoryRepository categoryRepository; 
	@Override
	public List<CategoryDTO> getAllCategories() {
		List<CategoryDTO> categoryDTOs=new ArrayList<>();
		List<CategoryModel> categoryModels= categoryRepository.findAll();
		categoryModels.forEach(categoryModel->{
			CategoryDTO categoryDTO=categoryConverter.toDTO(categoryModel);
			categoryDTOs.add(categoryDTO);
		});
		return categoryDTOs;
	}

	@Override
	public void saveCategory(CategoryModel categoryModel) {
		categoryRepository.save(categoryModel);	
	}

	@Override
	public CategoryModel getCategoryById(long id) {
		Optional<CategoryModel> optional=categoryRepository.findById(id);
		CategoryModel category=null;
		if(optional.isPresent()) category=optional.get();
		else throw new RuntimeException(" User not found for id :: " + id);
		return category;
	}

	@Override
	public void deletecategoryById(long id) {
		// TODO Auto-generated method stub
		categoryRepository.deleteById(id);
	}

	@Override
	public void save(CategoryModel categoryModel) {
		// TODO Auto-generated method stub
		categoryRepository.save(categoryModel);
	}

	@Override
	public CategoryModel getCategoryByName(String name) {
		
		return categoryRepository.findByName(name);
	}

	


}
