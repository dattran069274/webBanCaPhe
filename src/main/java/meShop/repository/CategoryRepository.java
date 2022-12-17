package meShop.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.Mapping;

import meShop.model.CategoryModel;
import meShop.model.UserModel;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, Long>{

	@Query("SELECT c FROM CategoryModel c WHERE c.name = ?1")
    public CategoryModel findByName(String name);
}
