package com.cydeo.controller;

import com.cydeo.dto.CategoryDto;
import com.cydeo.dto.ProductDto;
import com.cydeo.enums.ProductUnit;
import com.cydeo.service.CategoryService;
import com.cydeo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    ProductService productService;
    CategoryService categoryService;
    List<ProductUnit> productUnits = Arrays.asList(ProductUnit.values());

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String listOfProducts(Model model) {
//        model.addAttribute("product", new ProductDto());
        model.addAttribute("products", productService.getAllProductsForCurrentCompany());
        return "product/product-list";
    }

    @GetMapping("/create")
    public String createProduct(Model model) {
        model.addAttribute("newProduct", new ProductDto());
        model.addAttribute("categories", categoryService.getAllCategoriesForCurrentCompany());
        model.addAttribute("productUnits", productUnits);
        return "product/product-create";
    }

    @PostMapping("/create")
    public String insertProduct(@ModelAttribute("newProduct") ProductDto productDto, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategoriesForCurrentCompany());
            model.addAttribute("productUnits", productUnits);
            return "redirect:/product/product-create";
        }
            productService.create(productDto);
            model.addAttribute("products", productService.getAllProductsForCurrentCompany());
            return "product/product-list";
        }

    @GetMapping("/update/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {
        model.addAttribute("product", productService.findProductById(id));
        model.addAttribute("categories", categoryService.getAllCategoriesForCurrentCompany());
        model.addAttribute("productUnits", productUnits);
        return "product/product-update";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id")Long id, @ModelAttribute("product") ProductDto product, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("product", productService.findProductById(id));
            model.addAttribute("categories", categoryService.getAllCategoriesForCurrentCompany());
            model.addAttribute("productUnits", productUnits);
            return "product/product-update";
        }

        productService.update(product);
        model.addAttribute("products", productService.getAllProductsForCurrentCompany());
        return "product/product-list";
    }

    @GetMapping("delete/{productId}")
    public String deleteProduct(@PathVariable("productId") Long productId, Model model){

        productService.delete(productId);
        model.addAttribute("products", productService.getAllProductsForCurrentCompany());
        return "product/product-list";
    }
}
