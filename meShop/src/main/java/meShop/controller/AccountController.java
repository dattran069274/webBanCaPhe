package meShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import meShop.Constant.ConstantPageId;
import meShop.dto.UserDTO;
import meShop.model.UserModel;
import meShop.service.CookieService;
import meShop.service.SessionService;
import meShop.service.UserService;

@Controller
@RequestMapping("account")
public class AccountController {
	@Autowired
	SessionService sessionService;
	@Autowired
	CookieService cookieService;
	@Autowired UserService userService;
	@Autowired MainController mainController;
//	@GetMapping(value = "/showlogin")
//	String getLoginPage(@RequestParam("page") String page,Model model) {
//		model.addAttribute("page", page);
//		if(cookieService.get("USERNAME")!=null) {
//			System.out.println("tao nho may r "+cookieService.getValue("USERNAME", "AI eo biet"));
//			model.addAttribute("userName", cookieService.getValue("USERNAME", "AI eo biet"));
//			model.addAttribute("test", "ccc");
//			return "redirect:/"+page;
//		}
//		return "loginForm";
//	}
	
@GetMapping(value = "/login/{page}")
String getLoginPage(@PathVariable(value = "page",required= false) String page,Model model){
	model.addAttribute("page", ConstantPageId.HOME_PAGE);
	if(!sessionService.get("USERNAME","").equals("")) {
		System.out.println("login roi");
		if(page!=null&&page!=""){
			return "redirect:/"+page;
		} else {
			System.out.println("ve dau");
		}
	}
	return "Login";
}
@GetMapping(value = "/login")
String getLoginPage(Model model){
	model.addAttribute("page", ConstantPageId.HOME_PAGE);
	if(!sessionService.get("USERNAME","").equals("")) {
		return "redirect:/";
	}
	return "Login";
}
	ConstantPageId constantPageId;
	@PostMapping(value = "/showlogin")
	public String postLoginPage(@RequestParam(value="page",defaultValue = "/") int page,Model model) {
		model.addAttribute("page", page);
		if(cookieService.get("USERNAME")!=null) {
			System.out.println("tao nho may r "+cookieService.getValue("USERNAME", "AI eo biet"));
			model.addAttribute("userName", cookieService.getValue("USERNAME", "AI eo biet"));
			model.addAttribute("test", "ccc");
			return "redirect:/"+constantPageId.getPageUrl(page);
		}
		return "Login";
	}
	
	@GetMapping(value = "/register")
	String getRegisterPage(Model model){
		UserModel user=new UserModel();
		model.addAttribute("user", user);
		return "Regis";
	}
	String continueRegisterPage(Model model,UserModel user){
		model.addAttribute("user", user);
		return "Regis";
	}
	@PostMapping("/process_register")
	public String processRegister(Model model,UserModel user,@RequestParam("confirmPassword") String confirmPassword ) {
		System.out.println(user.toString());
		if(user.getPassword()==null||user.getPassword().length()<=6){
			System.out.println("Độ dài Mật khẩu");
			model.addAttribute("user", user);
			model.addAttribute("message", "Độ dài Mật khẩu>=6");
			model.addAttribute("confirmPassword", confirmPassword);
			return "Regis";
			
		}
		else if(!user.getPassword().equals(confirmPassword))
		{
			System.out.println("Mật khẩu Nhập lại không đúng");
			model.addAttribute("user", user);
			model.addAttribute("message", "Mật khẩu Nhập lại không đúng");
			model.addAttribute("confirmPassword", confirmPassword);
			return "Regis";
		}
		else {
			if(userService.isExsist("email",user.getEmail())){
				model.addAttribute("user", user);
				model.addAttribute("message", "Email đã được sử dụng");
				System.out.println("Email đã được sử dụng");
				model.addAttribute("confirmPassword", user.getPassword());
				return "Regis";
			}
			else if(userService.isExsist("userName",user.getUserName())){
				model.addAttribute("user", user);
				model.addAttribute("message", "UserName đã tồn tại");
				System.out.println("UserName đã tồn tại");
				model.addAttribute("confirmPassword", confirmPassword);
				return "Regis";
			}
			
		}
		String password=user.getPassword();
		try {
		String encriptPass=UserDTO.toHexString(UserDTO.getSHA(password));
		user.setPassword(encriptPass);
		model.addAttribute("userName", user.getUserName());
	    userService.saveUser(user);
		System.out.println("register ok");
		} catch (Exception e) {
			// TODO: handle exception
		}
	    return "Login";
	}
	@PostMapping(value = "/login")
	public String login(Model model,@RequestParam("userName") String userName,@RequestParam("password") String password,@RequestParam(value="page",defaultValue="5") int  page,@RequestParam(value="remember",defaultValue="false") String isRemeber) {	
		System.out.println("userName "+userName+ " password: "+password);
		System.out.println("isRemeber "+isRemeber);
		
		try {
		String encriptPass=UserDTO.toHexString(UserDTO.getSHA(password));
		UserModel user=userService.getUserByUserName(userName);
		System.out.println("system password: "+user.getPassword());
		System.out.println("system password length: "+user.getPassword().length());
		if(user.getPassword().equals(encriptPass)) {
			//dang nhap thanh conng
			String uri=sessionService.get("security-uri");
			if(uri!=null) return "redirect:"+uri;
			else {
				model.addAttribute("message", "Login Successful!");
				model.addAttribute("USERNAME", userName);
				sessionService.set("USERNAME", userName);
				sessionService.set("USERID", user.getId().toString());
				if(isRemeber.equals("on")) {
					cookieService.add("USERNAME",userName,1);
					cookieService.add("USERID",user.getId().toString(),1);
				}
				else {
					cookieService.remove("USERNAME");
					cookieService.remove("USERID");
				}
				System.out.println("message Login Successful!");
			}
		} else {
			model.addAttribute("message", "Invalid Password");
			model.addAttribute("userName", userName);
			return "Login";
		}
	}catch(Exception e) {
		model.addAttribute("message", "Invalid Username");
		model.addAttribute("userName", userName);
		System.out.println("message Invalid Username!");
		return "Login";	
	}
		return "redirect:/"+constantPageId.getPageUrl(page);
	}
	@PostMapping(value="/logout")
	public String doLogout(@RequestParam("page") String page) {
		sessionService.remove("USERNAME");
		cookieService.remove("USERNAME");
		return "redirect:/"+page;
	}
	@GetMapping(value = "logout")
	public String doLogoutGet(){
		sessionService.remove("USERNAME");
		cookieService.remove("USERNAME");
		return "redirect:/";
	}
}
