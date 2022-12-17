package meShop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meShop.converter.OrderConverter;
import meShop.dto.OrderDTO;
import meShop.model.CategoryModel;
import meShop.model.OrderModel;
import meShop.model.OrderStateModel;
import meShop.model.OrderStatus;
import meShop.model.ProductModel;
import meShop.model.UserModel;
import meShop.repository.OrderRepository;
import meShop.repository.OrderStateRepository;
import meShop.repository.UserRepository;
@Service
public class OrderServiceImpl implements OrderService{
	@Autowired OrderRepository orderRepository;
	@Autowired OrderConverter orderConverter;
	@Autowired UserRepository userRepository;
	@Autowired OrderStateRepository orderStateRepository;
	@Override
	public List<OrderDTO> getAllOrders() {
		List<OrderModel> models=orderRepository.findAll();
		List<OrderDTO> dtos=new ArrayList<>();
		models.forEach(model->{
			if(model.getPaypalOrderId()!=null){
				if(model.getPaypalOrderStatus().equals(OrderStatus.APPROVED.toString())){
					dtos.add(orderConverter.toDTO(model));
				}
			}
			else
			dtos.add(orderConverter.toDTO(model));
		});
		return dtos;
	}

	@Override
	public Long saveOrder(OrderDTO orderDTO,int action) {
		OrderModel orderModel=new OrderModel();
		orderDTO.setState(action);
		if(orderDTO.getId()!=null&&(!orderDTO.getId().isEmpty())) {
			OrderModel oldProductModel=null;
			Optional<OrderModel> optional=orderRepository.findById((long) (Integer.parseInt(orderDTO.getId())));
			if(optional.isPresent()) {
				oldProductModel=optional.get();
				orderModel=orderConverter.toModel(orderDTO,oldProductModel);
			}
		} else orderModel=orderConverter.toModel(orderDTO);
		
		
		UserModel userModel = null;
		Optional<UserModel> optional=userRepository.findById((long) (Integer.parseInt(orderDTO.getUserId())));
		if(optional.isPresent()) userModel=optional.get();
		orderModel.setUserModel(userModel);
		return orderRepository.save(orderModel).getId();
	}

	@Override
	public OrderModel getOrderById(long id) {
		// TODO Auto-generated method stub
		Optional<OrderModel> optional=orderRepository.findById(id);
		OrderModel order=null;
		if(optional.isPresent()) order=optional.get();
		else throw new RuntimeException(" User not found for id :: " + id);
		return order;
	}

	@Override
	public void deleteOrderById(long id) {
		orderRepository.deleteById(id);		
	}

	@Override
	public List<OrderModel> findOrdersByUserId(String userId) {
		return orderRepository.findOrdersByUserId((long) Integer.valueOf(userId));
	}

	@Override
	public List<OrderModel> findAllByUserId(String userId) {
		return null;
	}

	

	@Override
	public void saveOrder(OrderModel orderModel,int action) {
		orderModel.setState(orderStateRepository.findByCode(action));
		orderRepository.save(orderModel);
	}

	@Override
	public List<OrderDTO> getOrderByStateCode(int i) {
		// TODO Auto-generated method stub
		
		List<OrderModel> orderModels= orderRepository.findByStateCode(i);
		List<OrderDTO> dtos=new ArrayList<>();
		orderModels.forEach(order->{dtos.add(orderConverter.toDTO(order));});
		return dtos;
	}

	@Override
	public OrderModel findByPaypalOrderId(String orderId) {
		// TODO Auto-generated method stub
		return orderRepository.findByPaypalOrderId(orderId);
	}
	@Override
	public Long saveOrder(OrderDTO orderDTO) {
		OrderModel orderModel=new OrderModel();
		if(orderDTO.getId()!=null&&(!orderDTO.getId().isEmpty())) {
			OrderModel oldProductModel=null;
			Optional<OrderModel> optional=orderRepository.findById((long) (Integer.parseInt(orderDTO.getId())));
			if(optional.isPresent()) {
				oldProductModel=optional.get();
				orderModel=orderConverter.toModel(orderDTO,oldProductModel);
			}
		} else orderModel=orderConverter.toModel(orderDTO);
		
		
		UserModel userModel = null;
		Optional<UserModel> optional=userRepository.findById((long) (Integer.parseInt(orderDTO.getUserId())));
		if(optional.isPresent()) userModel=optional.get();
		orderModel.setUserModel(userModel);
		//state
		OrderStateModel orderStateModel = null;
		orderStateModel=orderStateRepository.findByCode(orderDTO.getState());
		orderModel.setState(orderStateModel);
		return orderRepository.save(orderModel).getId();
	}

}
