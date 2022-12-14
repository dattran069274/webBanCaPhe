package meShop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meShop.model.UserModel;
import meShop.repository.UserRepository;
@Service
public class UserServiceImpl implements UserService{
	@Autowired UserRepository userRepository; 
	@Override
	public List<UserModel> getAllUser() {
		
		return userRepository.findAll();
	}

	@Override
	public void saveUser(UserModel usermodel) {
		userRepository.save(usermodel);
		
	}
	@Override
	public boolean isExsist(String key,String value) {
		switch(key){
			case "email":
			return userRepository.existsByEmail(value);
			case "userName":
			return userRepository.existsByUserName(value);
		}
		return false;
	}
	@Override
	public UserModel getUserById(long id) {
		Optional<UserModel> optional=userRepository.findById(id);
		UserModel user=null;
		if(optional.isPresent()) user=optional.get();
		else throw new RuntimeException(" User not found for id :: " + id);
		return user;
	}

	@Override
	public void deleteUserById(long id) {
		userRepository.deleteById(id);
	}

	@Override
	public UserModel getUserByUserName(String userName) {
		// TODO Auto-generated method stub
		return userRepository.findByUserName(userName);
	}

}
