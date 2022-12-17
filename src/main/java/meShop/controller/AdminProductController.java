package meShop.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
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
import org.springframework.web.multipart.MultipartFile;

import meShop.Constant.ConstantPageId;
import meShop.controller.ShoppingCartController.UpdateReturn;
import meShop.converter.ProductConverter;
import meShop.dto.CategoryDTO;
import meShop.dto.ProductDTO;
import meShop.model.CategoryModel;
import meShop.model.Employee;
import meShop.model.ProductModel;
import meShop.model.UserModel;
import meShop.service.CategoryService;
import meShop.service.ProductService;
import meShop.service.SessionService;

@Controller
@RequestMapping(value = "/admin/products")
public class AdminProductController {
	@Autowired ProductService productService;
	@Autowired CategoryService categoryService;
	@Autowired SessionService sessionService;
	@Autowired ProductConverter productConverter;
	@GetMapping(value = "/views")
	String getProductPage(Model model) {
		if(!sessionService.checkSecurityAdmin()){
			return "redirect:/account/"+ConstantPageId.PRODUCT_ADMIN_PAGE+"/login";
		}
		List<ProductDTO> productDTOs=productService.getAllProducts();
		String userName=sessionService.get("USERNAME","");
		model.addAttribute("productDTOs", productDTOs);
		List<String> productImage1s=new ArrayList<>();
		List<String> productImage2s=new ArrayList<>();
		List<String> productImage3s=new ArrayList<>();
		model.addAttribute("productList", productDTOs);
		productDTOs.forEach(productDTO->{
			if(productDTO.getImage()!=null)
			productImage1s.add(Base64.getEncoder().encodeToString(productDTO.getImage()));
			else productImage1s.add("");
			if(productDTO.getImage2()!=null)
				productImage2s.add(Base64.getEncoder().encodeToString(productDTO.getImage2()));
				else productImage2s.add("");
			if(productDTO.getImage3()!=null)
				productImage3s.add(Base64.getEncoder().encodeToString(productDTO.getImage3()));
				else productImage3s.add("");
		});
		model.addAttribute("productImage1s", productImage1s);
		model.addAttribute("productImage2s", productImage1s);
		model.addAttribute("productImage3s", productImage1s);
		model.addAttribute("userName", userName);
//		CategoryModel category = new CategoryModel();
//		model.addAttribute("category", category);
		return "AdminProduct";
	}
	@GetMapping(value = "/caphe-hat")
	String  getCaPheHatPage(Model model) {
		List<ProductDTO> productDTOs=productService.getProductByCateId(ConstantPageId.CAPHE_HAT);
		System.out.println("caphe-hat size"+productDTOs.size());
		String userName=sessionService.get("USERNAME","");
		model.addAttribute("productDTOs", productDTOs);
		List<String> productImage1s=new ArrayList<>();
		List<String> productImage2s=new ArrayList<>();
		List<String> productImage3s=new ArrayList<>();
		model.addAttribute("productList", productDTOs);
		productDTOs.forEach(productDTO->{
			if(productDTO.getImage()!=null)
			productImage1s.add(Base64.getEncoder().encodeToString(productDTO.getImage()));
			else productImage1s.add("");
			if(productDTO.getImage2()!=null)
				productImage2s.add(Base64.getEncoder().encodeToString(productDTO.getImage2()));
				else productImage2s.add("");
			if(productDTO.getImage3()!=null)
				productImage3s.add(Base64.getEncoder().encodeToString(productDTO.getImage3()));
				else productImage3s.add("");
		});
		model.addAttribute("productImage1s", productImage1s);
		model.addAttribute("productImage2s", productImage1s);
		model.addAttribute("productImage3s", productImage1s);
		model.addAttribute("userName", userName);
		return "AdminProduct";
	}
	
	@GetMapping(value = "/update/{id}")
	String getProductUpdatePage(Model model,@PathVariable("id") int id) {
		ProductModel productModel=productService.getProductById(id);
		ProductDTO productDTO = productConverter.toDTO(productModel);
		String userName=sessionService.get("USERNAME","");	
		String productImage;
		String productImage2;
		String productImage3;
		model.addAttribute("productDTO", productDTO);
		if(productModel.getImage()!=null)
		productImage=Base64.getEncoder().encodeToString(productModel.getImage());
		else productImage="";
		if(productModel.getImage2()!=null)
		productImage2=Base64.getEncoder().encodeToString(productModel.getImage2());
		else productImage2="";
		if(productModel.getImage3()!=null)
		productImage3=Base64.getEncoder().encodeToString(productModel.getImage3());
		else productImage3="";
		model.addAttribute("productImage", productImage);
		model.addAttribute("productImage2", productImage2);
		model.addAttribute("productImage3", productImage3);
		model.addAttribute("userName", userName);
		List<CategoryDTO> categoryDTOs=categoryService.getAllCategories();
		model.addAttribute("categoryDTOs", categoryDTOs);
//		CategoryModel category = new CategoryModel();
//		model.addAttribute("category", category);
		return "AdminInsertProduct";
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
			ProductModel productModel = productService.getProductById(id);
			productService.deleteProductById(id);
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
//	@PostMapping(path="/insert", consumes = "application/json")
//	@CrossOrigin
//	public ResponseEntity<?> insertProduct(@RequestBody UserModel user)
//	{
//		Map<String, Object> map = new LinkedHashMap<String, Object>();
//		map.put("status", 1);
//		map.put("data", user.toString());
//	    System.out.println("user:"+user);
//	     return new ResponseEntity<>(map, HttpStatus.CREATED);
//	}
	
	@GetMapping(value = "/insert")
	String showInsertProductForm(Model model) {
		ProductDTO productDTO = new ProductDTO();
		String userName=sessionService.get("USERNAME","");
		model.addAttribute("userName", userName);
		List<CategoryDTO> categoryDTOs=categoryService.getAllCategories();
		model.addAttribute("productDTO", productDTO);
		model.addAttribute("categoryDTOs", categoryDTOs);
		return "AdminInsertProduct";
	}
	@PostMapping("/save")
	public String saveProduct(@ModelAttribute("productDTO") ProductDTO productDTO,@RequestParam("photo") MultipartFile multipartFile,@RequestParam("photo2") MultipartFile multipartFile2,@RequestParam("photo3") MultipartFile multipartFile3) throws IOException {
		System.out.println("saveProduct");
		System.out.println(productDTO);
		
		if(!multipartFile.isEmpty()&&!multipartFile2.isEmpty()&&!multipartFile3.isEmpty()) {
			System.out.println("set Image");
			productDTO.setImage(multipartFile.getBytes());
			productDTO.setImage2(multipartFile2.getBytes());
			productDTO.setImage3(multipartFile3.getBytes());
		} else {
			System.out.println(productDTO);
			if(productDTO.getId()!=null&&(!productDTO.getId().equals(""))) {
				ProductModel productDTO2=productService.getProductById(Integer.parseInt(productDTO.getId()));
				if(multipartFile.isEmpty()) {
					productDTO.setImage(productDTO2.getImage());
				} else productDTO.setImage(multipartFile.getBytes());
				if(multipartFile2.isEmpty()) {
					productDTO.setImage2(productDTO2.getImage2());
				} else  productDTO.setImage2(multipartFile2.getBytes());
				if(multipartFile3.isEmpty()) {
					productDTO.setImage3(productDTO2.getImage3());
				}else  productDTO.setImage3(multipartFile3.getBytes());
			}
		}
		System.out.println("saveProduct");
		productService.saveProduct(productDTO);
		return "redirect:/admin/products/views";	
	}

	
	
	
}


	
