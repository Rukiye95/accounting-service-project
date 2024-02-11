package com.cydeo.service.impl;

import com.cydeo.dto.CompanyDto;
import com.cydeo.entity.Company;
import com.cydeo.enums.CompanyStatus;
import com.cydeo.exceptions.CompanyAlreadyExistsException;
import com.cydeo.exceptions.UserAlreadyExistsException;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.CompanyRepository;
import com.cydeo.service.CompanyService;
import com.cydeo.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;

    public CompanyServiceImpl(CompanyRepository companyRepository, MapperUtil mapperUtil, SecurityService securityService) {
        this.companyRepository = companyRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
    }

    @Override
    public CompanyDto findById(Long id) {
        Optional<Company> companyId = companyRepository.findById(id);
        return mapperUtil.convert(companyId.get(), new CompanyDto());
    }

    @Override
    public List<CompanyDto> getAllCompany() {
        return companyRepository.findAll().stream().map(company -> mapperUtil.convert(company, new CompanyDto())).collect(Collectors.toList());
    }

    @Override
    public CompanyDto create(CompanyDto companyDto) {
       companyDto.setCompanyStatus(CompanyStatus.PASSIVE);

        if (companyRepository.existsByTitle(companyDto.getTitle())) {
            throw new CompanyAlreadyExistsException();
        }

        Company newCompany = mapperUtil.convert(companyDto, new Company());
        Company storedCompany = companyRepository.save(newCompany);

        log.info("Company is created: {} ", storedCompany);

         CompanyDto storedCompanyDto= mapperUtil.convert(storedCompany,new CompanyDto());
         return storedCompanyDto;


    }

    @Override
    public void update(CompanyDto dto) {
        Optional<Company> company= companyRepository.findById(dto.getId());
        Company convertedCompany = mapperUtil.convert(dto, new Company());
        if(company.isPresent()){
            convertedCompany.setId(company.get().getId());
            companyRepository.save(convertedCompany);
        }
    }

    @Override
    public void delete(Long companyId) {
        //Company can not be deleted only deactivate function available
        Company company = companyRepository.findById(companyId).get();
        company.setCompanyStatus(CompanyStatus.PASSIVE);
        companyRepository.save(company);
    }

    @Override
    public Company getCurrentCompanyEntity() {

        Long companyId = securityService.getLoggedInUser().getCompany().getId();
        CompanyDto currentCompanyDto = findById(companyId);
        Company currentCompanyEntity = mapperUtil.convert(currentCompanyDto,new Company());
        return currentCompanyEntity;
    }

}
