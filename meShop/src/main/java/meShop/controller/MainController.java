package meShop.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import meShop.Constant.ConstantPageId;
import meShop.converter.MainConverter;
import meShop.converter.ProductConverter;
import meShop.dto.CategoryDTO;
import meShop.dto.ProductDTO;
import meShop.model.CartItem;
import meShop.model.CartItemList;
import meShop.model.CategoryModel;
import meShop.model.ProductModel;
import meShop.model.UserModel;
import meShop.repository.UserRepository;
import meShop.service.CategoryService;
import meShop.service.CookieService;
import meShop.service.ProductService;
import meShop.service.SessionService;
import meShop.service.ShoppingCartService;
import meShop.service.UserService;
@Controller
public class MainController {
	@Autowired
	private UserService userService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductService productService;
	@Autowired ProductConverter productConverter;
	@Autowired SessionService sessionService;
	@Autowired CookieService cookieService;
	@Autowired ShoppingCartService shoppingCartService;
	
	@RequestMapping("/real")
    public String index(HttpServletRequest request, Model model) {
        String username = (String) request.getSession().getAttribute("USERNAME");

        if (username == null || username.isEmpty()) {
            return "redirect:/account/showlogin/"+ConstantPageId.CHAT_PAGE;
        }
        model.addAttribute("userName", username);

        return "chatReal";
    }

    @RequestMapping(path = "/real/login", method = RequestMethod.GET)
    public String showLoginPage() {
        return "loginReal";
    }

    @RequestMapping(path = "/real/login", method = RequestMethod.POST)
    public String doLogin(HttpServletRequest request, @RequestParam(defaultValue = "") String username) {
        username = username.trim();

        if (username.isEmpty()) {
            return "loginReal";
        }
        request.getSession().setAttribute("username", username);

        return "redirect:/real";
    }

