package meShop.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meShop.model.UserModel;

@Service
public class SessionService {
	@Autowired 
	HttpSession session;
	@Autowired UserService userService;
	@Autowired CookieService cookieService;
	@SuppressWarnings("unchecked")
	public <T> T get(String name) {
		return (T) session.getAttribute(name);
	}
	public <T> T get(String name,T defaultValue) {
		T value=get(name);
		return value!=null?value:defaultValue;
	}
	public void set(String name,Object value) {
		session.setAttribute(name, value);
	}
	public void remove(String name) {
		session.removeAttribute(name);
	}
	public boolean checkSecurity() {
		if(this.get("USERNAME")!=null) {
			System.out.println("checkSecurity true");
			return true;
		}
		else {
			if(cookieService.getValue("USERNAME", "").equals("")){
				System.out.println("checkSecurity false");
				return false;
			}
			else {
				String name=cookieService.getValue("USERNAME","");
				System.out.println("checkSecurity "+name);
				UserModel user=userService.getUserByUserName(name);
				System.out.println("checkSecurity_user "+user);
				System.out.println("vao else "+user.getFullName());
				this.set("USERNAME", user.getUserName());
				this.set("USERID", user.getId());
				this.set("USERADDRESS", user.getAddress());
				this.set("USERFULLNAME", user.getFullName());
				this.set("USERPHONE", user.getPhone());
				return true;
			}
		}
		
	}
	
	public boolean checkSecurityAdmin() {
		if(this.get("USERNAME")!=null) {
			UserModel user=userService.getUserByUserName(this.get("USERNAME"));
			if(user.getRole().getCode()==1)
			return true; else return false;
		}
		else {
			if(cookieService.getValue("USERNAME", "").equals("")){
				System.out.println("checkSecurity false");
				return false;
			}
			else {
				String name=cookieService.getValue("USERNAME","");
				System.out.println("checkSecurity "+name);
				UserModel user=userService.getUserByUserName(name);
				System.out.println("vao else "+user.getFullName());
				if(user.getRole().getCode()==1){
					this.set("USERNAME", user.getUserName());
					this.set("USERID", user.getId());
					this.set("USERADDRESS", user.getAddress());
					this.set("USERFULLNAME", user.getFullName());
					this.set("USERPHONE", user.getPhone());
					System.out.println("checkSecurity true");
					return true;
				} else {
					System.out.println("checkSecurity false");
					return false;
				}
				
			}
		}
		
	}
	public boolean checkSecurityShipper() {
		if(this.get("USERNAME")!=null) {
			UserModel user=userService.getUserByUserName(this.get("USERNAME"));
			if(user.getRole().getCode()==2)
			return true; else return false;
		}
		else {
			if(cookieService.getValue("USERNAME", "").equals("")){
				System.out.println("checkSecurity false");
				return false;
			}
			else {
				String name=cookieService.getValue("USERNAME","");
				System.out.println("checkSecurity "+name);
				UserModel user=userService.getUserByUserName(name);
				System.out.println("vao else "+user.getFullName());
				if(user.getRole().getCode()==2){
					this.set("USERNAME", user.getUserName());
					this.set("USERID", user.getId());
					this.set("USERADDRESS", user.getAddress());
					this.set("USERFULLNAME", user.getFullName());
					this.set("USERPHONE", user.getPhone());
					System.out.println("checkSecurity true");
					return true;
				} else {
					System.out.println("checkSecurity false");
					return false;
				}
				
			}
		}
		
	}
}
