package meShop.converter;

import org.springframework.stereotype.Component;

import meShop.dto.ProductDTO;
import meShop.model.ProductModel;
@Component
public class ProductConverter {
	public ProductModel toModel(ProductDTO dto) {
		ProductModel productModel = new ProductModel();
		productModel.setTitle(dto.getTitle());
		productModel.setDescription(dto.getDescription());
		if(dto.getPrice()==null) productModel.setPrice((double)123);
		else productModel.setPrice(dto.getPrice());
		productModel.setImage(dto.getImage());
		return productModel;
	}
	
	public  ProductDTO toDTO(ProductModel model) {
		ProductDTO dto = new ProductDTO();
		if (model.getId() != null) {
			dto.setId(model.getId().toString());
		}
		if(model.getCategory()!=null) {
			dto.setCategoryId(model.getCategory().getId().toString());
		}
		dto.setPrice(model.getPrice());
		dto.setTitle(model.getTitle());
		dto.setImage(model.getImage());
		dto.setDescription(model.getDescription());
		dto.setCreatedDate(model.getCreatedDate());
		dto.setCreatedBy(model.getCreatedBy());
		dto.setModifiedDate(model.getModifiedDate());
		dto.setModifiedBy(model.getModifiedBy());
		return dto;
	}

	public ProductModel toModel(ProductDTO productDTO, ProductModel oldProductModel) {
		// TODO Auto-generated method stub
		oldProductModel.setTitle(productDTO.getTitle());
		oldProductModel.setDescription(productDTO.getDescription());
		oldProductModel.setPrice(productDTO.getPrice());
		oldProductModel.setImage(productDTO.getImage());
		return oldProductModel;
	}
}
