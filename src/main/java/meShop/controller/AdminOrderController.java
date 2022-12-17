package meShop.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
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

import meShop.Constant.ConstantPageId;
import meShop.controller.ShoppingCartController.UpdateReturn;
import meShop.dto.CategoryDTO;
import meShop.dto.OrderDTO;
import meShop.dto.OrderDetailDTO;
import meShop.model.CategoryModel;
import meShop.model.Employee;
import meShop.model.OrderModel;
import meShop.model.ProductModel;
import meShop.model.UserModel;
import meShop.service.CategoryService;
import meShop.service.OrderDetailService;
import meShop.service.OrderService;
import meShop.service.ProductService;
import meShop.service.SessionService;
import meShop.service.UserService;

@Controller
@RequestMapping(value = "/admin/orders")
public class AdminOrderController {
	@Autowired OrderService orderService;
	@Autowired OrderDetailService orderDetailService;
	@Autowired ProductService productService;
	@Autowired UserService userService;
	@Autowired SessionService sessionService;
	@GetMapping(value = "/views")
	String getOrderAdminPage(Model model) {

		if(!sessionService.checkSecurityAdmin()){
			return "redirect:/account/"+ConstantPageId.ORDER_ADMIN_PAGE+"/login";
		}
		List<OrderDTO> orders=orderService.getAllOrders();
		Collections.reverse(orders);
		System.out.println("order admin size: "+orders.size());
		List<List<OrderDetailDTO>> orderDetails=new ArrayList<>();
		List<UserModel> users=new ArrayList<>();
		List<UserModel> shippers=new ArrayList<>();
		orders.forEach(order->{
			UserModel shipper=order.getShipper();
			shippers.add(shipper);
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
		model.addAttribute("shippers", shippers);
		model.addAttribute("orderDetails", orderDetails);
		return "AdminOrder";
	}
	@PostMapping("/action") 
	  public ResponseEntity handleActionOrders( @RequestParam("orderId") String orderId,@RequestParam("action") int action ) {
		 //System.out.println("productId "+productId+" quantity "+quantity);
		 //System.out.println("order action: "+action+" orderId: "+orderId);
		 Map<String, Object> map = new LinkedHashMap<String, Object>();
		 String userName=sessionService.get("USERNAME", "");
		 if(userName.equals("")){
			 map.put("status", 0);
			 map.put("message", "no permission");
			 return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		} 
		UserModel user=userService.getUserByUserName(userName);
		if(action!=2&&action!=1) {
			map.put("status", 0);
				map.put("message", "action no found");
				return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		}
		if(action==2){
			if(user.getRole().getCode()!=0){
				map.put("status", 0);
				map.put("message", "no permission");
				return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
			}
		}
		if(action==1){
			if(user.getRole().getCode()!=1){
				map.put("status", 0);
				map.put("message", "no permission");
				return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
			}
		}
		
		OrderModel orderModel=orderService.getOrderById(Integer.parseInt(orderId)); 
	    orderService.saveOrder(orderModel,action);
		return ResponseEntity.status(HttpStatus.OK).body("1");




		
	  }
	@GetMapping(value = "/details/{id}")
	String getOrderDetail(Model model,@PathVariable("id") int id) {
		OrderModel order=orderService.getOrderById(id);
		long userId=order.getUserModel().getId();
		if(order!=null) {
			List<OrderDetailDTO> orderDetails=new ArrayList<>();
			UserModel user=userService.getUserById(userId);
			List<OrderDetailDTO> OrderDetailTmp=orderDetailService.getOrderDetailByOrderId(order.getId());
			System.out.println("order id: "+order.getId()+" size: "+OrderDetailTmp.size());
			for (int i = 0; i < OrderDetailTmp.size(); i++) {
				ProductModel p=productService.getProductById(Integer.parseInt(OrderDetailTmp.get(i).getProductId()));
				String productBase64Images= Base64.getEncoder().encodeToString(p.getImage());
				OrderDetailTmp.get(i).setProductTitle(p.getTitle());
				OrderDetailTmp.get(i).setProductImg(productBase64Images);
				System.out.println("item "+i+" productId: " +OrderDetailTmp.get(i).getProductId()+" PorductName: "+p.getTitle()+" quantity: "+OrderDetailTmp.get(i).getQuantity());
			}
			orderDetails.addAll(OrderDetailTmp);
			model.addAttribute("orderDetails", orderDetails);
			model.addAttribute("user", user);
			model.addAttribute("order", order);
		}
		
			System.out.println("------------------------getOrderDetail----------------------");
		return "AdminOrderDetail";
	}
}


	
