package com.prefbm.tombola.controller;

import com.prefbm.tombola.entity.Maison;
import com.prefbm.tombola.entity.Recensement;
import com.prefbm.tombola.repository.MaisonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/maisons")
public class MaisonController {

    @Autowired
    MaisonRepository maisonService;

    @GetMapping("/index")
    public String maisons(Model model){
        List<Maison> maisons = maisonService.findAll();
        model.addAttribute("listMaisons", maisons);
        return "maison-views/maisons";
    }
}
