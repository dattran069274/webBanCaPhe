package meShop.service;

import java.util.List;

import meShop.model.UserModel;

public interface UserService {
	List<UserModel> getAllUser();
	void saveUser(UserModel usermodel);
	UserModel getUserById(long id); 
	void deleteUserById(long id);
	UserModel getUserByUserName(String userName);;
}	
