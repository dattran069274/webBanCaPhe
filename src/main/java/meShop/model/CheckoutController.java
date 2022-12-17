package meShop.model;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.var;
import lombok.extern.slf4j.Slf4j;
import meShop.service.OrderService;

@RestController
@RequestMapping(value = "/checkout")
@Slf4j
public class CheckoutController {

	private final PayPalHttpClient payPalHttpClient;
    private final OrderDAO orderDAO;
    @Autowired OrderService orderService;
    @Autowired
    public CheckoutController(PayPalHttpClient payPalHttpClient, OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
        this.payPalHttpClient = payPalHttpClient;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> checkout(@RequestBody OrderDTO orderDTO) throws Exception {
    	System.out.println("OrderDTO: "+orderDTO);
    	PayPalAppContextDTO appContext = new PayPalAppContextDTO();
        appContext.setReturnUrl("http://localhost:8888/checkout/success");
        appContext.setBrandName("My brand");
        appContext.setLandingPage(PaymentLandingPage.BILLING);
        orderDTO.setApplicationContext(appContext);
        OrderResponseDTO orderResponse = payPalHttpClient.createOrder(orderDTO);
        OrderModel entity = new OrderModel();
        Date date=new Date();
        entity.setOrderDate(date);
        entity.setPaypalOrderId(orderResponse.getId());
        entity.setPaypalOrderStatus(orderResponse.getStatus().toString());
        orderService.saveOrder(entity,0);
        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping(value = "/success")
    public String paymentSuccess(HttpServletRequest request,Model model) {
        System.out.println("success");
    	String orderId = request.getParameter("token");
    	OrderModel out = orderService.findByPaypalOrderId(orderId);
        out.setPaypalOrderStatus(OrderStatus.APPROVED.toString());
        orderService.saveOrder(out,0);
        model.addAttribute("message", "Payment success!");
        return "Checkout";
    }

}
