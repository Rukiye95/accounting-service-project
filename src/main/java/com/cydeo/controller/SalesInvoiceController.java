package com.cydeo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/salesInvoices")
public class SalesInvoiceController {

    @GetMapping("/list")
    public String listSalesInvoices(Model model) {


        return "invoice/sales-invoice-list";
    }

    @GetMapping("/update")
    public String updateSalesInvoices(Model model) {


        return "invoice/sales-invoice-update";
    }

    @GetMapping("/create")
    public String createSalesInvoices(Model model) {


        return "invoice/sales-invoice-create";
    }

}