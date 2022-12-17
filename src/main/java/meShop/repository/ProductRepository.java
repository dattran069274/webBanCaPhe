package meShop.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import meShop.model.CategoryModel;
import meShop.model.ProductModel;
@Repository
@Transactional
public interface ProductRepository extends JpaRepository<ProductModel, Long>{
	@Query(value = "Select s from ProductModel s where s.id In :ids")
	List<ProductModel> getProductByListId(List<Long> ids);
	@Query(value = "Select * from products  ORDER BY saled DESC limit 4",nativeQuery = true)
	List<ProductModel> getProductByListLimit4();
	@Query("Delete FROM ProductModel u WHERE u.category.id = ?1")
	@Modifying
	void deleteByCategoryId(long id);
	@Query(value = "Select s from ProductModel s where s.category.id =?1")
	List<ProductModel> getProductByCateId(long cateId);
	
	@Query(value = "Select s from ProductModel s where s.title like %:key%")
	List<ProductModel> getProductsByKey(String key);
	Page<ProductModel> findByTitleContaining(String title, Pageable pageable);
	Page<ProductModel> findByCategory_id(long id, Pageable pageable);
}
