package meShop.service;

import java.util.List;

import meShop.dto.CategoryDTO;
import meShop.dto.OrderDTO;
import meShop.model.CategoryModel;
import meShop.model.OrderModel;

public interface OrderService {
	List<OrderDTO> getAllOrders();
	Long saveOrder(OrderDTO orderDTO);
	Long saveOrder(OrderDTO orderDTO,int action);
	void saveOrder(OrderModel orderModel,int action);
	OrderModel getOrderById(long id); 
	void deleteOrderById(long id);
	List<OrderModel> findOrdersByUserId(String userId);
	List<OrderModel> findAllByUserId(String userId);
	List<OrderDTO> getOrderByStateCode(int i);
	OrderModel findByPaypalOrderId(String orderId);
}
