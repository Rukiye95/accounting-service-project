package com.cydeo.repository;

import com.cydeo.entity.Category;
import com.cydeo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.category.company.id = ?1 and p.isDeleted = false " +
            "order by p.category.description asc , p.name asc ")
    List<Product> getAllNotDeletedProductsForCompany(Long companyId);

    List<Product> findByCategory(Category category);
}