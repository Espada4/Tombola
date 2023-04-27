package com.prefbm.tombola.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MyController {

    @GetMapping("/")
    public String home(){
        return "redirect:/beneficiaires/index";
//        return "template";
    }
}
