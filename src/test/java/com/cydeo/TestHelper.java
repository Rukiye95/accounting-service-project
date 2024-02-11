package com.cydeo;

import com.cydeo.dto.*;
import com.cydeo.entity.Category;
import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.Company;
import com.cydeo.entity.User;
import com.cydeo.enums.*;
import com.cydeo.mapper.MapperUtil;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TestHelper {


    static MapperUtil mapperUtil = new MapperUtil(new ModelMapper());

    public static UserDto getUser(String role){
        return UserDto.builder()
                .id(1L)
                .firstname("John")
                .lastname("Mike")
                .phone("+1 (111) 111-1111")
                .password("Abc1")
                .confirmPassword("Abc1")
                .role(new RoleDto(1L,role))
                .isOnlyAdmin(false)
                .company(getCompany(CompanyStatus.ACTIVE))
                .build();
    }

    public static User getUserEntity(String role){
        return mapperUtil.convert(getUser(role), new User());
    }

    public static CompanyDto getCompany(CompanyStatus status){
        return CompanyDto.builder()
                .title("Test_Company")
                .website("www.test.com")
                .id(1L)
                .phone("+1 (111) 111-1111")
                .companyStatus(status)
                .address(new AddressDto())
                .build();
    }

    public static Company getCompanyEntity(CompanyStatus status){
        return mapperUtil.convert(getCompany(status), new Company());

    }

    public static CategoryDto getCategory(){
        return CategoryDto.builder()
                .companyDto(getCompany(CompanyStatus.ACTIVE))
                .description("Test_Category")
                .build();
    }

    public static Category getCategoryEntity(){
        return mapperUtil.convert(getCategory(), new Category());
    }

    public static ClientVendorDto getClientVendor(ClientVendorType type){
        return ClientVendorDto.builder()
                .clientVendorType(type)
                .clientVendorName("Test_ClientVendor")
                .address(new AddressDto())
                .website("https://www.test.com")
                .phone("+1 (111) 111-1111")
                .build();
    }

    public static ClientVendor getClientVendorEntity(ClientVendorType type){
        return mapperUtil.convert(getClientVendor(type), new ClientVendor());
    }

    public static ProductDto getProduct(){
        return ProductDto.builder()
                .category(getCategory())
                .productUnit(ProductUnit.PCS)
                .name("Test_Product")
                .quantityInStock(10)
                .lowLimitAlert(5)
                .build();
    }

    public static InvoiceProductDto getInvoiceProduct(){
        return InvoiceProductDto.builder()
                .product(getProduct())
                .price(BigDecimal.TEN)
                .tax(10)
                .quantity(10)
                .invoice(new InvoiceDto())
                .build();
    }

    public static InvoiceDto getInvoice(InvoiceStatus status, InvoiceType type){
        return InvoiceDto.builder()
                .invoiceNo("T-001")
                .client(getClientVendor(ClientVendorType.CLIENT))
                .invoiceStatus(status)
                .invoiceType(type)
                .date(LocalDate.of(2022,01,01))
                .company(getCompany(CompanyStatus.ACTIVE))
                .price(BigDecimal.valueOf(1000))
                .tax(BigDecimal.TEN)
                .total(BigDecimal.TEN.multiply(BigDecimal.valueOf(1000)))
                .build();
    }
}
