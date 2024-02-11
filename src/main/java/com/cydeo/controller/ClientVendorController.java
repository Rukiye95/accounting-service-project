package com.cydeo.controller;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.enums.ClientVendorType;
import com.cydeo.service.ClientVendorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
@RequestMapping("/clientVendors")
public class ClientVendorController {

    private final ClientVendorService clientVendorService;


    public ClientVendorController(ClientVendorService clientVendorService) {
        this.clientVendorService = clientVendorService;
    }

    @GetMapping("/list")
    public String listOfClientVendors(Model model){

        model.addAttribute("clientVendors", clientVendorService.listClientVendorDetails());

        return "clientVendor/clientVendor-list";
    }

    @GetMapping("/update/{id}")
    public String editClientVendor(@PathVariable("id")Long id, Model model)
    {
        model.addAttribute("clientVendor", clientVendorService.findById(id));
        model.addAttribute("clientVendorType", Arrays.asList(ClientVendorType.values()));

        return "clientVendor/clientVendor-update";
    }

    @PostMapping("/update/{id}")
    public String updateClientVendor(@Valid @ModelAttribute("clientVendor")ClientVendorDto clientVendorDto, BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors())
        {
            model.addAttribute("clientVendor", clientVendorService.findById(clientVendorDto.getId()));
            model.addAttribute("clientVendorType", Arrays.asList(ClientVendorType.values()));

        }
        clientVendorService.update(clientVendorDto);
        return "redirect:clientVendor/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteClientVendor(@PathVariable Long id, Model model) {
        clientVendorService.deleteById(id);
        return "redirect:clientVendor/list";
    }

    @GetMapping("/create")
    public String createClientVendor(Model model){
        model.addAttribute("newClientVendor", new ClientVendorDto());
        model.addAttribute("clientVendorType", Arrays.asList(ClientVendorType.values()));
        return "clientVendor/clientVendor-create";
    }

    @PostMapping("/create")
    public String insertClientVendor(@Valid @ModelAttribute("newClientVendor") ClientVendorDto clientVendorDto, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors()) {
            model.addAttribute("newClientVendor", new ClientVendorDto());
            model.addAttribute("clientVendorType", Arrays.asList(ClientVendorType.values()));

            return "clientVendor/clientVendor-create";

        }
        clientVendorService.save(clientVendorDto);
        return "redirect:clientVendor/list";
    }

}
