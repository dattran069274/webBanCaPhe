package meShop.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Exception.InvalidPasswordException;
import Helptools.AccountValidator;
import Helptools.Utility;
import meShop.Constant.ConstantPageId;
import meShop.dto.UserDTO;
import meShop.model.RoleModel;
import meShop.model.UserModel;
import meShop.service.CookieService;
import meShop.service.RoleService;
import meShop.service.SessionService;
import meShop.service.UserService;
import net.bytebuddy.utility.RandomString;

@Controller
@RequestMapping("account")
public class AccountController {
	@Autowired
	SessionService sessionService;
	@Autowired
	CookieService cookieService;
	@Autowired UserService userService;
	@Autowired MainController mainController;
	@Autowired RoleService roleService;
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

@PostMapping("/reset_password")
public String processResetPassword(HttpServletRequest request, Model model) {
	String token = request.getParameter("token");
	String password = request.getParameter("password").trim();
	UserModel user = userService.get(token);
	model.addAttribute("title", "Reset your password");
	if (user == null) {
		model.addAttribute("message", "Invalid Token");
		return "ForgetMess";
		} else {
			try {
				AccountValidator.isValidPassword(password);
				userService.updatePassword(user, password);
				model.addAttribute("message", "You have successfully changed your password.");
			}
			catch (InvalidPasswordException e) {
				model.addAttribute("message", e.printMessage());
				model.addAttribute("token", token);
				return "reset_password_form";
			}
			catch (Exception e) {
				model.addAttribute("message",e.getMessage() );
				model.addAttribute("token", token);
				return "reset_password_form";
			}	
		}
	return "ForgetMess";
}
@GetMapping("/reset_password")
public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
    UserModel user = userService.get(token);
    model.addAttribute("token", token);
     
    if (user == null) {
        model.addAttribute("message", "Invalid Token");
        return "ForgetMess";
    }
     
    return "reset_password_form";
}
	@GetMapping(value = "/{pageId}/login")
	String loginFromPage(Model model,@PathVariable("pageId") int pageId){
		model.addAttribute("pageId", pageId);
		return "Login";
	}
	@GetMapping(value = "/forgot_password")
	String forgotPasswordPage(){
		return "ForgotForm";
	}
	@PostMapping(value = "/forgot_password")
	String processFogotPasswordForm(HttpServletRequest request, Model model) {
		String email=request.getParameter("email");
		String token=RandomString.make(45);
		System.out.println("email "+email);
		System.out.println("token "+token);
		try {
			userService.updateResetPassword(token, email);
			String resetPasswordLink=Utility.getSiteURL(request)+"/account/reset_password?token="+token;
			System.out.println("resetPasswordLink "+resetPasswordLink);
			sendmail(email,resetPasswordLink);
			model.addAttribute("message", "Vui lòng kiểm tra email!");
			model.addAttribute("email", email);
		} 
		catch (Exception ex) {
			System.out.println("error:"+ex.getMessage());
			model.addAttribute("message", "Email chhưa đăng kí tài khoản!");
			model.addAttribute("email", email);
	    } 
		
		return "ForgotForm";
	}
	private void sendmail(String email,String resetPasswordLink) throws Exception, MessagingException, IOException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		   protected PasswordAuthentication getPasswordAuthentication() {
			  return new PasswordAuthentication("1951120092@sv.ut.edu.vn", "0989710348");
		   }
		});
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("tutorialspoint@gmail.com", false));

		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
		String subject = "Here's the link to reset your password";
		  
		 String content = "<p>Hello,</p>"
				 + "<p>You have requested to reset your password.</p>"
				 + "<p>Click the link below to change your password:</p>"
				 + "<p><a href=\"" + resetPasswordLink + "\">Change my password</a></p>"
				 + "<br>"
				 + "<p>Ignore this email if you do remember your password, "
				 + "or you have not made the request.</p>";
		  
		 msg.setSubject(subject);
		  
		 msg.setContent(content,"text/html");
		Transport.send(msg);   
	 }
	@GetMapping(value = "view/{page}")
	String getAccountInfo(Model model,@PathVariable(value = "page",required= false) String page) {
		if(!sessionService.checkSecurity()){
			return "redirect:/account/"+page+"/login";
		}
		String userName=sessionService.get("USERNAME", "");
		if(!userName.equals("")) {
			UserModel userModel=userService.getUserByUserName(userName);
			System.out.println(userModel);
			model.addAttribute("user", userModel);
		}
		else {
			System.out.println("user empty");
		}
		System.out.println("page" +page);
		model.addAttribute("page",page );
		return "Account";
	}
