package meShop.converter;

import java.util.Base64;

import org.springframework.stereotype.Component;

import meShop.dto.ProductDTO;
import meShop.model.ProductModel;
@Component
public class ProductConverter {
	public ProductModel toModel(ProductDTO dto) {
		ProductModel productModel = new ProductModel();
		productModel.setTitle(dto.getTitle());
		productModel.setDescription(dto.getDescription());
		productModel.setSaled(dto.getSaled());
		productModel.setPrice(dto.getPrice());
		productModel.setImage(dto.getImage());
		productModel.setImage2(dto.getImage2());
		productModel.setImage3(dto.getImage3());
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
		dto.setStrImage(Base64.getEncoder().encodeToString(model.getImage()));
		dto.setStrImage2(Base64.getEncoder().encodeToString(model.getImage2()));
		dto.setStrImage3(Base64.getEncoder().encodeToString(model.getImage3()));
		dto.setPrice(model.getPrice());
		dto.setTitle(model.getTitle());
		dto.setImage(model.getImage());
		dto.setImage2(model.getImage2());
		dto.setImage3(model.getImage3());
		dto.setSaled(model.getSaled());
		dto.setDescription(model.getDescription());
		dto.setCreatedDate(model.getCreatedDate());
		dto.setCreatedBy(model.getCreatedBy());
		dto.setModifiedDate(model.getModifiedDate());
		dto.setModifiedBy(model.getModifiedBy());
		dto.setCategoryName(model.getCategory().getName());
		return dto;
	}

	public ProductModel toModel(ProductDTO productDTO, ProductModel oldProductModel) {
		// TODO Auto-generated method stub
		oldProductModel.setTitle(productDTO.getTitle());
		oldProductModel.setDescription(productDTO.getDescription());
		oldProductModel.setPrice(productDTO.getPrice());
		oldProductModel.setImage(productDTO.getImage());
		oldProductModel.setImage2(productDTO.getImage2());
		oldProductModel.setImage3(productDTO.getImage3());
		oldProductModel.setSaled(productDTO.getSaled());
		return oldProductModel;
	}
}
