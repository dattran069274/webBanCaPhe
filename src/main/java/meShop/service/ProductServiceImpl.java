package meShop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	public List<ProductModel> getProductByListLimit4() {
		return productRepository.getProductByListLimit4();
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

	@Override
	public void deleteProductByCategoryId(long id) {
		productRepository.deleteByCategoryId(id);
		
	}

	@Override
	public List<ProductDTO> getProductByCateId(long cateId) {
		List<ProductModel> models= productRepository.getProductByCateId(cateId);
		List<ProductDTO> dtos=new ArrayList<>();
		models.forEach(model->{dtos.add(productConverter.toDTO(model));});
		return dtos;
	}
	

	@Override
	public void save(ProductModel productModel) {
		productRepository.save(productModel);
		
	}

	
	@Override
	public int getTotalPage(int pageSize) {
		List<ProductModel> productModels= productRepository.findAll();
		int size=productModels.size();
		if(size%pageSize==0)
		return size/pageSize;
		else return size/pageSize+1;
	}

	@Override
	public int getTotalPage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ProductDTO> getProducts(int pageNum, int pageSize, String sortBy) {
		Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(sortBy));
		 
        Page<ProductModel> pagedResult = productRepository.findAll(paging);
         
        if(pagedResult.hasContent()) {
            List<ProductDTO> ProductDTOs=new ArrayList<>();
            List<ProductModel> productModels=pagedResult.getContent();
            productModels.forEach(product->{
            	ProductDTOs.add(productConverter.toDTO(product));
            });
            return ProductDTOs;
        } else {
            return new ArrayList<ProductDTO>();
        }
	}
	@Override
	public List<ProductDTO> getProductsByKey(String key,int pageNo,int pageSize,String sortBy) {
		// List<ProductModel> models= productRepository.getProductsByKey(key);
		//List<ProductDTO> dtos=new ArrayList<>();
		//models.forEach(model->{dtos.add(productConverter.toDTO(model));});
		//return dtos;
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		 
        Page<ProductModel> pagedResult = productRepository.findByTitleContaining(key,paging);
         
        if(pagedResult.hasContent()) {
            List<ProductDTO> ProductDTOs=new ArrayList<>();
            List<ProductModel> productModels=pagedResult.getContent();
            productModels.forEach(product->{
            	ProductDTOs.add(productConverter.toDTO(product));
            });
            return ProductDTOs;
        } else {
            return new ArrayList<ProductDTO>();
        }
	}
	@Override
	public List<ProductDTO> getProductsByKey(String key) {
		List<ProductModel> models= productRepository.getProductsByKey(key);
		List<ProductDTO> dtos=new ArrayList<>();
		models.forEach(model->{dtos.add(productConverter.toDTO(model));});
		return dtos;
	}
	@Override
	public int getTotalPage(int pageSize, String key) {
		List<ProductModel> productModels= productRepository.getProductsByKey(key);
		int size=productModels.size();
		if(size%pageSize==0)
		return size/pageSize;
		else return size/pageSize+1;
	}
	@Override
	public int getTotalPage(int pageSize, long cateId) {
		List<ProductModel> productModels= productRepository.getProductByCateId(cateId);
		int size=productModels.size();
		if(size%pageSize==0)
		return size/pageSize;
		else return size/pageSize+1;
	}
	@Override
	public List<ProductDTO> getProductByCateIdPage(long capheHat,int pageNo,int pageSize,String sortBy) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		 
        Page<ProductModel> pagedResult = productRepository.findByCategory_id(capheHat,paging);
         
        if(pagedResult.hasContent()) {
            List<ProductDTO> ProductDTOs=new ArrayList<>();
            List<ProductModel> productModels=pagedResult.getContent();
            productModels.forEach(product->{
            	ProductDTOs.add(productConverter.toDTO(product));
            });
            return ProductDTOs;
        } else {
            return new ArrayList<ProductDTO>();
        }
	}

}