    @RequestMapping(path = "/real/logout")
    public String logout(HttpServletRequest request) {
        request.getSession(true).invalidate();
        
        return "redirect:/real/login";
    }
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping(value = "/")
	public String getHomePage(Model model) {
		if(cookieService.get("USERNAME")!=null) {
			model.addAttribute("userName",cookieService.getValue("USERNAME","GUEST"));
		} else
		model.addAttribute("userName",sessionService.get("USERNAME")!=null?sessionService.get("USERNAME"):"GUEST");
		if(cookieService.get("cartList")!=null){
			initCart();
			System.out.println("initCart");
		}
		return "index";
	}
	private void initCart() {
		if(cookieService.get("CARTLIST")!=null)
		try {
			CartItemList cartItemList =(CartItemList) MainConverter.From_String(cookieService.getValue("CARTLIST", null));
			List<CartItem> listCartItemTmp=cartItemList.getCartItems(cartItemList);
			listCartItemTmp.forEach(cartItem->{
				ProductModel productModel=productService.getProductById(Integer.parseInt(cartItem.getProductId()));
				cartItem.setPrice(productModel.getPrice());
				cartItem.setImgae(productModel.getImage());
				cartItem.setTitle(productModel.getTitle());
				shoppingCartService.addCart(cartItem);
			});
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@PostMapping("/process_register")
	public String processRegister(UserModel user) {
		System.out.println(user.getPassword());
	    //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    //String encodedPassword = passwordEncoder.encode(user.getPassword());
	    //user.setPassword(encodedPassword);
	     
	    userService.saveUser(user);
	     
	    return "registerSuccess";
	}
	@GetMapping(value = "/userIndex")
	String getUserPage(Model model) {
		model.addAttribute("userList", userService.getAllUser());
		return "userIndex";
	}
	@GetMapping(value ="/register")
	public String showRegistrationForm(Model model) {
	    model.addAttribute("user", new UserModel());
	    return "signupForm";
	}
	@GetMapping(value = "/categoryIndex")
	String getCategoryPage(Model model) {
		model.addAttribute("categoryList", categoryService.getAllCategories());
		return "categoryIndex";
	}

	@GetMapping(value = "/productIndex")
	String getProductPage(Model model) {
		//if(!checkSecurity()) return "redirect:/account/login";
		List<ProductDTO> productDTOs=productService.getAllProducts();
		List<String> productImages=new ArrayList<>();
		model.addAttribute("productList", productDTOs);
		productDTOs.forEach(productDTO->{
			productImages.add(Base64.getEncoder().encodeToString(productDTO.getImage()));
		});
		model.addAttribute("productImages", productImages);
		return "productIndex";
	}

	private boolean checkSecurity() {
		String userName=sessionService.get("USERNAME");
		if(userName!=null) {
			System.out.println("check security "+userName);
			return true;
		}
		return false;
	}
	@PostMapping("/saveProduct")
	public String saveProduct(@ModelAttribute("productDTO") ProductDTO productDTO,@RequestParam("photo") MultipartFile multipartFile) throws IOException {
		System.out.println(productDTO);
		if(!multipartFile.isEmpty()) {
			System.out.println("set Image");
			productDTO.setImage(multipartFile.getBytes());
		}
		
		System.out.println("saveProduct");
		//,@ModelAttribute("product") ProductModel productModel
		System.out.println(productDTO);
		productService.saveProduct(productDTO);
		return "redirect:/productIndex";	
	}

	@GetMapping(value = "showFormUpdate/{id}")
	public String showUpdateForm(@PathVariable(value = "id") long id, Model model) {
		UserModel user = userService.getUserById(id);

		// set employee as a model attribute to pre-populate the form
		model.addAttribute("user", user);
		return "updateUser";
	}

	@GetMapping(value = "showFormUpdateCategory/{id}")
	public String showFormUpdateCategory(@PathVariable(value = "id") long id, Model model) {
		CategoryModel category = categoryService.getCategoryById(id);

		// set employee as a model attribute to pre-populate the form
		model.addAttribute("category", category);
		return "updateCategory";
	}
	
	

	@GetMapping(value = "showUpdateProductForm/{id}")
	public String showUpdateProductForm(@PathVariable(value = "id") long id, Model model) {
		ProductDTO productDTO = productConverter.toDTO(productService.getProductById(id));
		System.out.println("dto update");
		System.out.println(productDTO);
		System.out.println("productBase64Images "+productService.getProductById(id).getImage());
		String productBase64Images= Base64.getEncoder().encodeToString(productService.getProductById(id).getImage());
        model.addAttribute("image", productBase64Images);
        model.addAttribute("cateList", categoryService.getAllCategories());
		// set employee as a model attribute to pre-populate the form
		model.addAttribute("productDTO", productDTO);
		return "updateProduct";
	}
	
	@PostMapping(value = "saveUser")
	public String saveUser(@ModelAttribute("user") UserModel usermodel) {
		// TODO: process POST request
		userService.saveUser(usermodel);
		return "redirect:/userIndex";
	}

	@PostMapping(value = "saveCategory")
	public String saveUser(@ModelAttribute("user") CategoryModel categoryModel) {
		// TODO: process POST request
		categoryService.saveCategory(categoryModel);
		return "redirect:/categoryIndex";
	}

	@PostMapping(value = "updateUser")
	public String updateUser(@ModelAttribute("user") UserModel usermodel) {
		// TODO: process POST request
		// userService.updateUser(usermodel.id,usermodel.getUserName());
		System.out.println(usermodel.toString());
		return "redirect:/";
	}

	@GetMapping(value = "/showFormAddUser")
	String showInsertUserForm(Model model) {
		UserModel user = new UserModel();
		model.addAttribute("user", user);
		return "newUser";
	}

	@GetMapping(value = "/showFormAddCategory")
	String showFormAddCategory(Model model) {
		CategoryModel category = new CategoryModel();
		model.addAttribute("category", category);
		return "newCategory";
	}
	@GetMapping(value = "/showFormAddProduct")
	String showInsertProductForm(Model model) {
		ProductDTO productDTO = new ProductDTO();
		List<CategoryDTO> cateList=categoryService.getAllCategories();
		model.addAttribute("productDTO", productDTO);
		model.addAttribute("cateList", cateList);
		return "newProduct";
	}
	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable(value = "id") long id) {

		// call delete employee method
		this.userService.deleteUserById(id);
		return "redirect:/userIndex";
	}
	@DeleteMapping("/deleteProduct")
	public String deleteProductById(@RequestParam("id") String id) {
		System.out.println(id);
		productService.deleteProductById((long) Integer.parseInt(id));
		return "redirect:/productIndex";
	}

}
