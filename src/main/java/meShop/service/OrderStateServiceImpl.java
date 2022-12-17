package meShop.service;

import org.springframework.beans.factory.annotation.Autowired;

import meShop.model.OrderStateModel;
import meShop.repository.OrderStateRepository;

public class OrderStateServiceImpl implements OrderStateService{
@Autowired OrderStateRepository orderStateRepository;
	@Override
	public
	OrderStateModel findByCode(int code) {
		return orderStateRepository.findByCode(code);
	}

}
