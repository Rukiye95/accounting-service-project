package com.cydeo.controller;

import com.cydeo.dto.CategoryDto;
import com.cydeo.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService=categoryService;
    }

    @GetMapping("/list")
    public String listAllCategories(Model model){
        model.addAttribute("categories",categoryService.getAllCategoriesForCurrentCompany());
        return"category/category-list";
    }

    @GetMapping("/create")
    public String getCreateCategoryForm(Model model){
        model.addAttribute("newCategory",new CategoryDto());
        return"category/category-create";
    }

    @PostMapping("/create")
    public String createCategory(@ModelAttribute CategoryDto categoryDto, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()) {
            model.addAttribute("newCategory",new CategoryDto());
            return "redirect:/categories/create";
        }

        categoryService.create(categoryDto);
        return"redirect:/categories/list";
    }
    @GetMapping("/update/{id}")
    public String editCategoryById(@PathVariable Long id, Model model){
        model.addAttribute("category",categoryService.getCategoryById(id));
        return "/category/category-update";
    }
    @PostMapping("/update/{id}")
    public String updateCategory(@ModelAttribute("category") CategoryDto categoryDto,BindingResult bindingResult,Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("category",new CategoryDto());
            return "category/category-update";
        }
        categoryService.edit(categoryDto);
        return "redirect:/categories/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategoryById(@PathVariable Long id){
        categoryService.delete(id);
        return "redirect:/categories/list";
    }

}
