//package meShop.controller;
//
//import java.util.ArrayList;
//import java.util.Base64;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import meShop.controller.ShoppingCartController.UpdateReturn;
//import meShop.dto.OrderDTO;
//import meShop.dto.OrderDetailDTO;
//import meShop.model.OrderModel;
//import meShop.model.ProductModel;
//import meShop.model.UserModel;
//import meShop.service.OrderDetailService;
//import meShop.service.OrderService;
//import meShop.service.ProductService;
//import meShop.service.UserService;
//
//@Controller
//@RequestMapping(value = "/admin/orders")
//public class HistoryAdminController {
//	@Autowired OrderService orderService;
//	@Autowired OrderDetailService orderDetailService;
//	@Autowired ProductService productService;
//	@Autowired UserService userService;
//	@GetMapping(value = "/views")
//	String getOrderAdminPage(Model model) {
//		List<OrderDTO> orders=orderService.getAllOrders();
//		System.out.println("order admin size: "+orders.size());
//		List<List<OrderDetailDTO>> orderDetails=new ArrayList<>();
//		List<UserModel> users=new ArrayList<>();
//		orders.forEach(order->{
//			users.add(userService.getUserById(Integer.parseInt(order.getUserId())));
//			List<OrderDetailDTO> OrderDetailTmp=orderDetailService.getOrderDetailByOrderId(Integer.parseInt(order.getId()));
//			
//			System.out.println("order id: "+order.getId()+" size: "+OrderDetailTmp.size());
//			for (int i = 0; i < OrderDetailTmp.size(); i++) {
//				ProductModel p=productService.getProductById(Integer.parseInt(OrderDetailTmp.get(i).getProductId()));
//				String productBase64Images= Base64.getEncoder().encodeToString(p.getImage());
//				OrderDetailTmp.get(i).setProductTitle(p.getTitle());
//				OrderDetailTmp.get(i).setProductImg(productBase64Images);
//				System.out.println("item "+i+" productId: " +OrderDetailTmp.get(i).getProductId()+" PorductName: "+p.getTitle());
//			}
//			orderDetails.add(OrderDetailTmp);
//			System.out.println("--------------------------------------------------------");
//		});
//		model.addAttribute("orders", orders);
//		model.addAttribute("users", users);
//		model.addAttribute("orderDetails", orderDetails);
//		return "orderAdminPage";
//	}
//	@PostMapping("/action") 
//	  public ResponseEntity handleActionOrders( @RequestParam("orderId") String orderId,@RequestParam("action") int action ) {
//		 //System.out.println("productId "+productId+" quantity "+quantity);
//		 //System.out.println("order action: "+action+" orderId: "+orderId);
//		OrderModel orderModel=orderService.getOrderById(Integer.parseInt(orderId)); 
//		orderModel.setState(action);
//	    orderService.saveOrder(orderModel);
//		return ResponseEntity.status(HttpStatus.OK).body("1");
//	  }
//}
