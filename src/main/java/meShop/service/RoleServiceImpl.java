package meShop.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meShop.dto.UserDTO;
import meShop.model.RoleModel;
import meShop.model.UserModel;
import meShop.repository.RoleRepository;
import meShop.repository.UserRepository;
@Service
public class RoleServiceImpl implements RoleService{
	@Autowired RoleRepository roleRepository;

	@Override
	public RoleModel finByCode(int code) {
		return roleRepository.findByCode(code);
	} 

}
