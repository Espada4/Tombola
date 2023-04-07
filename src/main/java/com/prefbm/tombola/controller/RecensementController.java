package com.prefbm.tombola.controller;

import com.prefbm.tombola.entity.Recensement;
import com.prefbm.tombola.repository.RecensementRepository;
import com.prefbm.tombola.service.RecensementService;
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
import java.util.List;

@Controller
@RequestMapping("/recensements")
public class RecensementController {
    final String UPLOAD_DIR = System.getProperty("user.dir").replace("\\","/")+"/upload/recensements/";

    @Autowired
    RecensementService recensementService;

    @GetMapping("/index")
    public String recensements(Model model){
        List<Recensement> recensements = recensementService.findAll();
        model.addAttribute("listRecensements", recensements);
        return "recensement-views/recensements";
    }

    @GetMapping(value = "/formCreate")
    public String formRecensement(Model model) {
        model.addAttribute("recensement", new Recensement());
        return "recensement-views/formCreate";
    }

    @PostMapping(value = "/save")
    public String save(@ModelAttribute("recensement") Recensement recensement, @RequestParam("file") MultipartFile file) {
        String fileName =file.getOriginalFilename();
        recensement.setRecensementPV(fileName);
        recensementService.save(recensement);
        String pathSting = UPLOAD_DIR+recensement.getRecensementId()+"/"+fileName;
        System.out.println("----------------------------------------------------------------------"+pathSting);
        File fileDir = new File(pathSting);
        fileDir.getParentFile().mkdirs();
        Path path = Paths.get(pathSting);
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/recensements/index";
    }

    @GetMapping("/delete")
    public String deleteRecensement(Long recensementId) {
        recensementService.delete(recensementId);
        deleteDirectory(new File(UPLOAD_DIR+recensementId));
        return "redirect:/recensements/index";
    }

    @GetMapping(value = "/formUpdate")
    public String updateRecensement(Model model, Long recensementId) {
        Recensement recensement = recensementService.findById(recensementId);
        model.addAttribute("recensement", recensement);
        //model.addAttribute()
        return "recensement-views/formUpdate";
    }


    @PostMapping("/update/{id}")
    public String updateRecensement(@PathVariable Long id,
                                @ModelAttribute("recensement") Recensement recensement,
                                Model model, @RequestParam("file") MultipartFile file) {

        // get student from database by id
        Recensement existingRecensement = recensementService.findById(id);
        String pathString = UPLOAD_DIR+id.toString()+"/"+existingRecensement.getRecensementPV();
        existingRecensement.setRecensementId(id);
        existingRecensement.setRecensementDate(recensement.getRecensementDate());

        if(!file.isEmpty()) {
            System.out.println("------------------------------Entring the new realm------------------------------");
            new File(pathString).delete();
            String fileName =file.getOriginalFilename();
            pathString = UPLOAD_DIR+id+"/"+fileName;
            File fileDir = new File(pathString);
            fileDir.getParentFile().mkdirs();
            Path path = Paths.get(pathString);

            existingRecensement.setRecensementPV(fileName);
            try {
                //file.transferTo(dest);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        recensementService.save(existingRecensement);
        return "redirect:/recensements/index";
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
