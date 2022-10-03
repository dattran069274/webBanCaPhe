package meShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import meShop.model.ProductModel;
@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long>{
}
