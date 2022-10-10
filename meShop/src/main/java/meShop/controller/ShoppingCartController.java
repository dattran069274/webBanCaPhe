package meShop.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import org.json.Cookie;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import meShop.Constant.ConstantPageId;
import meShop.converter.MainConverter;
import meShop.dto.CartItemsDTO;
import meShop.dto.OrderDTO;
import meShop.dto.OrderDetailDTO;
import meShop.model.CartItem;
import meShop.model.CartItemList;
import meShop.model.ProductModel;
import meShop.service.CookieService;
import meShop.service.OrderDetailService;
import meShop.service.OrderService;
import meShop.service.ProductService;
import meShop.service.SessionService;
import meShop.service.ShoppingCartService;

@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {
	@Autowired
	ShoppingCartService shoppingCartService;
	@Autowired
	ProductService productService;
	@Autowired OrderService orderService;
	@Autowired OrderDetailService orderDetailService;
	@Autowired
	CookieService cookieService;
	@Autowired SessionService sessionService;
	@Autowired MainConverter mainConverter;
	ConstantPageId constantPageId;

	private void initCart() {
		System.out.println("init cart");
		if(cookieService.get("CARTLIST")!=null&&shoppingCartService.getAllCartItem().size()==0)
		{
			System.out.println("init cart form cookie");
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
	}
	
	
	
	@GetMapping(value = "/views")
	String getViews(Model model) {
		initCart();
		CartItemsDTO cartItemsDTO = new CartItemsDTO(shoppingCartService.getAllCartItem());
		model.addAttribute("cartItemsDTO", cartItemsDTO);
		model.addAttribute("total", shoppingCartService.getTotal());
		List<String> cartItemImages = new ArrayList<>();
		if (shoppingCartService.getAllCartItem().size() != 0)
			shoppingCartService.getAllCartItem().forEach(cartItem -> {
				cartItemImages.add(Base64.getEncoder().encodeToString(cartItem.getImgae()));
			});
		model.addAttribute("cartItemImages", cartItemImages);
		return "cartIndex";
	}
	
	@GetMapping(value = "/add/{id}")
	String addCart(@PathVariable("id") String productId) throws ClassNotFoundException{
		ProductModel product=productService.getProductById(Integer.parseInt(productId));
		CartItem cartItem=new CartItem();
		if(product!=null) {
			cartItem.setTitle(product.getTitle());
			cartItem.setImgae(product.getImage());
			cartItem.setPrice(product.getPrice());
			cartItem.setProductId(productId);
			int quantity=shoppingCartService.addCart(cartItem);
			 updeteCookieCart();
		}
		
		return "redirect:/productIndex";
	}

	private void updeteCookieCart() throws ClassNotFoundException {
		if(shoppingCartService.getAllCartItem().size()==0) {
			System.out.println("remove cart cookie");
			cookieService.remove("CARTLIST");
			return;
		}
		List<String> itemId=new ArrayList<>();
		 List<Integer> itemQuantity=new ArrayList<>();
		 shoppingCartService.getAllCartItem().forEach(item->{
			 itemId.add(item.getProductId());
			 itemQuantity.add(item.getQuantity());
		 });
		 String value;
		try {
			List<CartItem> list=new ArrayList<>();
			shoppingCartService.getAllCartItem().forEach(item->{
				list.add(item);
			});
			value = MainConverter.To_String(new CartItemList(itemId,itemQuantity));
			cookieService.add("CARTLIST",value,24*30);
			CartItemList cartItems=(CartItemList) MainConverter.From_String(value);
			System.out.println("cartItems decode size: "+cartItems);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}



	@GetMapping(value = "/delete/{id}")
	String deleteCart(@PathVariable("id") String productId) {
		shoppingCartService.removeCart(productId);
		if(cookieService.getValue("CARTLIST", "")!=null) {
			try {
				updeteCookieCart();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "redirect:/shopping-cart/views";
	}
	class UpdateItem{
		String productId;
		int quantity;
		public String getProductId() {
			return productId;
		}
		public void setProductId(String productId) {
			this.productId = productId;
		}
		public int getQuantity() {
			return quantity;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}	
	}
//	@RequestMapping(value = "/updateCart",method = RequestMethod.POST, consumes = {"application/x-www-form-urlencoded"})
//	public String createAccount(@RequestBody UpdateItem updateItem){
//		shoppingCartService.updateCart(updateItem.getProductId(),updateItem.getQuantity());
//	    return "login";
//	}
	class UpdateReturn{
		double total;
		UpdateReturn(double a){this.total=a;}
		public double getTotal() {
			return total;
		}
		public void setTotal(double total) {
			this.total = total;
		}
		
	}
	 @PostMapping("/updateCart") 
	  public ResponseEntity handleFileUpload( @RequestParam("productId") String productId,@RequestParam("quantity") int quantity ) {
		 //System.out.println("productId "+productId+" quantity "+quantity);
		 if(quantity==0) {
			 deleteCart(productId);
			 System.out.println("deleteCart form updateCart");	
		 } else shoppingCartService.updateCart(productId,quantity);
	    return ResponseEntity.status(HttpStatus.OK).body(new UpdateReturn(shoppingCartService.getTotal()));
	  }
	 
		

	@PostMapping(value = "/checkout")
	public String checkout(@ModelAttribute CartItemsDTO cartItemsDTO) {
		System.out.println("checkout");
		//check current user
		//if has 
		// add into order -> idOrder, idUser B1
		// add into OrderDetail (1 idOrder) + (n idProduct)
				// foreach shoppingcartService-> item -> idProduct
		//! if has => login  =>back to B1
		String userName=sessionService.get("USERNAME", "");
		if(userName!="") {
			System.out.println("USER NAME: "+sessionService.get("USERNAME", "eo biet"));
			System.out.println("USER ID: "+sessionService.get("USERID", "eo biet").toString());
			OrderDTO orderDTO=new OrderDTO();
			Date date=new Date();
			System.out.println("date "+date);
			orderDTO.setOrderDate(date);
			orderDTO.setUserId(sessionService.get("USERID"));
			Long orderId=orderService.saveOrder(orderDTO);
			shoppingCartService.getAllCartItem().forEach(productItem->{
				OrderDetailDTO detailDTO=new OrderDetailDTO();
				detailDTO.setOrderId(String.valueOf(orderId));
				detailDTO.setPrice(productItem.getPrice());
				detailDTO.setProductId(productItem.getProductId());
				detailDTO.setQuantity(productItem.getQuantity());
				detailDTO.setAmount(productItem.getQuantity()*productItem.getPrice());
				detailDTO.setCreatedDate(date);
				detailDTO.setModifiedDate(date);
				Long id=orderDetailService.saveOrderDetail(detailDTO);
				//System.out.println("save "+productItem.getProductId()+ "return id : "+id );
			});
			//xoa cartList trong cookie va trong shoppingcart
			cookieService.remove("CARTLIST");
			shoppingCartService.clearCart();
		}
		else return "redirect:/account/showlogin/"+constantPageId.CART_PAGE;
		return "redirect:/shopping-cart/views";
	}
}
