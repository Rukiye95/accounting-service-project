package com.cydeo.service.impl;

import com.cydeo.dto.CategoryDto;
import com.cydeo.dto.CompanyDto;
import com.cydeo.entity.Category;
import com.cydeo.entity.Company;
import com.cydeo.entity.Product;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.CategoryRepository;
import com.cydeo.repository.ProductRepository;
import com.cydeo.service.CategoryService;
import com.cydeo.service.CompanyService;
import com.cydeo.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;
    private final MapperUtil mapperUtil;

    private final SecurityService securityService;
    private final CompanyService companyService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, MapperUtil mapperUtil, SecurityService securityService, CompanyService companyService,
                               ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
        this.companyService = companyService;
        this.productRepository = productRepository;
    }

    @Override
    public void create(CategoryDto dto) {
        Category newCategory = mapperUtil.convert(dto, new Category());
        newCategory.setCompany(companyService.getCurrentCompanyEntity());
        categoryRepository.save(newCategory);
        log.info("Category created for categoryId : " + newCategory.getId());
    }

    @Override
    public void edit(CategoryDto dto) {
        Optional<Category> categoryOptional = categoryRepository.findById(dto.getId());

        if (categoryOptional.isPresent()) {
            Category category= categoryOptional.get();
            Category convertedCategory = mapperUtil.convert(dto, new Category());
            convertedCategory.setId(category.getId());
            convertedCategory.setCompany(companyService.getCurrentCompanyEntity());
            categoryRepository.save(convertedCategory);
        }
        else {
            log.info("this category does not exist");
        }
    }

    @Override
    public void delete(Long id) {
        Category categoryFound = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category Not Found"));

        List<Product> productsInCategory = productRepository.findByCategory(categoryFound);

        if(productsInCategory.stream().anyMatch(product -> !product.getIsDeleted())&&productsInCategory.stream().anyMatch(product -> product.getQuantityInStock()>0)){
            throw new RuntimeException("Cannot delete this category, please make sure no products under this category");
        }
        else {
           categoryFound.setIsDeleted(true);
           categoryRepository.save(categoryFound);
           log.info(categoryFound.getDescription()+" is deleted");
        }
    }


    @Override
    public CategoryDto getCategoryById(Long id) {

        return mapperUtil.convert(categoryRepository.findById(id), new CategoryDto());

    }

    @Override
    public List<CategoryDto> getAllCategoriesForCurrentCompany() {

        Long companyId = securityService.getLoggedInUser().getCompany().getId();
        log.info("current company id :"+companyId);
        List<Category> listByCompany = categoryRepository.findAllByCompanyId(companyId);
        log.info("category list for current company: "+listByCompany.stream().map(Category::getDescription).collect(Collectors.toList()));
        return listByCompany.stream().map(category -> mapperUtil.convert(category, new CategoryDto())).collect(Collectors.toList());

    }
}
