package meShop.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import meShop.Constant.ConstantPageId;
import meShop.converter.MainConverter;
import meShop.dto.ProductDTO;
import meShop.model.CartItem;
import meShop.model.CartItemList;
import meShop.model.ProductModel;
import meShop.service.CookieService;
import meShop.service.ProductService;
import meShop.service.SessionService;
import meShop.service.ShoppingCartService;

@Controller
@RequestMapping(value = "/product")
class ProductController{
    @Autowired ProductService productService;
    @Autowired CookieService cookieService;
    @Autowired SessionService sessionService;
    @Autowired ShoppingCartService shoppingCartService;
    @GetMapping(value="/views")
    String getProductHome(@RequestParam(defaultValue = "1") Integer pageNo, 
            @RequestParam(defaultValue = "8") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,Model model){
    	initCart();
		String userName=sessionService.get("USERNAME", "GUEST");
		model.addAttribute("userName", userName);
	model.addAttribute("totalCartItems", shoppingCartService.getAllCartItem().size());
    	List<ProductDTO> list=productService.getProducts(pageNo-1, pageSize, sortBy);
    	model.addAttribute("productList", list);
    	model.addAttribute("totalPages", productService.getTotalPage(pageSize));
		model.addAttribute("currentPage", ""+pageNo);
        return "Product";
    }
    @GetMapping(value="/ca-phe-hat")
    String getProductCaphehat(Model model,@RequestParam(name="pageNo",defaultValue="1") int pageNo,@RequestParam(defaultValue = "8") Integer pageSize,
	@RequestParam(defaultValue = "id") String sortBy){
    	initCart();
		model.addAttribute("totalCartItems", shoppingCartService.getAllCartItem().size());
        List<ProductDTO> productDTOs=productService.getProductByCateId(ConstantPageId.CAPHE_HAT);
		System.out.println("cafehat:"+productDTOs.size());
		model.addAttribute("totalPages", productService.getTotalPage(pageSize,ConstantPageId.CAPHE_HAT));
		model.addAttribute("totalPages", 1);
		model.addAttribute("currentPage", ""+pageNo);
		List<String> productImages=new ArrayList<>();
		model.addAttribute("productList", productDTOs);
		productDTOs.forEach(productDTO->{
			productImages.add(Base64.getEncoder().encodeToString(productDTO.getImage()));
		});
		model.addAttribute("productImages", productImages);
        return "Product";
    }
    @GetMapping(value="/ca-phe-hoa-tan")
    String getProductCaphehoatan(Model model){
    	initCart();
		model.addAttribute("totalPages", 1);
		model.addAttribute("currentPage", "1");
		model.addAttribute("totalCartItems", shoppingCartService.getAllCartItem().size());
        List<ProductDTO> productDTOs=productService.getProductByCateId(ConstantPageId.CAPHE_HOATAN);
		List<String> productImages=new ArrayList<>();
		model.addAttribute("productList", productDTOs);
		productDTOs.forEach(productDTO->{
			productImages.add(Base64.getEncoder().encodeToString(productDTO.getImage()));
		});
		model.addAttribute("productImages", productImages);
        return "Product";
    }
    @GetMapping(value="/ca-phe-phin")
    String getProductCaphephin(Model model){
    	initCart();
		model.addAttribute("totalPages", 1);
		model.addAttribute("currentPage", "1");
		model.addAttribute("totalCartItems", shoppingCartService.getAllCartItem().size());
        List<ProductDTO> productDTOs=productService.getProductByCateId(ConstantPageId.CAPHE_PHIN);
		List<String> productImages=new ArrayList<>();
		model.addAttribute("productList", productDTOs);
		productDTOs.forEach(productDTO->{
			productImages.add(Base64.getEncoder().encodeToString(productDTO.getImage()));
		});
		model.addAttribute("productImages", productImages);
        return "Product";
    }
    @GetMapping(value="/search/{key}")
    String getProductCaphehoatan(Model model,@PathVariable("key") String key,@RequestParam(name="pageNo",defaultValue="1") int pageNo,@RequestParam(defaultValue = "8") Integer pageSize,
	@RequestParam(defaultValue = "id") String sortBy){
		System.out.println("search "+key);
		model.addAttribute("totalPages", productService.getTotalPage(pageSize,key));
		model.addAttribute("currentPage", ""+pageNo);
        List<ProductDTO> productDTOs=productService.getProductsByKey(key,pageNo-1, pageSize, sortBy);
		List<String> productImages=new ArrayList<>();
		model.addAttribute("productList", productDTOs);
		productDTOs.forEach(productDTO->{
			productImages.add(Base64.getEncoder().encodeToString(productDTO.getImage()));
		});
		model.addAttribute("productImages", productImages);
		int total=shoppingCartService.getAllCartItem().size();
		System.out.println("total"+total);
		model.addAttribute("totalCartItems", total);
        return "Product";
    }
    @GetMapping(value = "/{id}")
	String getProductDetail(@PathVariable(value = "id") long id,Model model){
    	initCart();
		model.addAttribute("totalCartItems", shoppingCartService.getAllCartItem().size());
		ProductModel p=productService.getProductById(id);
		String img=Base64.getEncoder().encodeToString(p.getImage());
		String img2=Base64.getEncoder().encodeToString(p.getImage());
		String img3=Base64.getEncoder().encodeToString(p.getImage());
		model.addAttribute("product", p);
		model.addAttribute("productImg", img);
		model.addAttribute("productImg2", img2);
		model.addAttribute("productImg3", img3);
		model.addAttribute("userName",sessionService.get("USERNAME")!=null?sessionService.get("USERNAME"):"GUEST");
		String userName=sessionService.get("USERNAME", "GUEST");
		model.addAttribute("userName", userName);
		return "infoProduct";
	}
    @PostMapping("/save")
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
    private void initCart() {
		if(cookieService.get("CARTLIST")!=null&&shoppingCartService.getAllCartItem().size()==0)
		try {
			CartItemList cartItemList =(CartItemList) MainConverter.From_String(cookieService.getValue("CARTLIST", null));
			List<CartItem> listCartItemTmp=cartItemList.getCartItems(cartItemList);
			shoppingCartService.clearCart();
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
}