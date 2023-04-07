package com.prefbm.tombola.controller;

import com.prefbm.tombola.entity.Maison;
import com.prefbm.tombola.entity.Recensement;
import com.prefbm.tombola.repository.MaisonRepository;
import com.prefbm.tombola.service.MaisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/maisons")
public class MaisonController {

    @Autowired
    MaisonService maisonService;

    @GetMapping("/index")
    public String maisons(Model model){
        List<Maison> maisons = maisonService.findAll();
        model.addAttribute("listMaisons", maisons);
        return "maison-views/maisons";
    }
    @GetMapping(value = "/formCreate")
    public String formMaison(Model model) {
        model.addAttribute("maison", new Maison());
        return "maison-views/formCreate";
    }

    @PostMapping(value = "/save")
    public String save(@ModelAttribute("maison") Maison maison){
        maisonService.save(maison);
        return "redirect:/maisons/index";
    }
    @PostMapping(value = "/update")
    public String updateMaison(@ModelAttribute("maison") Maison maison, Long maisonID){
        Maison existing = maisonService.findById(maisonID);
        existing.setMaisonNumero(maison.getMaisonNumero());
        existing.setMaisonRue(maison.getMaisonRue());
        existing.setProprietaire(maison.getProprietaire());
        existing.setNombreFamille(maison.getNombreFamille());
        maisonService.save(existing);
        return "redirect:/maisons/index";
    }

    @GetMapping(value = "/formUpdate")
    public String updateMaison(Model model, Long maisonID) {
        Maison maison = maisonService.findById(maisonID);
        model.addAttribute("maison", maison);
        return "maison-views/formUpdate";
    }
    @GetMapping("/delete")
    public String deleteInstructor(Long maisonID) {
        maisonService.delete(maisonID);
        return "redirect:/maisons/index";
    }


}
