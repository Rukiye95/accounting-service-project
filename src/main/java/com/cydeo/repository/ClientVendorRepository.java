package com.cydeo.repository;

import com.cydeo.dto.CompanyDto;
import com.cydeo.entity.ClientVendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ClientVendorRepository extends JpaRepository<ClientVendor, Long> {


    List<ClientVendor> findAllByClientVendorNameIsNotNull();

    List<ClientVendor> findByCompany(CompanyDto companyDto);

}
