package com.prefbm.tombola.controller;

import com.prefbm.tombola.entity.Appartement;
import com.prefbm.tombola.entity.Beneficiaire;
import com.prefbm.tombola.entity.Maison;
import com.prefbm.tombola.entity.Recensement;
import com.prefbm.tombola.service.AppartementService;
import com.prefbm.tombola.service.RecensementService;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("appartements")

public class AppartementController {
    @Autowired
    AppartementService appartementService;
    @Autowired
    RecensementService recensementService;


    @GetMapping("/index")
    String beneficiaires(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword){

        List<Appartement> appartements = appartementService.findAll();
        model.addAttribute("listAppartements", appartements);
        return "appartement-views/appartements";
    }

    @GetMapping(value = "/formCreate")
    public String formBeneficiaire(Model model, @RequestParam(name = "recensementId", defaultValue = "1")Long recensementId) {
        model.addAttribute("appartement", new Appartement());
        model.addAttribute("recensementId",recensementId);
        return "appartement-views/formCreate";
    }

    @PostMapping(value = "/save")
    public String save(@ModelAttribute("appartement") Appartement appartement, Long recensementId){
        Recensement recensement = recensementService.findById(recensementId);
        //appartementService.setRecensement(recensement);
        appartementService.save(appartement);
        return "redirect:/appartements/index";
    }

}
