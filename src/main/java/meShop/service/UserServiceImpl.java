package meShop.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meShop.dto.UserDTO;
import meShop.model.UserModel;
import meShop.repository.RoleRepository;
import meShop.repository.UserRepository;
@Service
public class UserServiceImpl implements UserService{
	@Autowired UserRepository userRepository; 
	@Autowired RoleRepository roleRepository; 
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
	@Override
	public void updateResetPassword(String token, String email) throws Exception {
		UserModel user=userRepository.findByEmail(email);
		if(user!=null) {
			user.setResetPasswordToken(token);
			userRepository.save(user);
		} else {
			throw new Exception("Could not find any user with email "+email);
		}
		
	}
	@Override
	public UserModel get(String resetPasswordToken) {
		return userRepository.findByResetPasswordToken(resetPasswordToken);
		
	}

	@Override
	public void updatePassword(UserModel user, String newPassword) {
		try {
			String encriptPass=UserDTO.toHexString(UserDTO.getSHA(newPassword));
			user.setPassword(encriptPass);
			user.setResetPasswordToken(null);
			userRepository.save(user);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
