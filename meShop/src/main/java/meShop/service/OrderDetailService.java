package meShop.service;

import java.util.List;

import meShop.dto.OrderDetailDTO;
import meShop.model.OrderDetailModel;

public interface OrderDetailService {
	List<OrderDetailDTO> getAllOrderDetails();
	List<OrderDetailDTO> getOrderDetailByOrderId(long id);
	Long saveOrderDetail(OrderDetailDTO orderDetailDTO);

	OrderDetailModel getOrderDetailById(long id);

	void deleteOrderDetailById(long id);
}
