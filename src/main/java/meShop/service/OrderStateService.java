package meShop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import meShop.dto.CategoryDTO;
import meShop.dto.OrderDTO;
import meShop.model.CategoryModel;
import meShop.model.OrderModel;
import meShop.model.OrderStateModel;
@Service
public interface OrderStateService {
	OrderStateModel findByCode(int code); 
}
