package meShop.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import meShop.Constant.ConstantPageId;
import meShop.dto.CategoryDTO;
import meShop.dto.OrderDTO;
import meShop.dto.OrderDetailDTO;
import meShop.dto.ProductDTO;
import meShop.model.OrderModel;
import meShop.model.OrderStateModel;
import meShop.model.ProductModel;
import meShop.model.UserModel;
import meShop.service.CategoryService;
import meShop.service.OrderDetailService;
import meShop.service.OrderService;
import meShop.service.ProductService;
import meShop.service.SessionService;
import meShop.service.UserService;
@Controller
@RequestMapping("/shipper")
public class ShipperController {
	@Autowired CategoryService categoryService;
	@Autowired SessionService sessionService;
	@Autowired UserService userService;
	@Autowired ProductService productService;
	@Autowired OrderService orderService;
	@Autowired OrderDetailService orderDetailService;
	//don admin da duyet 
	// @GetMapping("/views")
	// public ResponseEntity<?> getAllOrderHTTP() {
	// 	Map<String, Object> map = new LinkedHashMap<String, Object>();
	// 	try {
	// 		map.put("status", 2);
	// 		map.put("message", orderService.getOrderByStateCode(1));
	// 		return new ResponseEntity<>(map, HttpStatus.OK);
	// 	} catch (Exception ex) {
	// 		map.clear();
	// 		map.put("status", 0);
	// 		map.put("message", "Data is not found");
	// 		return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
	// 	}
	// }
	@GetMapping("/views")
	public String getAllOrder(Model model,@RequestParam(name="page",defaultValue="all") String page) {
		int action=-1;
		switch (page) {
			case "all":
			action=1;
				break;
			case "me":
				action=3;
				break;
			default:
				break;
		}
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		if(sessionService.checkSecurityShipper()&&action!=-1){
			List<OrderDTO> orders=orderService.getOrderByStateCode(action);
		Collections.reverse(orders);
		System.out.println("order admin size: "+orders.size());
		List<List<OrderDetailDTO>> orderDetails=new ArrayList<>();
		List<UserModel> users=new ArrayList<>();
		orders.forEach(order->{
			users.add(userService.getUserById(Integer.parseInt(order.getUserId())));
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
		String userName=sessionService.get("USERNAME","");
		model.addAttribute("userName", userName);
		model.addAttribute("orders", orders);
		model.addAttribute("users", users);
		model.addAttribute("orderDetails", orderDetails);
			if(action==1) return "ShipperOrder"; else return "ShipperMyOrder";
		}
		else {
			if(action==1) return "redirect:/account/"+ConstantPageId.ORDER_ACCEPTED_SHIPPER_PAGE+"/login";
			else return "redirect:/account/"+ConstantPageId.ORDER_DELIVERI_SHIPPER_PAGE+"/login";	
		}
	}	
	@PostMapping("/orders/action") 
	  public ResponseEntity handleActionOrders( @RequestParam("orderId") String orderId,@RequestParam("action") int action ,@RequestParam(name="messageError",defaultValue = "") String messageError) {
		 //System.out.println("productId "+productId+" quantity "+quantity);
		 //System.out.println("order action: "+action+" orderId: "+orderId);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		String userName=sessionService.get("USERNAME", "");
		if(userName.equals("")){
			map.put("status", 0);
			map.put("message", "no permission");
			return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		} 
		else if(action<3) {
			map.put("status", 0);
			map.put("message", "no permission");
			return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		}
		
			UserModel user=userService.getUserByUserName(userName);
			if(user.getRole().getCode()!=2){
				map.put("status", 0);
				map.put("message", "no permission");
				return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
			}
		
		OrderModel orderModel=orderService.getOrderById(Integer.parseInt(orderId)); 
		if(action==3) {
			//action shipper nhan don
			System.out.println("action 3");
			orderModel.setShipper(user);
		} else {
			System.out.println("action "+action);
		}
		if(action==5) {
			orderModel.setMessageError(messageError);
		}
	    orderService.saveOrder(orderModel,action);
		
		map.put("status", 0);
		map.put("message", "save state order ok");
		return new ResponseEntity<>(map, HttpStatus.OK); 
	  }
}
