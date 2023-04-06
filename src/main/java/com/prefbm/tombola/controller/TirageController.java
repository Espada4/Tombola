package com.prefbm.tombola.controller;

import com.prefbm.tombola.entity.Recensement;
import com.prefbm.tombola.entity.Tirage;
import com.prefbm.tombola.repository.TirageRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/tirages")
public class TirageController {

    @Autowired
    TirageRepository tirageService;

    @GetMapping("/index")
    public String tirages(Model model){
        List<Tirage> tirages = tirageService.findAll();
        model.addAttribute("listTirages", tirages);
        return "tirage-views/tirages";
    }



}
