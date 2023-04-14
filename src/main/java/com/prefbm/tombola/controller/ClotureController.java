package com.prefbm.tombola.controller;

import com.prefbm.tombola.entity.Beneficiaire;
import com.prefbm.tombola.entity.Cloture;
import com.prefbm.tombola.entity.Cloture;
import com.prefbm.tombola.entity.Cloture;
import com.prefbm.tombola.repository.BeneficiaireRepository;
import com.prefbm.tombola.service.BeneficiaireService;
import com.prefbm.tombola.service.ClotureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/clotures")
public class ClotureController {
    final String UPLOAD_DIR = System.getProperty("user.dir").replace("\\","/")+"/upload/clotures/";
    @Autowired
    ClotureService clotureService;
    @Autowired
    BeneficiaireService beneficiaireService;
    @Autowired
    private BeneficiaireRepository beneficiaireRepository;

    @GetMapping("/index")
    public String clotures(Model model){
        List<Cloture> clotures = clotureService.findAll();
        model.addAttribute("listClotures", clotures);
        return "cloture-views/clotures";
    }
    @GetMapping(value = "/formCreate")
    public String formCloture(Model model) {

        List<Beneficiaire> beneficiaires=beneficiaireService.findByNotClosed(); //replace with not closed beneficiaire beneficiaire
        model.addAttribute("beneficiaires",beneficiaires);
        model.addAttribute("cloture", new Cloture());
        return "cloture-views/formCreate";
    }


    @PostMapping(value = "/save")
    public String save(@ModelAttribute("cloture") Cloture cloture,
                       @RequestParam("file") MultipartFile file,
                       @RequestParam("indexes") List<Long> indexes) {

        String fileName =file.getOriginalFilename();
        List<Beneficiaire> beneficiaires = cloture.getBeneficiaires();

        for(Long i:indexes){
            beneficiaires.add(beneficiaireService.findById(i));
        }
        cloture.setPvCloture(fileName);
        clotureService.save(cloture);
        String pathSting = UPLOAD_DIR+cloture.getClotureId()+"/"+fileName;
        System.out.println("----------------------------------------------------------------------"+pathSting);
        File fileDir = new File(pathSting);
        fileDir.getParentFile().mkdirs();
        Path path = Paths.get(pathSting);
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //clotureService.save(cloture);
        System.out.println(cloture.getBeneficiaires());

        return "redirect:/clotures/index";
    }


    @GetMapping(value = "/formUpdate")
    public String updateCloture(Model model, Long clotureId) {
        Cloture cloture = clotureService.findById(clotureId);
        List<Beneficiaire> beneficiaires = beneficiaireService.findByNotClosed();
        List<Beneficiaire> existingBeneficiaires = cloture.getBeneficiaires();

        beneficiaires.addAll(existingBeneficiaires);
        model.addAttribute("cloture", cloture);
        model.addAttribute("beneficiaires",beneficiaires.stream().distinct().collect(Collectors.toList()));
        //model.addAttribute()
        return "cloture-views/formUpdate";
    }


    @PostMapping("/update/{id}")
    public String updateCloture(@PathVariable Long id,
                               @ModelAttribute("cloture") Cloture cloture,
                               Model model, @RequestParam("file") MultipartFile file,
                                @RequestParam("indexes") List<Long> indexes) {
        Beneficiaire beneficiaire;
        // get student from database by id
        Cloture existingCloture = clotureService.findById(id);
        List<Beneficiaire> beneficiaires =existingCloture.getBeneficiaires();

        //Remove those marked by error and corrected after update
        List<Long> tobedeleted = beneficiaires.stream().map(b ->b.getBeneficiaireId()).collect(Collectors.toList());

        if(tobedeleted.removeAll(indexes)){
            for(Long l:tobedeleted) {
                beneficiaire = beneficiaireService.findById(l);
                beneficiaires.remove(beneficiaire);
                beneficiaire.setCloture(null);
                beneficiaireService.save(beneficiaire);
            }
        };


        for(Long i:indexes){ beneficiaires.add(beneficiaireService.findById(i)); }

        String pathString = UPLOAD_DIR+id.toString()+"/"+existingCloture.getPvCloture();
        existingCloture.setClotureId(id);
        existingCloture.setClotureDate(cloture.getClotureDate());

        if(!file.isEmpty()) {
            System.out.println("------------------------------Entring the new realm------------------------------");
            new File(pathString).delete();
            String fileName =file.getOriginalFilename();
            pathString = UPLOAD_DIR+id+"/"+fileName;
            File fileDir = new File(pathString);
            fileDir.getParentFile().mkdirs();
            Path path = Paths.get(pathString);

            existingCloture.setPvCloture(fileName);
            try {
                //file.transferTo(dest);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        clotureService.save(existingCloture);
        return "redirect:/clotures/index";
    }



}
