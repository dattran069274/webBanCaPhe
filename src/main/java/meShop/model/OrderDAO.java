package meShop.model;

import org.springframework.data.repository.CrudRepository;

public interface OrderDAO extends CrudRepository<Order, Long> {

    Order findByPaypalOrderId(String paypalOrderId);
}
