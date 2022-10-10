package meShop.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import meShop.Constant.ConstantPageId;
import meShop.dto.OrderDetailDTO;
import meShop.model.ProductModel;
import meShop.repository.OrderDetailRepository;
import meShop.service.CookieService;
import meShop.service.OrderDetailService;
import meShop.service.OrderService;
import meShop.service.ProductService;
import meShop.service.SessionService;

@Controller
public class HistoryController {
	@Autowired SessionService sessionService;
	@Autowired CookieService cookieService;
	@Autowired OrderDetailService orderDetailService;
	@Autowired ProductService productService;
	@Autowired OrderService orderService;
	@GetMapping(value = "/historyIndex")
	public String getHistoryMainPage(Model model) {
		String userName=updateUserState();
		if(userName.equals("")) return "redirect:/account/showlogin/"+ConstantPageId.HISTORYBUY_PAGE;
		model.addAttribute("userName",userName );
		String userId=sessionService.get("USERID","");
		//lay don hang order cuoi (lastest)
		List<OrderDetailDTO> orderDetail=orderDetailService.getOrderDetailByOrderId(orderService.findLastOneByUserId(userId).get(orderService.findLastOneByUserId(userId).size()-1).getId());
		List<Long> productIds=new ArrayList<>(); 
		List<Integer> quantitys=new ArrayList<>();
		orderDetail.forEach(od->{
			productIds.add((long)Integer.valueOf(od.getProductId()));
			quantitys.add(od.getQuantity());
			System.out.println("ProductId: "+od.getProductId());
		});
		List<ProductModel> products=productService.getProductByListId(productIds);
		List<String> productImages=new ArrayList<>();
		model.addAttribute("productList",products );
		products.forEach(product->{
			productImages.add(Base64.getEncoder().encodeToString(product.getImage()));
		});
		model.addAttribute("productImages",productImages );
		model.addAttribute("quantitys",quantitys );
		model.addAttribute("state",orderService.findLastOneByUserId(userId).get(orderService.findLastOneByUserId(userId).size()-1).getState());
		return "historyIndex";
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
