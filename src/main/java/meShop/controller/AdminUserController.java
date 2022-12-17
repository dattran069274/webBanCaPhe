package meShop.controller;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import meShop.controller.ShoppingCartController.UpdateReturn;
import meShop.dto.CategoryDTO;
import meShop.model.CategoryModel;
import meShop.model.Employee;
import meShop.model.UserModel;
import meShop.service.CategoryService;
import meShop.service.SessionService;
import meShop.service.UserService;

@Controller
@RequestMapping(value = "/admin/users")
public class AdminUserController {
	@Autowired UserService userService;
	@Autowired SessionService sessionService;
	@GetMapping(value = "/views")
	String getUserPage(Model model) {
		List<UserModel> userModels=userService.getAllUser();
		String userName=sessionService.get("USERNAME","");
		model.addAttribute("userModels", userModels);
		model.addAttribute("userName", userName);
		//CategoryModel category = new CategoryModel();
		//model.addAttribute("category", category);
		return "AdminUser";
	}
	

//	@PutMapping("/update") 
//	  public ResponseEntity handleUpdate( @RequestParam("id") String id,@RequestParam("name") String name) {
//		System.out.println("id"+id);
//		System.out.println("name"+name);
//		Map<String, Object> map = new LinkedHashMap<String, Object>();
//		if(name!=null&&name.length()>0) {
//			try {
//				CategoryModel categoryModel=categoryService.getCategoryById(Integer.parseInt(id));
//				categoryModel.setName(name);
//				categoryService.saveCategory(categoryModel);
//				 System.out.println("handleUpdate category name "+name);	
//				 map.put("status", 1);
//					map.put("data", categoryService.getCategoryById(Integer.parseInt(id)).getName());
//					return new ResponseEntity<>(map, HttpStatus.OK); 
//			}
//			catch (Exception e) {
//				map.clear();
//				map.put("status", 0);
//				map.put("message", "Data is not found");
//				return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
//			}	
//		}
//		else 
//		{
//				System.out.println("handleUpdate category name null");
//				map.clear();
//				map.put("status", 0);
//				map.put("message", "handleUpdate category name null");
//				return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
//		}
//		
//	  }


	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		try {
			UserModel userModel = userService.getUserById(id);
			userService.deleteUserById(id);
			map.put("status", 1);
			map.put("message", "Record is deleted successfully!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception ex) {
			map.clear();
			map.put("status", 0);
			map.put("message", "Data is not found");
			return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		}

	}
//	@PostMapping("/save")
//	public ResponseEntity<?> saveUser(@RequestParam("name") String name) {
//		System.out.println(name);
//		Map<String, Object> map = new LinkedHashMap<String, Object>();
//		CategoryModel old=categoryService.getCategoryByName(name);
//		if(old!=null) {
//			map.put("status", 0);
//			map.put("message", "CategoryName has already exsit");
//			return new ResponseEntity<>(map, HttpStatus.CONFLICT);
//		}
//		CategoryModel categoryModel=new CategoryModel();
//		categoryModel.setName(name);
//		Date date=new Date();
//		categoryModel.setCreatedDate(date);
//		categoryModel.setModifiedDate(date);
//		categoryService.save(categoryModel);
//		map.put("status", 1);
//		map.put("message", "Record is Saved Successfully!");
//		return new ResponseEntity<>(map, HttpStatus.CREATED);
//	}
	
	//insert
	
//	@PostMapping(path="/insert", consumes = "application/json")
//	@CrossOrigin
//	public ResponseEntity<?> createEmployee(@RequestBody UserModel user)
//	{
//		Map<String, Object> map = new LinkedHashMap<String, Object>();
//		map.put("status", 1);
//		map.put("data", user.toString());
//	    System.out.println("employee"+user);
//	     return new ResponseEntity<>(map, HttpStatus.CREATED);
//	}
	
	
	
}


	
