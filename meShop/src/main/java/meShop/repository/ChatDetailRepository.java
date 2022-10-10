package meShop.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.Mapping;

import meShop.model.ChatDetailModel;
import meShop.model.UserModel;

import java.util.List;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
@Repository

public interface ChatDetailRepository extends JpaRepository<ChatDetailModel, Long>{
	@Query("SELECT c FROM ChatDetailModel c WHERE c.senderId = ?1")
	List<ChatDetailModel> getAllChatDetailsBySenderId(long senderId);
//	@Modifying
//	@Query("UPDATE UserModel u  SET u.userName = :userName WHERE u.id = :userId")
//    void  update(@Param("userId") long userId, @Param("userName") String userName);
//	@Query("SELECT u FROM UserModel u WHERE u.email = ?1")
//    public UserModel findByEmail(String email);
//	@Query("SELECT u FROM UserModel u WHERE u.userName = ?1")
//    public UserModel findByUserName(String userName);

}
