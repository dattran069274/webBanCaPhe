package meShop.controller;

import java.lang.foreign.VaList;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import meShop.dto.ProductDTO;
import meShop.model.ProductModel;
import meShop.service.ProductService;

@Controller
@RequestMapping(value = "/product")
class ProductController{
    @Autowired ProductService productService;
    @GetMapping(value="/views")
    String getProductHome(Model model){
        List<ProductDTO> productDTOs=productService.getAllProducts();
		List<String> productImages=new ArrayList<>();
		model.addAttribute("productList", productDTOs);
		productDTOs.forEach(productDTO->{
			productImages.add(Base64.getEncoder().encodeToString(productDTO.getImage()));
		});
		model.addAttribute("productImages", productImages);
        return "Product";
    }
    @GetMapping(value = "/{id}")
	String getProductDetail(@PathVariable(value = "id") long id,Model model){
		
		ProductModel p=productService.getProductById(id);
		String img=Base64.getEncoder().encodeToString(p.getImage());
		String img2=Base64.getEncoder().encodeToString(p.getImage());
		String img3=Base64.getEncoder().encodeToString(p.getImage());
		model.addAttribute("product", p);
		model.addAttribute("productImg", img);
		model.addAttribute("productImg2", img2);
		model.addAttribute("productImg3", img3);
		return "infoProduct";
	}
}