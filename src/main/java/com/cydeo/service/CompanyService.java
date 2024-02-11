package com.cydeo.service;

import com.cydeo.dto.CompanyDto;
import com.cydeo.entity.Company;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface CompanyService {

    List<CompanyDto> getAllCompany();

    CompanyDto findById(Long id);

    CompanyDto create(CompanyDto companyDto);

    void update(CompanyDto dto);

    void delete(Long companyId);

    Company getCurrentCompanyEntity();
}
