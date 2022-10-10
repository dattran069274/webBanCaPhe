package meShop.converter;

import org.springframework.stereotype.Component;

import meShop.dto.OrderDTO;
import meShop.dto.OrderDetailDTO;
import meShop.dto.ProductDTO;
import meShop.model.OrderDetailModel;
import meShop.model.OrderModel;
import meShop.model.ProductModel;
@Component
public class OrderDetailConverter {
	public OrderDetailModel toModel(OrderDetailDTO dto) {
		OrderDetailModel orderDetailModel = new OrderDetailModel();
		orderDetailModel.setPrice(dto.getPrice());
		orderDetailModel.setQuanity(dto.getQuantity());
		orderDetailModel.setAmount(dto.getAmount());
		return orderDetailModel;
	}
	
	public  OrderDetailDTO toDTO(OrderDetailModel model) {
		OrderDetailDTO dto = new OrderDetailDTO();
		dto.setProductId(String.valueOf(model.getProductModel().getId()));
		dto.setAmount(model.getAmount());
		dto.setPrice(model.getPrice());
		dto.setQuantity(model.getQuanity());
		return dto;
	}

	public OrderDetailModel toModel(OrderDetailDTO dto, OrderDetailModel oldModel) {
		// TODO Auto-generated method stub
		oldModel.setAmount(dto.getAmount());
		oldModel.setPrice(dto.getPrice());
		oldModel.setQuanity(dto.getQuantity());
		return oldModel;
	}
}
