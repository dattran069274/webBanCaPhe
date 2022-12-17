package meShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import meShop.model.OrderDetailModel;
import meShop.model.OrderModel;
import meShop.model.ProductModel;
@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Long>{
	@Query("SELECT o FROM OrderModel o WHERE o.userModel.id = ?1")
    public List<OrderModel> findOrdersByUserId(long userId);
    @Query("SELECT o FROM OrderModel o WHERE o.state.code = ?1")
	public List<OrderModel> findByStateCode(int i);
    public OrderModel findByPaypalOrderId(String orderId);
}
