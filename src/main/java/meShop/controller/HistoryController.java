package meShop.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import meShop.Constant.ConstantPageId;
import meShop.converter.OrderConverter;
import meShop.dto.OrderDTO;
import meShop.dto.OrderDetailDTO;
import meShop.model.OrderModel;
import meShop.model.ProductModel;
import meShop.model.UserModel;
import meShop.repository.OrderDetailRepository;
import meShop.service.CookieService;
import meShop.service.OrderDetailService;
import meShop.service.OrderService;
import meShop.service.ProductService;
import meShop.service.SessionService;
import meShop.service.ShoppingCartService;
import meShop.service.UserService;

@Controller
public class HistoryController {
	@Autowired SessionService sessionService;
	@Autowired CookieService cookieService;
	@Autowired OrderDetailService orderDetailService;
	@Autowired ProductService productService;
	@Autowired OrderService orderService;
	@Autowired UserService userService;
	@Autowired OrderConverter orderConverter;
	@Autowired
	ShoppingCartService shoppingCartService;
	@GetMapping(value = "/history/views")
	public String getHistoryMainPage(Model model) {
		if(!sessionService.checkSecurity()){
			return "redirect:/account/"+ConstantPageId.HISTORYBUY_PAGE+"/login";
		}
		model.addAttribute("total", shoppingCartService.getTotal());
		model.addAttribute("totalCartItems", shoppingCartService.getAllCartItem().size());
		String userName=sessionService.get("USERNAME","");
		if(!userName.equals("")) {
			UserModel user=userService.getUserByUserName(userName);
			List<OrderModel> orderModels=orderService.findOrdersByUserId(String.valueOf(user.getId()));
			Collections.reverse(orderModels);
			List<OrderDTO> orders=new ArrayList<>();
			orderModels.forEach(orderModel->{
				orders.add(orderConverter.toDTO(orderModel));
			});
			System.out.println("order size: "+orders.size());
			List<List<OrderDetailDTO>> orderDetails=new ArrayList<>();
			//List<UserModel> users=new ArrayList<>();
			orders.forEach(order->{
				//users.add(userService.getUserById(Integer.parseInt(order.getUserId())));
				List<OrderDetailDTO> OrderDetailTmp=orderDetailService.getOrderDetailByOrderId(Integer.parseInt(order.getId()));
				System.out.println("order id: "+order.getId()+" size: "+OrderDetailTmp.size());
				for (int i = 0; i < OrderDetailTmp.size(); i++) {
					ProductModel p=productService.getProductById(Integer.parseInt(OrderDetailTmp.get(i).getProductId()));
					String productBase64Images= Base64.getEncoder().encodeToString(p.getImage());
					OrderDetailTmp.get(i).setProductTitle(p.getTitle());
					OrderDetailTmp.get(i).setProductImg(productBase64Images);
					System.out.println("item "+i+" productId: " +OrderDetailTmp.get(i).getProductId()+" PorductName: "+p.getTitle());
				}
				orderDetails.add(OrderDetailTmp);
				System.out.println("--------------------------------------------------------");
			});
			//String userName=sessionService.get("USERNAME","");
			model.addAttribute("userName", userName);
			model.addAttribute("orders", orders);
			//model.addAttribute("users", users);
			model.addAttribute("orderDetails", orderDetails);

			
		}
		
		return "History";
	}
	public String updateUserState() {
		String userName="";
		if(cookieService.get("USERNAME")!=null) {
			userName=cookieService.getValue("USERNAME","GUEST");
		} else
			userName=sessionService.get("USERNAME")!=null?sessionService.get("USERNAME"):"GUEST";
		return userName;
	}
}
