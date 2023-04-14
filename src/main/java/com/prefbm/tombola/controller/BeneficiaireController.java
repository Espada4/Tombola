package com.prefbm.tombola.controller;

import com.prefbm.tombola.entity.Beneficiaire;
import com.prefbm.tombola.entity.Maison;
import com.prefbm.tombola.repository.BeneficiaireRepository;
import com.prefbm.tombola.service.BeneficiaireService;
import com.prefbm.tombola.service.MaisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("beneficiaires")
public class BeneficiaireController {
    @Autowired
    BeneficiaireService beneficiaireService;
    @Autowired
    MaisonService maisonService;

    @GetMapping("/index")
    String beneficiaires(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword){

        List<Beneficiaire> beneficiaires = beneficiaireService.findByCin(keyword);
        model.addAttribute("listBeneficiaires", beneficiaires);
        model.addAttribute("keyword", keyword);
        return "beneficiaire-views/beneficiaires";
    }

    @GetMapping(value = "/formCreate")
    public String formBeneficiaire(Model model, @RequestParam Long maisonID) {
        System.out.println("im here----------------------------------------------------------------------------------"+maisonID);
        model.addAttribute("beneficiaire", new Beneficiaire());
        model.addAttribute("maisonID",maisonID);
        return "beneficiaire-views/formCreate";
    }

    @GetMapping(value = "/formUpdate")
    public String updateMaison(Model model, Long beneficiaireId) {
        Beneficiaire beneficiaire = beneficiaireService.findById(beneficiaireId);
        model.addAttribute("beneficiaire", beneficiaire);
        return "beneficiaire-views/formUpdate";
    }

    @PostMapping(value = "/save")
    public String save(@ModelAttribute("beneficiaire") Beneficiaire beneficiaire, Long maisonID){
        Maison maison = maisonService.findById(maisonID);
        beneficiaire.setMaison(maison);
        beneficiaireService.save(beneficiaire);
        return "redirect:/maisons/maisonDetails?maisonID="+beneficiaire.getMaison().getMaisonID();
    }

    @GetMapping("/delete")
    public String deleteBeneficiaire(Long beneficiaireId) {
        Long maisonId = beneficiaireService.findById(beneficiaireId).getMaison().getMaisonID();
        beneficiaireService.delete(beneficiaireId);
        return "redirect:/maisons/maisonDetails?maisonID="+maisonId;
    }

    @PostMapping(value = "/update")
    public String updateBeneficiaire(@ModelAttribute("beneficiaire") Beneficiaire beneficiaire, Long beneficiaireId) {
        Beneficiaire existing= beneficiaireService.findById(beneficiaireId);
        existing.setCin(beneficiaire.getCin());
        existing.setNom(beneficiaire.getNom());
        existing.setPrenom(beneficiaire.getPrenom());
        existing.setEtage(beneficiaire.getEtage());
        existing.setNombreEnfant(beneficiaire.getNombreEnfant());
        existing.setSituationFamiliale(existing.getSituationFamiliale());
        existing.setTypeResidence(existing.getTypeResidence());
        existing.setSituationResident(existing.getSituationResident());
        existing.setObservations(existing.getObservations());
        beneficiaireService.save(existing);

        return "redirect:/maisons/maisonDetails?maisonID="+existing.getMaison().getMaisonID();

    }



}
