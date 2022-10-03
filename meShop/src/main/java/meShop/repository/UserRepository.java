package meShop.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.Mapping;

import meShop.model.UserModel;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
@Repository

public interface UserRepository extends JpaRepository<UserModel, Long>{
//	@Modifying
//    @Query("UPDATE user u SET u.userName = :userName,u.fullName = :fullName,u.phone = :phone,u.email=:email,u.address=:address WHERE u.id = :userId")
//    int update(@Param("userId") long userId, @Param("userName") String userName,
//    		@Param("fullName") String fullName,
//    		@Param("phone") String phone,@Param("email") String email
//    		,@Param("address") String address);
	@Modifying
	@Query("UPDATE UserModel u  SET u.userName = :userName WHERE u.id = :userId")
    void  update(@Param("userId") long userId, @Param("userName") String userName);
	@Query("SELECT u FROM UserModel u WHERE u.email = ?1")
    public UserModel findByEmail(String email);
}
