package meShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import meShop.model.OrderDetailModel;
import meShop.model.OrderModel;
import meShop.model.ProductModel;
import meShop.model.UserModel;
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetailModel, Long>{
	@Query("SELECT o FROM OrderDetailModel o WHERE o.order.id = ?1")
    public List<OrderDetailModel> findByOrderId(long orderId);
}
