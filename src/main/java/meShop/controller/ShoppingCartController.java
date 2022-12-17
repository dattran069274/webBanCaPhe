package meShop.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.json.Cookie;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
import meShop.model.MoneyDTO;
import meShop.model.OrderDAO;
import meShop.model.OrderIntent;
import meShop.model.OrderModel;
import meShop.model.OrderResponseDTO;
import meShop.model.OrderStatus;
import meShop.model.PayPalAppContextDTO;
import meShop.model.PayPalHttpClient;
import meShop.model.PaymentLandingPage;
import meShop.model.ProductModel;
import meShop.model.PurchaseUnit;
import meShop.service.CookieService;
import meShop.service.OrderDetailService;
import meShop.service.OrderService;
import meShop.service.ProductService;
import meShop.service.SessionService;
import meShop.service.ShoppingCartService;
import meShop.service.UserService;

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
	@Autowired UserService userService;
	private final PayPalHttpClient payPalHttpClient;
    private final OrderDAO orderDAO;
	@Autowired
    public ShoppingCartController(PayPalHttpClient payPalHttpClient, OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
        this.payPalHttpClient = payPalHttpClient;
    }
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
		// initCart();
		CartItemsDTO cartItemsDTO = new CartItemsDTO(shoppingCartService.getAllCartItem());
		model.addAttribute("cartItemsDTO", cartItemsDTO);
		model.addAttribute("total", shoppingCartService.getTotal());
		List<String> cartItemImages = new ArrayList<>();
		if (shoppingCartService.getAllCartItem().size() != 0)
			shoppingCartService.getAllCartItem().forEach(cartItem -> {
				cartItemImages.add(Base64.getEncoder().encodeToString(cartItem.getImgae()));
			});
		model.addAttribute("userName",sessionService.get("USERNAME")!=null?sessionService.get("USERNAME"):"GUEST");
		model.addAttribute("cartItemImages", cartItemImages);
		//return "cartIndex";
		return "Cart";
	}
	
	@GetMapping(value = "/add/{id}")
	ResponseEntity<?> addCart(@PathVariable("id") String productId,@RequestParam("num") String num,@RequestParam("go") Boolean go) throws ClassNotFoundException{
		System.out.println("addCart "+num);
		System.out.println("go "+go);
		ProductModel product=productService.getProductById(Integer.parseInt(productId));
		CartItem cartItem=new CartItem();
		if(product!=null) {
			cartItem.setTitle(product.getTitle());
			cartItem.setImgae(product.getImage());
			cartItem.setPrice(product.getPrice());
			cartItem.setProductId(productId);
			int quantity=shoppingCartService.addCart(cartItem,Integer.parseInt(num));
			 updeteCookieCart();
		}
		
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("status", 1);
		return new ResponseEntity<>(map, HttpStatus.CREATED);
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
	 
			
	 @GetMapping(value = "/checkout")
	 String getCheckoutPage(Model model) {
		 CartItemsDTO cartItemsDTO = new CartItemsDTO(shoppingCartService.getAllCartItem());
			model.addAttribute("cartItemsDTO", cartItemsDTO);
			model.addAttribute("total", shoppingCartService.getTotal());
			List<String> cartItemImages = new ArrayList<>();
			if (shoppingCartService.getAllCartItem().size() != 0)
				shoppingCartService.getAllCartItem().forEach(cartItem -> {
					cartItemImages.add(Base64.getEncoder().encodeToString(cartItem.getImgae()));
				});
			String address=sessionService.get("USERADDRESS", "");
			String fullName=sessionService.get("USERFULLNAME", "");
			String phone=sessionService.get("USERPHONE", "");
			model.addAttribute("phone",phone);
			model.addAttribute("address",address);
			model.addAttribute("fullName",fullName);
			model.addAttribute("cartItemImages", cartItemImages);
			model.addAttribute("userName",sessionService.get("USERNAME")!=null?sessionService.get("USERNAME"):"GUEST");

		 return "CheckOut";
	 }
	 public OrderResponseDTO checkoutPaypal(meShop.model.OrderDTO orderDTO,long id) throws Exception {
    	System.out.println("OrderDTO: "+orderDTO);
    	PayPalAppContextDTO appContext = new PayPalAppContextDTO();
        appContext.setReturnUrl("http://localhost:8888/shopping-cart/success");
        appContext.setBrandName("My brand");
        appContext.setLandingPage(PaymentLandingPage.BILLING);
        orderDTO.setApplicationContext(appContext);
        OrderResponseDTO orderResponse = payPalHttpClient.createOrder(orderDTO);

        OrderModel entity = orderService.getOrderById(id);
        entity.setPaypalOrderId(orderResponse.getId());
		if(orderResponse.getStatus()==null) {
			entity.setPaypalOrderStatus("CREATED");
		}
		else
        entity.setPaypalOrderStatus(orderResponse.getStatus().toString());
        //Order out = orderDAO.save(entity);
        orderService.saveOrder(entity, 0);
        //System.out.println("Saved order: {}"+ out);
        return orderResponse;
    }

    @GetMapping(value = "/success")
    public String paymentSuccess(HttpServletRequest request) {
    	String orderId = request.getParameter("token");
    	OrderModel out = orderService.findByPaypalOrderId(orderId);
		checkoutAndRemoveCart(out.getId());
        out.setPaypalOrderStatus(OrderStatus.APPROVED.toString());
        orderService.saveOrder(out, 0);
        System.out.println("payment paypal success");
        return "PaypalMess";
    }
	public double readVNDUSDAPI(){
		URL url;
		try {
			url = new URL("https://api.currencyfreaks.com/latest?apikey=e820c2797cbe459eb077f22d4eab1816");
		

        // Creating an HTTP connection
        HttpURLConnection MyConn = (HttpURLConnection) url.openConnection();

        // Set the request method to "GET"
        MyConn.setRequestMethod("GET");

        // Collect the response code
        int responseCode = MyConn.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == MyConn.HTTP_OK) {
            // Create a reader with the input stream reader.
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    MyConn.getInputStream()));
            String inputLine;

            // Create a string buffer
            StringBuffer response = new StringBuffer();

            // Write each of the input line
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //Show the output
            System.out.println(response.toString());
			JSONObject json = new JSONObject(response.toString());  
			JSONObject rates = json.getJSONObject("rates");  
			System.out.println(rates.getString("VND"));  
			return Double.parseDouble(rates.getString("VND"));
        } else {
            System.out.println("Error found !!!");
        }
		}
	 catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return -1;
    }
	 @PostMapping(value = "/checkout")
	 public String checkout(@ModelAttribute CartItemsDTO cartItemsDTO,@RequestParam(name="paypal",defaultValue = "false") boolean isPaypal,@RequestParam("receiverName") String receiverName,@RequestParam("receiverAddress") String receiverAddress) {
		 if(!sessionService.checkSecurity()){
			 return "redirect:/account/"+ConstantPageId.CHECKOUT_PAGE+"/login";
		 }
		 double totalPrice=shoppingCartService.getTotal()/readVNDUSDAPI();
		 System.out.println("checkout");
		 System.out.println("receiverName"+receiverName);
		 System.out.println("receiverAddress"+receiverAddress);
		 System.out.println("totalPrice"+totalPrice);
		 //check current user
		 //if has 
		 // add into order -> idOrder, idUser B1
		 // add into OrderDetail (1 idOrder) + (n idProduct)
				 // foreach shoppingcartService-> item -> idProduct
		 //! if has => login  =>back to B1
		 OrderResponseDTO orderResponseDTO=null;
		 String userName=sessionService.get("USERNAME", "");
		 if(userName!="") {
			 System.out.println("USER NAME: "+sessionService.get("USERNAME", ""));
			 System.out.println("USER ID: "+sessionService.get("USERID", "").toString());
			 OrderDTO orderDTO=new OrderDTO();
			 Date date=new Date();
			 System.out.println("date "+date);
			 orderDTO.setOrderDate(date);
			 orderDTO.setUserId(sessionService.get("USERID"));
			 orderDTO.setReceiverAddress(receiverAddress);
			 orderDTO.setReceiverName(receiverName);
			 orderDTO.setState(0);
			 Long orderId=orderService.saveOrder(orderDTO);
			 //paypal
			 if(isPaypal) {
				 meShop.model.OrderDTO dto=new meShop.model.OrderDTO();
				 dto.setIntent(OrderIntent.CAPTURE);
				 List<PurchaseUnit> purchaseUnits=new ArrayList<>();
				 PurchaseUnit purchaseUnit=new PurchaseUnit();
				 MoneyDTO moneyDTO=new MoneyDTO();
				 moneyDTO.setCurrencyCode("USD");
				 //
				 System.out.println("totalPrice"+totalPrice);
				 moneyDTO.setValue(String.format("%.2f",totalPrice));
				 purchaseUnit.setAmount(moneyDTO);
				 purchaseUnits.add(purchaseUnit);
				 dto.setPurchaseUnits(purchaseUnits);
				 try {
					  orderResponseDTO=checkoutPaypal(dto,orderId);
					 System.out.println(orderResponseDTO); 
				 } catch (Exception e) {
					 // TODO Auto-generated catch block
					 e.printStackTrace();
					 System.out.println("failed checkoutPaypal(dto)"); 
				 }
			 }
			 else {
				checkoutAndRemoveCart(orderId);
			 }
			 
		}
		else return "redirect:/account/showlogin/"+constantPageId.CART_PAGE;
		if(isPaypal) {
			return "redirect:"+orderResponseDTO.getLinks().get(1).getHref();
		}
		return "redirect:/shopping-cart/views";
	}
	public void checkoutAndRemoveCart(long orderId){
		Date date=new Date();
		System.out.println("save shoppingCartService "+(shoppingCartService.getAllCartItem()==null?"null":"eo null"));
		System.out.println("save shoppingCartService "+shoppingCartService.getAllCartItem().size());
		shoppingCartService.getAllCartItem().forEach(productItem->{
			ProductModel productModel=productService.getProductById(Integer.parseInt(productItem.getProductId()));
			productModel.setSaled(productItem.getQuantity());
			productService.save(productModel);
			OrderDetailDTO detailDTO=new OrderDetailDTO();
			detailDTO.setOrderId(String.valueOf(orderId));
			detailDTO.setPrice(productItem.getPrice());
			detailDTO.setProductId(productItem.getProductId());
			detailDTO.setQuantity(productItem.getQuantity());
			detailDTO.setAmount(productItem.getQuantity()*productItem.getPrice());
			detailDTO.setCreatedDate(date);
			detailDTO.setModifiedDate(date);
			Long id=orderDetailService.saveOrderDetail(detailDTO);
			System.out.println("save "+productItem.getTitle()+" q:" +productItem.getQuantity());
		});
		//xoa cartList trong cookie va trong shoppingcart
	   cookieService.remove("CARTLIST");
	   shoppingCartService.clearCart();
	}
	
}
