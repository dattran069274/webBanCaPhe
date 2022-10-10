package meShop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meShop.converter.OrderConverter;
import meShop.converter.OrderDetailConverter;
import meShop.dto.OrderDTO;
import meShop.dto.OrderDetailDTO;
import meShop.model.CategoryModel;
import meShop.model.OrderDetailModel;
import meShop.model.OrderModel;
import meShop.model.ProductModel;
import meShop.model.UserModel;
import meShop.repository.OrderDetailRepository;
import meShop.repository.OrderRepository;
import meShop.repository.ProductRepository;
import meShop.repository.UserRepository;
@Service
public class OrderDetailServiceImpl implements OrderDetailService{
	@Autowired OrderDetailRepository orderDetailRepository;
	@Autowired OrderRepository orderRepository;
	@Autowired OrderDetailConverter orderDetailConverter;
	@Autowired UserRepository userRepository;
	@Autowired ProductRepository productRepository;
	@Override
	public List<OrderDetailDTO> getAllOrderDetails() {
		List<OrderDetailModel> models=orderDetailRepository.findAll();
		List<OrderDetailDTO> dtos=new ArrayList<>();
		models.forEach(model->{
			dtos.add(orderDetailConverter.toDTO(model));
		});
		return dtos;
	}

	@Override
	public Long saveOrderDetail(OrderDetailDTO orderDTO) {
		OrderDetailModel orderDetailModel=new OrderDetailModel();
		orderDetailModel=orderDetailConverter.toModel(orderDTO);
		
		ProductModel productModel = null;
		Optional<ProductModel> optional=productRepository.findById((long) (Integer.parseInt(orderDTO.getProductId())));
		if(optional.isPresent()) productModel=optional.get();
		orderDetailModel.setProductModel(productModel);
		
		OrderModel orderModel = null;
		Optional<OrderModel> optional2=orderRepository.findById((long) (Integer.parseInt(orderDTO.getOrderId())));
		if(optional2.isPresent()) orderModel=optional2.get();
		orderDetailModel.setOrder(orderModel);
		return orderDetailRepository.save(orderDetailModel).getId();
	}

	@Override
	public OrderDetailModel getOrderDetailById(long id) {
		// TODO Auto-generated method stub
		Optional<OrderDetailModel> optional=orderDetailRepository.findById(id);
		OrderDetailModel order=null;
		if(optional.isPresent()) order=optional.get();
		else throw new RuntimeException(" User not found for id :: " + id);
		return order;
	}

	

	@Override
	public void deleteOrderDetailById(long id) {
		orderDetailRepository.deleteById(id);
		
	}

	@Override
	public List<OrderDetailDTO> getOrderDetailByOrderId(long id) {
		List<OrderDetailModel> models=orderDetailRepository.findByOrderId(id);
		List<OrderDetailDTO> dtos=new ArrayList<>();
		models.forEach(model->{
			dtos.add(orderDetailConverter.toDTO(model));
		});
		return dtos;
	}

	

}
