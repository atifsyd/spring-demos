package com.atifsyd.springintro.springMVC.controller;

import com.atifsyd.springintro.springMVC.model.Product;
import com.atifsyd.springintro.springMVC.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProductController {

    ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/product/list")
    public String getProductData(Model model) {
        model.addAttribute("products", productService.listAll());
        return "product/list";
    }

    @RequestMapping("/product/show/{id}")
    public String getProduct(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.getById(id));
        return "product/show";
    }

    @RequestMapping("/product/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        return "product/productform";
    }

    @RequestMapping(value = "/product/edit/{id}")
    public String editProduct(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.getById(id));
        return "product/productform";
    }

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public String saveOrUpdateProduct(Product product){
        Product savedProduct = productService.saveOrUpdate(product);
        return "redirect:/product/show/" + savedProduct.getId();
    }

    @RequestMapping(value = "/product/remove/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        productService.remove(id);
        return "redirect:/product/list";
    }
}
