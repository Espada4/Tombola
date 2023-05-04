package com.prefbm.tombola.controller;

import com.prefbm.tombola.entity.Beneficiaire;
import com.prefbm.tombola.entity.Participation;
import com.prefbm.tombola.entity.Tirage;
import com.prefbm.tombola.entity.Tirage;
import com.prefbm.tombola.repository.ParticipationRepository;
import com.prefbm.tombola.repository.TirageRepository;
import com.prefbm.tombola.service.AppartementService;
import com.prefbm.tombola.service.BeneficiaireService;
import com.prefbm.tombola.service.TirageService;
import jakarta.persistence.SecondaryTable;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tirages")
public class TirageController {
    final String UPLOAD_DIR = System.getProperty("user.dir").replace("\\","/")+"/upload/tirages/";
    @Autowired
    TirageService tirageService;
    @Autowired
    BeneficiaireService beneficiaireService;
    @Autowired
    ParticipationRepository participationService;
    @Autowired
    AppartementService appartementService;

    @GetMapping("/index")
    public String tirages(Model model){
        List<Tirage> tirages = tirageService.findAll();
        model.addAttribute("listTirages", tirages);
        return "tirage-views/tirages";
    }

    @GetMapping(value = "/tirageDetails")
    public String formDetails(Model model,Long tirageId) {
        Tirage tirage = tirageService.findById(tirageId);
        model.addAttribute("tirage", tirage);
        /*List<Beneficiaire> beneficiaires = new ArrayList<>();
        for(Participation participation:tirage.getParticipations()){
            for(Beneficiaire b:participation.getBeneficiaires()) beneficiaires.add(b);
        }
        model.addAttribute("beneficiaires", beneficiaires);*/
        return "tirage-views/tirageDetails";
    }

    @PostMapping(value = "/saveDetails")
    public String saveDetails(@ModelAttribute("tirage") Tirage tirage) {
        Participation persistedParticipation;
        for(Participation p:tirage.getParticipations()){
            persistedParticipation = participationService.findById(p.getParticipationId()).get();
            persistedParticipation.setResultat(p.isResultat());
            participationService.save(persistedParticipation);
        }
        return "redirect:/tirages/index";
    }





    @GetMapping(value = "/formCreate")
    public String formTirage(Model model) {
       /* List<Beneficiaire> beneficiaires=beneficiaireService.findNextParticipants();
        //Map<Beneficiaire,Boolean> tacos = beneficiaires.stream().collect(Collectors.toMap(b->b,b->true));
        model.addAttribute("beneficiaires",beneficiaires);*/
        model.addAttribute("tirage", new Tirage());
        return "tirage-views/formCreate";
    }

    @PostMapping(value = "/save")
    public String save(@ModelAttribute("tirage") Tirage tirage, @RequestParam("file") MultipartFile file) {
        String fileName =file.getOriginalFilename();
        tirage.setPvTirage(fileName);
        tirageService.save(tirage);
        String pathSting = UPLOAD_DIR+tirage.getTirageId()+"/"+fileName;
        System.out.println("----------------------------------------------------------------------"+pathSting);
        File fileDir = new File(pathSting);
        fileDir.getParentFile().mkdirs();
        Path path = Paths.get(pathSting);
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/tirages/tirageDetails?tirageId="+tirage.getTirageId();
    }

    @GetMapping("/delete")
    public String deleteTirage(Long tirageId) {
        tirageService.delete(tirageId);
        deleteDirectory(new File(UPLOAD_DIR+tirageId));
        return "redirect:/tirages/index";
    }

    @GetMapping(value = "/formUpdate")
    public String updateTirage(Model model, Long tirageId) {
        Tirage tirage = tirageService.findById(tirageId);
        model.addAttribute("tirage", tirage);
        //model.addAttribute()
        return "tirage-views/formUpdate";
    }


    @PostMapping("/update/{id}")
    public String updateTirage(@PathVariable Long id,
                                    @ModelAttribute("tirage") Tirage tirage,
                                    Model model, @RequestParam("file") MultipartFile file) {

        // get student from database by id
        Tirage existingTirage = tirageService.findById(id);
        String pathString = UPLOAD_DIR+id.toString()+"/"+existingTirage.getPvTirage();
        existingTirage.setTirageId(id);
        existingTirage.setDateTirage(tirage.getDateTirage());
        existingTirage.setNombreAppartement(tirage.getNombreAppartement());

        if(!file.isEmpty()) {
            System.out.println("------------------------------Entring the new realm------------------------------");
            new File(pathString).delete();
            String fileName =file.getOriginalFilename();
            pathString = UPLOAD_DIR+id+"/"+fileName;
            File fileDir = new File(pathString);
            fileDir.getParentFile().mkdirs();
            Path path = Paths.get(pathString);

            existingTirage.setPvTirage(fileName);
            try {
                //file.transferTo(dest);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        tirageService.update(existingTirage);
        return "redirect:/tirages/tirageDetails?tirageId="+id;
    }



    @GetMapping("/download/{id}/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable("id") Long id, @PathVariable("fileName") String fileName) {
        System.out.println(id);
        String pathString = UPLOAD_DIR+id.toString()+"/"+fileName;
        File file = new File(pathString);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + fileName);
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        Path path = Paths.get(pathString);
        ByteArrayResource resource = null;
        String mimeType = URLConnection.guessContentTypeFromName(fileName);
        System.out.println(mimeType);
        if (mimeType == null) {
            //unknown mimetype so set the mimetype to application/octet-stream
            mimeType = "application/octet-stream";
        }
        try {
            resource = new ByteArrayResource(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType(mimeType))
                .body(resource);
    }



    public static void deleteDirectory(File directory) {

        // if the file is directory or not
        if(directory.isDirectory()) {
            File[] files = directory.listFiles();

            // if the directory contains any file
            if(files != null) {
                for(File file : files) {

                    // recursive call if the subdirectory is non-empty
                    deleteDirectory(file);
                }
            }
        }

        if(directory.delete()) {
            System.out.println(directory + " is deleted");
        }
        else {
            System.out.println("Directory not deleted");
        }
    }





}
