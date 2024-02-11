package com.cydeo.repository;

import com.cydeo.entity.Company;
import com.cydeo.entity.Role;
import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    List<User> findByRoleOrderByCompanyTitleAscRoleDescriptionAsc(Role role);
    List<User> findByCompanyOrderByCompanyTitleAscRoleDescriptionAsc(Company company);

    boolean existsByUsername(String username);
}
