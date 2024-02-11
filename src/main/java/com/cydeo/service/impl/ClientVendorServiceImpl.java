package com.cydeo.service.impl;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.dto.CompanyDto;
import com.cydeo.entity.ClientVendor;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.service.ClientVendorService;
import com.cydeo.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ClientVendorServiceImpl implements ClientVendorService {

    private final ClientVendorRepository clientVendorRepository;
    private final UserService userService;
    MapperUtil mapperUtil;

    public ClientVendorServiceImpl(ClientVendorRepository clientVendorRepository, UserService userService, MapperUtil mapperUtil) {
        this.clientVendorRepository = clientVendorRepository;
        this.userService = userService;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<ClientVendorDto> findAllVendorNames() {
        List<ClientVendor> clientVendors = clientVendorRepository.findAllByClientVendorNameIsNotNull();
        return clientVendors.stream()
                .map(clientVendor -> mapperUtil.convert(clientVendor, new ClientVendorDto()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ClientVendorDto> listClientVendorDetails() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        CompanyDto company = userService.findByUsername(username).getCompany();

        return clientVendorRepository.findByCompany(company)
                .stream().map(a->mapperUtil.convert(a, new ClientVendorDto()))
                .collect(Collectors.toList());
    }

    @Override
    public ClientVendorDto findById(Long id) {
        ClientVendor clientVendor = clientVendorRepository.findById(id).orElseThrow(() -> new NoSuchElementException("ClientVendor not found"));
        return mapperUtil.convert(clientVendor, new ClientVendorDto());
    }

    @Override
    public void update(ClientVendorDto clientVendorDto) {
        ClientVendor clientVendor = clientVendorRepository.findById(clientVendorDto.getId()).orElseThrow(() -> new NoSuchElementException("ClientVendor not found"));;
        ClientVendor convertedClientVendor = mapperUtil.convert(clientVendorDto, new ClientVendor());
        convertedClientVendor.setId(clientVendorDto.getId());
        clientVendorRepository.save(convertedClientVendor);
    }

    @Override
    public void deleteById(Long id) {
        ClientVendor clientVendor = clientVendorRepository.findById(id).orElseThrow(() -> new NoSuchElementException("ClientVendor not found"));
        clientVendor.setIsDeleted(true);
        clientVendorRepository.save(clientVendor);
    }

    @Override
    public void save(ClientVendorDto clientVendorDto) {
        ClientVendor clientVendor = mapperUtil.convert(clientVendorDto, new ClientVendor());
        clientVendorRepository.save(clientVendor);
    }


}