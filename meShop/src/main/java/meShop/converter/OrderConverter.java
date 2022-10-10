package meShop.converter;

import org.springframework.stereotype.Component;

import meShop.dto.OrderDTO;
import meShop.dto.ProductDTO;
import meShop.model.OrderModel;
import meShop.model.ProductModel;
@Component
public class OrderConverter {
	public OrderModel toModel(OrderDTO dto) {
		OrderModel orderModel = new OrderModel();
		orderModel.setOrderDate(dto.getOrderDate());
		return orderModel;
	}
	
	public  OrderDTO toDTO(OrderModel model) {
		OrderDTO dto = new OrderDTO();
		if (model.getId() != null) {
			dto.setId(model.getId().toString());
		}
		if(model.getUserModel()!=null) {
			dto.setUserId((model.getUserModel().getId().toString()));
		}
		dto.setState(model.getState());
		dto.setOrderDate(model.getOrderDate());
		dto.setCreatedDate(model.getCreatedDate());
		dto.setCreatedBy(model.getCreatedBy());
		dto.setModifiedDate(model.getModifiedDate());
		dto.setModifiedBy(model.getModifiedBy());
		return dto;
	}

	public OrderModel toModel(OrderDTO dto, OrderModel oldModel) {
		// TODO Auto-generated method stub
		oldModel.setOrderDate(dto.getOrderDate());
		return oldModel;
	}
}
