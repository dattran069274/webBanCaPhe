//package meShop.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import meShop.model.CustomUserDetails;
//import meShop.model.UserModel;
//import meShop.repository.UserRepository;
// 
//public class CustomUserDetailsService implements UserDetailsService {
// 
//    @Autowired
//    private UserRepository userRepo;
//     
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserModel user = userRepo.findByEmail(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        return new CustomUserDetails(user);
//    }
// 
//}
