package com.cydeo.service;

import com.cydeo.dto.InvoiceDto;

import java.util.List;

public interface InvoiceService {

    InvoiceDto findById(Long id);

    List<InvoiceDto> findAll();
}