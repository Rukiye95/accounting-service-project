package com.cydeo.repository;

import com.cydeo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    //List<Category> findAll();
    void deleteCategoryById(Long id);
    List<Category> findAllByCompanyId(Long companyId);


}
