package com.cydeo.controller;

import com.cydeo.dto.InvoiceDto;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.service.ClientVendorService;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/purchaseInvoices")
public class PurchasesInvoiceController {

    InvoiceService invoiceService;
    InvoiceProductService invoiceProductService;
    ClientVendorService clientVendorService;

    public PurchasesInvoiceController(InvoiceService invoiceService, InvoiceProductService invoiceProductService, ClientVendorService clientVendorService) {
        this.invoiceService = invoiceService;
        this.invoiceProductService = invoiceProductService;
        this.clientVendorService = clientVendorService;
    }

    @GetMapping("/list")
    public String listPurchaseInvoices(InvoiceProduct code, Model model) {

        model.addAttribute("invoices", invoiceService.findAll());

        return "invoice/purchase-invoice-list";
    }

    @GetMapping("/update/{id}")
    public String updatePurchaseInvoices(@PathVariable("id") Long id, Model model) {

        model.addAttribute("invoice", invoiceService.findAll());
        return "invoice/purchase-invoice-update";
    }

    @GetMapping("/create")
    public String createPurchaseInvoices(Model model) {
        model.addAttribute("newPurchaseInvoice", new InvoiceDto());
        model.addAttribute("vendors", clientVendorService.findAllVendorNames());

        return "invoice/purchase-invoice-create";
    }

}