@GetMapping(value = "/login/{page}")
String getLoginPage(@PathVariable(value = "page") String page,Model model){
	//model.addAttribute("page", ConstantPageId.HOME_PAGE);
	// if(!sessionService.get("USERNAME","").equals("")) {
	// 	System.out.println("login roi");
	// 	if(page!=null&&page!=""){
	// 		return "redirect:/"+page;
	// 	} else {
	// 		System.out.println("ve dau");
	// 	}
	// }
	//return "Login";
	return "redirect:/account/"+ConstantPageId.getIdFromPageUrl(page)+"/login";
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
			model.addAttribute("test", "11");
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
	@PostMapping("/save")
	String saveAndGetPrePage(Model model,UserModel user,@RequestParam("page") String page,@RequestParam("userName") String userName) {
		System.out.println("truyen len "+user.toString());
		UserModel oldUser=userService.getUserByUserName(userName);
		oldUser.setFullName(user.getFullName());
		oldUser.setAddress(user.getAddress());
		oldUser.setPhone(user.getPhone());

		sessionService.set("USERFULLNAME", user.getFullName());
		sessionService.set("USERADDRESS", user.getAddress());
		userService.saveUser(oldUser);
		System.out.println("sau save"+userService.getUserByUserName(user.getUserName()));
		return "redirect:/"+ConstantPageId.getPageUrl(Integer.parseInt(page));
	}
	@PostMapping("/process_register")
	public String processRegister(Model model,UserModel user,@RequestParam("confirmPassword") String confirmPassword ) {
		System.out.println(user.toString());
		if(!AccountValidator.isValidUsername(user.getUserName())){
			System.out.println("Username kh pass");
			model.addAttribute("user", user);
			model.addAttribute("message", "Tên đăng nhập bắt đầu bằng chữ cái, độ dài >=6 kí tự, không chứa khoảng trắng");
			model.addAttribute("confirmPassword", confirmPassword);
			return "Regis";
		}
		if(user.getPassword()==null){
			System.out.println("Độ dài Mật khẩu");
			model.addAttribute("user", user);
			model.addAttribute("message", "Mật khẩu không được trống");
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
		try {
			AccountValidator.isValidPassword(user.getPassword());
			String password=user.getPassword();
			String encriptPass=UserDTO.toHexString(UserDTO.getSHA(password));
			user.setPassword(encriptPass);
			model.addAttribute("userName", user.getUserName());
			RoleModel roleUser=roleService.finByCode(0);
			user.setRole(roleUser);
		    userService.saveUser(user);
			System.out.println("register ok");
		}
		catch (InvalidPasswordException e) {
			 System.out.print(e.getMessage());
	         System.out.println(e.printMessage());  
	         model.addAttribute("user", user);
			model.addAttribute("message", e.printMessage());
			return "Regis";
		}
		catch (Exception e) {
			System.out.print(e.getMessage()); 
	         model.addAttribute("user", user);
			model.addAttribute("message", "UserName đã tồn tại");
			return "Regis";
		} 
	    return "Login";
	}
	@PostMapping(value = "/login")
	public String login(Model model,@RequestParam("userName") String userName,@RequestParam("password") String password,@RequestParam(value="pageId") int  pageId,@RequestParam(value="remember",defaultValue="false") String isRemeber) {	
		System.out.println("login");
		System.out.println("userName "+userName+ " password: "+password);
		System.out.println("isRemeber "+isRemeber);
		System.out.println("pageId "+pageId);
		try {
		String encriptPass=UserDTO.toHexString(UserDTO.getSHA(password));
		UserModel user=userService.getUserByUserName(userName);
		System.out.println("system password: "+user.getPassword());
		System.out.println("system password length: "+user.getPassword().length());
		if(user.getPassword().equals(encriptPass)) {
			//dang nhap thanh conng
			System.out.println("login pass equal");
			String uri=sessionService.get("security-uri");
			if(uri!=null) return "redirect:"+uri;
			else {
				model.addAttribute("message", "Login Successful!");
				model.addAttribute("USERNAME", userName);
				sessionService.set("USERNAME", userName);
				sessionService.set("USERID", user.getId().toString());
				sessionService.set("USERADDRESS", user.getAddress());
				sessionService.set("USERFULLNAME", user.getFullName());
				sessionService.set("USERPHONE", user.getPhone());
				System.out.println("USERADDRESS: "+user.getPhone());
				if(isRemeber.equals("on")) {
					cookieService.add("USERNAME",userName,1);
					cookieService.add("USERID",user.getId().toString(),1);
					//cookieService.add("USERADDRESS", user.getAddress(),1);
					//cookieService.add("USERFULLNAME", user.getFullName(),1);
					cookieService.add("USERPHONE", user.getPhone(),1);
				}
				else {
					cookieService.remove("USERNAME");
					cookieService.remove("USERID");
					cookieService.remove("USERADDRESS");
					cookieService.remove("USERFULLNAME");
				}
				System.out.println("message Login Successful!");
			}
		} else {
			model.addAttribute("message", "Sai Mật Khẩu");
			model.addAttribute("userName", userName);
			model.addAttribute("pageId", pageId);
			return "Login";
		}
	}catch(Exception e) {
		model.addAttribute("message", "Sai Tên Đăng Nhập");
		model.addAttribute("userName", userName);
		model.addAttribute("pageId", pageId);
		System.out.println("message Invalid Username!");
		System.out.println("loi "+e.getMessage());
		return "Login";	
	}
		return "redirect:/"+constantPageId.getPageUrl(pageId);
	}
	@PostMapping(value="/logout")
	public String doLogout(@RequestParam("page") String page) {
		sessionService.remove("USERNAME");
		sessionService.remove("USERFULLNAME");
		sessionService.remove("USERID");
		sessionService.remove("USERADDRESS");
		
		cookieService.remove("USERNAME");
		cookieService.remove("USERFULLNAME");
		cookieService.remove("USERID");
		cookieService.remove("USERADDRESS");
		cookieService.remove("USERPHONE");
		return "redirect:/"+page;
	}
	@GetMapping(value = "logout")
	public String doLogoutGet(){
		sessionService.remove("USERNAME");
		sessionService.remove("USERFULLNAME");
		sessionService.remove("USERID");
		sessionService.remove("USERADDRESS");
		
		cookieService.remove("USERNAME");
		cookieService.remove("USERFULLNAME");
		cookieService.remove("USERID");
		cookieService.remove("USERADDRESS");
		cookieService.remove("USERPHONE");
		return "redirect:/";
	}
}
