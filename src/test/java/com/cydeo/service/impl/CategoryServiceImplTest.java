package com.cydeo.service.impl;

import com.cydeo.converter.CategoryDTOConverter;
import com.cydeo.dto.*;
import com.cydeo.entity.*;
import com.cydeo.enums.CompanyStatus;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.CategoryRepository;
import com.cydeo.service.CategoryService;
import com.cydeo.service.CompanyService;
import com.cydeo.service.SecurityService;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

@ContextConfiguration(classes = {CategoryServiceImpl.class})
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CategoryServiceImplTest {


    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private static SecurityService securityService;

    @Mock
    private CompanyService companyService;

    @Mock
    private MapperUtil mapperUtil;


    static Company company = new Company();

    static CompanyDto companyDto = new CompanyDto();

    static UserDto userDto = new UserDto();

    static User user = new User();
    static Category category1 = new Category();
    static Category category2 = new Category();
    static Category category3 = new Category();

    @BeforeAll
    static void setUp() {
        company.setId(2L);
        company.setLastUpdateUserId(1L);
        company.setIsDeleted(false);
        company.setInsertUserId(1L);
        company.setInsertDateTime(LocalDateTime.now());
        company.setLastUpdateDateTime(LocalDateTime.now());
        company.setWebsite("www.oreo.com");
        company.setTitle("Oreo");
        company.setPhone("123456789");
        company.setCompanyStatus(CompanyStatus.ACTIVE);
        company.setAddress(new Address("7925 Jones Branch Dr, #3300", "Tysons", "Virginia", "VA", "United States", "22102-1234"));

        companyDto.setId(2L);
        companyDto.setWebsite("www.oreo.com");
        companyDto.setTitle("Oreo");
        companyDto.setPhone("123456789");
        companyDto.setCompanyStatus(CompanyStatus.ACTIVE);
        companyDto.setAddress(new AddressDto(1L, "7925 Jones Branch Dr, #3300", "Tysons", "Virginia", "VA", "United States", "22102-1234"));

        user.setId(1L);
        user.setRole(new Role("Manager"));
        user.setPhone("43242342");
        user.setCompany(company);
        user.setInsertUserId(1L);
        user.setEnabled(true);
        user.setFirstname("Josh");
        user.setIsDeleted(false);
        user.setLastname("Tyler");
        user.setPassword("test");
        user.setInsertDateTime(LocalDateTime.now());
        user.setLastUpdateDateTime(LocalDateTime.now());
        user.setLastUpdateUserId(1L);
        user.setUsername("Oreo@greentech.com");

        userDto.setCompany(companyDto);
        userDto.setRole(new RoleDto(1L, "Manager"));
        userDto.setLastname("Tyler");
        userDto.setFirstname("Josh");
        userDto.setId(1L);
        userDto.setPhone("1233455661");
        userDto.setPassword("test");
        userDto.setUsername("Oreo@greenTech.com");
        userDto.setConfirmPassword("test");
        userDto.setOnlyAdmin(false);

        category1.setId(1L);
        category1.setIsDeleted(false);
        category1.setInsertUserId(1L);
        category1.setInsertDateTime(LocalDateTime.now());
        category1.setLastUpdateDateTime(LocalDateTime.now());
        category1.setLastUpdateUserId(1L);
        category1.setDescription("computer");
        category1.setCompany(company);

        category2.setId(2L);
        category2.setIsDeleted(false);
        category2.setInsertUserId(1L);
        category2.setInsertDateTime(LocalDateTime.now());
        category2.setLastUpdateDateTime(LocalDateTime.now());
        category2.setLastUpdateUserId(1L);
        category2.setDescription("phone");
        category2.setCompany(company);

        category3.setId(3L);
        category3.setIsDeleted(false);
        category3.setInsertUserId(1L);
        category3.setInsertDateTime(LocalDateTime.now());
        category3.setLastUpdateDateTime(LocalDateTime.now());
        category3.setLastUpdateUserId(1L);
        category3.setDescription("monitor");
        category3.setCompany(company);
    }


    @Test
    public void test_categoriesList() {
        when(securityService.getLoggedInUser()).thenReturn(userDto);

        List<Category> expectedCategoryList = new ArrayList<>(List.of(category1, category2, category3));

        when(categoryRepository.findAllByCompanyId(company.getId())).thenReturn(expectedCategoryList);

        List<CategoryDto> actualCategoryList = categoryService.getAllCategoriesForCurrentCompany();

        verify(categoryRepository).findAllByCompanyId(company.getId());
        verify(mapperUtil, times(3)).convert(any(Category.class), any(CategoryDto.class));
        assertNotNull(actualCategoryList);
        assertEquals(3, actualCategoryList.size());
        assertInstanceOf(ArrayList.class, actualCategoryList);
    }

    @Test
    public void bddTest_findCategoryById() {

        CategoryDto categoryDto = new CategoryDto();

        given(categoryRepository.findById(anyLong())).willReturn(Optional.of(category1));

        categoryService.getCategoryById(category1.getId());

        then(categoryRepository).should().findById(anyLong());
        then(categoryRepository).should(never()).findById(-4L);
    }

//    @Test //this test is currently not working
//    public void test_createCategory() {
//        when(securityService.getLoggedInUser()).thenReturn(userDto);
//        CategoryDto categoryDto = new CategoryDto();
//        when(mapperUtil.convert(category1,new CategoryDto())).thenReturn(categoryDto);
//        categoryService.create(categoryDto);
//        //
//        //        vhen(categoryRepository.save(category1)).thenReturn(new Category());
//        when(companyService.getCurrentCompanyEntity()).thenReturn(company);
//
//        verify(categoryRepository).save(category1);
//
//    }
//
//    @Test
//    public void test_editCategory(){
//        CategoryDto categoryDto = new CategoryDto();
//        categoryDto.setId(1L);
//        Category existingCategory = new Category();
//        Category convertedCategory = new Category();
//
//       when(categoryRepository.findById(categoryDto.getId())).thenReturn(Optional.of(existingCategory));
//       when(mapperUtil.convert(categoryDto, new Category())).thenReturn(convertedCategory);
//
//        // Act
//        categoryService.edit(categoryDto);
//
//        // Assert
//        // Verify that the convertedCategory was saved
//        verify(categoryRepository).save(convertedCategory);
//    }
//
    @AfterEach
    void tearDown() {
        Mockito.reset(categoryRepository, securityService, companyService, mapperUtil);
    }
}