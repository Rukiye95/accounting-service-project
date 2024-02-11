package com.cydeo.service;

import com.cydeo.dto.ClientVendorDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientVendorService {

    List<ClientVendorDto> findAllVendorNames();

    List<ClientVendorDto> listClientVendorDetails();

    ClientVendorDto findById(Long id);

    void update(ClientVendorDto clientVendorDto);

    void deleteById(Long id);

    void save(ClientVendorDto clientVendorDto);
}
