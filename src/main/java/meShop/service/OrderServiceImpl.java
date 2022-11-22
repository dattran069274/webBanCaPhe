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
import meShop.model.ProductModel;
import meShop.model.UserModel;
import meShop.repository.OrderRepository;
import meShop.repository.UserRepository;
@Service
public class OrderServiceImpl implements OrderService{
	@Autowired OrderRepository orderRepository;
	@Autowired OrderConverter orderConverter;
	@Autowired UserRepository userRepository;
	@Override
	public List<OrderDTO> getAllOrders() {
		List<OrderModel> models=orderRepository.findAll();
		List<OrderDTO> dtos=new ArrayList<>();
		models.forEach(model->{
			dtos.add(orderConverter.toDTO(model));
		});
		return dtos;
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
	public List<OrderModel> findLastOneByUserId(String userId) {
		return orderRepository.findLastOneByUserId((long) Integer.valueOf(userId));
	}

	@Override
	public List<OrderModel> findAllByUserId(String userId) {
		return null;
	}

	@Override
	public void saveOrder(OrderModel orderModel) {
		orderRepository.save(orderModel);
	}

}
