package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/footer")
public class FooterController {

    @GetMapping("/UsloviPrava")
    public String getTermsPage(){
        return "UsloviZaKupuvanje";
    }
    @GetMapping("/UsloviIsporaka")
    public String getConditionsDelivery(){
        return "UsloviIsporaka";
    }
    @GetMapping("/VrakjanjeProizvod")
    public String geReturnItem(){
        return "VrakjanjeProizvod";
    }

    @GetMapping("/UsloviKoristenje")
    public String getTermsForUser(){
        return "UsloviKoristenje";
    }
    @GetMapping("/Politika")
    public String getPolicyContent(){
        return "Politika";
    }
}
