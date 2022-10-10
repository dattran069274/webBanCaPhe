package meShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import meShop.model.ProductModel;
@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long>{
	@Query(value = "Select s from ProductModel s where s.id In :ids")
	List<ProductModel> getProductByListId(List<Long> ids);
}
