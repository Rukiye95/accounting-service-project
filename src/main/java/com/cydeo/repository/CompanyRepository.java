package com.cydeo.repository;

import com.cydeo.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT u.company FROM User u WHERE u.id = :userId")
    Optional<Company> findCompanyByUserId(@Param("userId") Long userId);


    boolean existsByTitle(String companyName);
}
