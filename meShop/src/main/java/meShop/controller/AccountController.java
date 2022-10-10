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
		return "loginForm";
	}
	@GetMapping(value = "/showlogin/{page}")
	String getLoginPage(@PathVariable("page") String page,Model model){
		model.addAttribute("page", page);
		return "loginForm";
	}
	@PostMapping(value = "/login")
	public String login(Model model,@RequestParam("userName") String userName,@RequestParam("password") String password,@RequestParam("page") int  page,@RequestParam("remember") String isRemeber) {
		System.out.println("userName "+userName+ " password: "+password);
		System.out.println("isRemeber "+isRemeber);
		
		try {
		UserModel user=userService.getUserByUserName(userName);
//		System.out.println("user co userName: "++user);
		System.out.println("system password: "+user.getPassword());
		System.out.println("system password length: "+user.getPassword().length());
		if(user.getPassword().equals(password)) {
			//dang nhap thanh conng
			String uri=sessionService.get("security-uri");
			if(uri!=null) return "redirect:"+uri;
			else {
				model.addAttribute("message", "Login Successful!");
				sessionService.set("USERNAME", userName);
				sessionService.set("USERID", user.getId().toString());
				if(isRemeber.equals("true")) {
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
			return "loginForm";
		}
	}catch(Exception e) {
		model.addAttribute("message", "Invalid Username");
		System.out.println("message Invalid Username!");
		return "loginForm";	
	}
		return "redirect:/"+constantPageId.getPageUrl(page);
	}
	@PostMapping(value="/logout")
	public String doLogout(@RequestParam("page") String page) {
		sessionService.remove("USERNAME");
		cookieService.remove("USERNAME");
		return "redirect:/"+page;
	}
}
