package meShop.service;

import java.util.List;

import meShop.dto.CategoryDTO;
import meShop.dto.OrderDTO;
import meShop.model.CategoryModel;
import meShop.model.OrderModel;

public interface OrderService {
	List<OrderDTO> getAllOrders();
	Long saveOrder(OrderDTO orderDTO);
	void saveOrder(OrderModel orderModel);
	OrderModel getOrderById(long id); 
	void deleteOrderById(long id);
	List<OrderModel> findLastOneByUserId(String userId);
	List<OrderModel> findAllByUserId(String userId);
}
