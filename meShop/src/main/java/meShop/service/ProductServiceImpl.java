package meShop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meShop.converter.ProductConverter;
import meShop.dto.CategoryDTO;
import meShop.dto.ProductDTO;
import meShop.model.CategoryModel;
import meShop.model.ProductModel;
import meShop.model.UserModel;
import meShop.repository.CategoryRepository;
import meShop.repository.ProductRepository;
import meShop.repository.UserRepository;
@Service
public class ProductServiceImpl implements ProductService{
	@Autowired ProductRepository productRepository; 
	@Autowired CategoryRepository categoryRepository; 
	@Override
	public List<ProductDTO> getAllProducts() {
		List<ProductDTO> productDTOs=new ArrayList<>();
		List<ProductModel> productModels= productRepository.findAll();
		
		productModels.forEach(productModel->{
			ProductDTO productDTO=productConverter.toDTO(productModel);
			productDTOs.add(productDTO);
		});
		return productDTOs;
	}
	@Autowired
	private ProductConverter productConverter;
	

	@Override
	public ProductModel getProductById(long id) {
		Optional<ProductModel> optional=productRepository.findById(id);
		ProductModel product=null;
		if(optional.isPresent()) product=optional.get();
		else throw new RuntimeException(" User not found for id :: " + id);
		return product;
	}

	@Override
	public void deleteProductById(long id) {
		// TODO Auto-generated method stub
		productRepository.deleteById(id);
	}



	@Override
	public void saveProduct(ProductDTO productDTO) {
		ProductModel productModel=new ProductModel();
		if(!productDTO.getId().isEmpty()){
				System.out.println("id: "+productDTO.getId()+"abc");
				ProductModel oldProductModel=null;
				Optional<ProductModel> optional=productRepository.findById((long) (Integer.parseInt(productDTO.getId())));
				if(optional.isPresent()) {
					oldProductModel=optional.get();
					productModel=productConverter.toModel(productDTO,oldProductModel);
				}
			System.out.println("lam tren");
		} 
		else 
		{
			System.out.println("lam duoi");
			productModel=productConverter.toModel(productDTO);
		}
		CategoryModel categoryModel = null;
			Optional<CategoryModel> optional=categoryRepository.findById((long) (Integer.parseInt(productDTO.getCategoryId())));
		if(optional.isPresent()) categoryModel=optional.get();
		productModel.setCategory(categoryModel);
		System.out.println(productModel);
		productRepository.save(productModel);
	}

	@Override
	public List<ProductModel> getProductByListId(List<Long> ids) {
		return productRepository.getProductByListId(ids);
	}


}
