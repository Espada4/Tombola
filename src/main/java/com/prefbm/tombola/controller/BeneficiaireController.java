package com.prefbm.tombola.controller;

import com.prefbm.tombola.entity.Beneficiaire;
import com.prefbm.tombola.repository.BeneficiaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("beneficiaires")
public class BeneficiaireController {
    @Autowired
    BeneficiaireRepository beneficiaireService;

    @GetMapping("/index")
    String beneficiaires(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword){

        List<Beneficiaire> beneficiaires = beneficiaireService.findByCinContainsAllIgnoreCase(keyword);
        model.addAttribute("listBeneficiaires", beneficiaires);
        model.addAttribute("keyword", keyword);
        return "beneficiaire-views/beneficiaires";
    }

}
