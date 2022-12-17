package meShop.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import meShop.dto.OrderDTO;
import meShop.dto.ProductDTO;
import meShop.model.OrderModel;
import meShop.model.OrderStateModel;
import meShop.model.OrderStatus;
import meShop.model.ProductModel;
import meShop.repository.OrderStateRepository;
@Component
public class OrderConverter {
	@Autowired OrderStateRepository  orderStateRepository;
	public OrderModel toModel(OrderDTO dto) {
		OrderModel orderModel = new OrderModel();
		orderModel.setOrderDate(dto.getOrderDate());
		orderModel.setReceiverAddress(dto.getReceiverAddress());
		orderModel.setReceiverName(dto.getReceiverName());
		OrderStateModel state=orderStateRepository.findByCode(dto.getState());
		orderModel.setState(state);
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
		if(model.getPaypalOrderStatus()!=null){
			dto.setIsPaid(model.getPaypalOrderStatus().equals(OrderStatus.APPROVED.toString())?true:false);
		}
		dto.setMessageError(model.getMessageError());
		dto.setShipper(model.getShipper());
		dto.setState(model.getState().getCode());
		dto.setStateName(model.getState().getName());
		dto.setOrderDate(model.getOrderDate());
		dto.setCreatedDate(model.getCreatedDate());
		dto.setCreatedBy(model.getCreatedBy());
		dto.setModifiedDate(model.getModifiedDate());
		dto.setModifiedBy(model.getModifiedBy());
		dto.setReceiverAddress(model.getReceiverAddress());
		dto.setReceiverName(model.getReceiverName());
		return dto;
	}

	public OrderModel toModel(OrderDTO dto, OrderModel oldModel) {
		// TODO Auto-generated method stub
		oldModel.setOrderDate(dto.getOrderDate());
		return oldModel;
	}
}