package meShop.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.Mapping;

import meShop.model.OrderStateModel;
import meShop.model.UserModel;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
@Repository

public interface OrderStateRepository extends JpaRepository<OrderStateModel, Long>{
	OrderStateModel findByCode(int code);
}
