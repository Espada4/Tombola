package com.prefbm.tombola.runner;

import com.prefbm.tombola.entity.*;
import com.prefbm.tombola.repository.*;
import com.prefbm.tombola.service.ClotureService;
import com.prefbm.tombola.service.MaisonService;
import com.prefbm.tombola.service.TirageService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Component
public class ExcelRunner implements CommandLineRunner {

    @Autowired
    RecensementRepository recensementRepository;
    @Autowired
    AppartementRepository appartementRepository;

    @Autowired
    TirageRepository tirageRepository;

    @Autowired
    MaisonService maisonService;

    @Autowired
    BeneficiaireRepository beneficiaireRepository;
    @Autowired
    ClotureService clotureService;

    @Autowired
    TirageService tirageService;

    @Autowired
    ParticipationRepository participationRepository;

    @Override
    public void run(String... args) throws Exception {
        /*this.saveTirage();
        this.saveMaisons();
        this.saveBeneficiaires();
        this.saveAppartements();*/
        //this.saveParticipations();

    }


    public void saveMaisons() throws IOException {
        Calendar c = Calendar.getInstance();
        c.set(2022, 3, 16);
        Date date= c.getTime();
        Recensement recensement = new Recensement("درب خليفة", date, 45, "pvRecencement1");
        recensementRepository.save(recensement);
//        FileInputStream file = new FileInputStream(new File("E:\\Users\\ghass\\OneDrive\\Bureau\\GhassaneApp\\dueh.xlsx"));
        FileInputStream file = new FileInputStream(new File(System.getProperty("user.dir")+"/dueh.xlsx"));
        Workbook workbook = new XSSFWorkbook(file);
        Maison maison = new Maison();
        maison.setRecensement(recensement);//        FileInputStream file = new FileInputStream(new File("E:\\Users\\ghass\\OneDrive\\Bureau\\GhassaneApp\\dueh.xlsx"));



        try {

            Sheet sheet = workbook.getSheetAt(1);

            int i = 1;
            Row row = sheet.getRow(i);
            while (row != null) {
                maison.setMaisonID((long) row.getCell(0).getNumericCellValue());
                maison.setMaisonRue((int) row.getCell(1).getNumericCellValue());
                Cell cell = row.getCell(2);
                if(cell.getCellType()==CellType.NUMERIC) cell.setCellValue(""+(int)cell.getNumericCellValue());
                maison.setMaisonNumero(row.getCell(2).getStringCellValue());
                if(row.getCell(3)!=null)
                    maison.setProprietaire(row.getCell(3).getStringCellValue());
                maison.setNombreFamille((int) row.getCell(4).getNumericCellValue());
                if(row.getCell(5)!=null)
                    maison.setObservationMaison(row.getCell(5).getStringCellValue());
                maisonService.save(maison);
                //System.out.println(maison);


                row = sheet.getRow(i);
                i++;
                System.out.println(i);

            }


        }
        finally {

        }

    }


    public void saveAppartements() throws IOException {
        FileInputStream file = new FileInputStream(new File(System.getProperty("user.dir")+"/dueh.xlsx"));
        Workbook workbook = new XSSFWorkbook(file);
        try {

            Sheet sheet = workbook.getSheetAt(3);
            int i = 1;
            Row row = sheet.getRow(i);
            while (row != null) {
                Appartement appartement = new Appartement();
                appartement.setAppartementId((long) row.getCell(0).getNumericCellValue());
                appartement.setAdresse(row.getCell(1).getStringCellValue());
                appartement.setNumeroGroupe(row.getCell(2).getStringCellValue());
                appartement.setNumeroImmeuble(row.getCell(3).getStringCellValue());
                appartement.setEtage(row.getCell(4).getStringCellValue());
                appartement.setNumeroAppartement((int) row.getCell(5).getNumericCellValue());
                if(row.getCell(6)!=null)
                    appartement.setTitreFoncier(row.getCell(6).getStringCellValue());
                appartementRepository.save(appartement);
                row = sheet.getRow(i);
                i++;
                System.out.println(i);
            }
        }
        finally {

        }

    }


    public void saveParticipations() throws IOException {
        FileInputStream file = new FileInputStream(new File(System.getProperty("user.dir")+"/dueh.xlsx"));
        Workbook workbook = new XSSFWorkbook(file);
        List<Beneficiaire> beneficiaires = new ArrayList<>();
        try {

            Sheet sheet = workbook.getSheetAt(2);
            int i = 1;
            Row row = sheet.getRow(i);
            while (row != null) {
                Participation participation = new Participation();
                participation.setResultat(true);

                if(row.getCell(4)!=null)
                    participation.setTirage(tirageService.findById((long)row.getCell(4).getNumericCellValue()));
                Beneficiaire beneficiaire = null;
                if(row.getCell(1)!=null){
                    System.out.println("im here :"+ row.getCell(1).getNumericCellValue());
                    if((long)row.getCell(1).getNumericCellValue()!=0){
                        beneficiaires.clear();
                        beneficiaire = beneficiaireRepository.findById((long)row.getCell(1).getNumericCellValue()).get();
                        beneficiaire.setParticipation(participation);
                        beneficiaires.add(beneficiaire);
                        participation.setBeneficiaires(beneficiaires);

                    }

                }
                if(row.getCell(3)!=null)
                    participation.setAppartement(appartementRepository.findById((long)row.getCell(3).getNumericCellValue()).get());
                if(row.getCell(2)!=null)
                    participation.setNum_dossier(""+(long)row.getCell(2).getNumericCellValue());


                participationRepository.save(participation);
                if(beneficiaire!=null)beneficiaireRepository.save(beneficiaire);


                row = sheet.getRow(i);
                i++;
                System.out.println(i);
            }
        }
        finally {

        }

    }

    public void saveBeneficiaires() throws IOException {
        FileInputStream file = new FileInputStream(new File(System.getProperty("user.dir")+"/dueh.xlsx"));
        Workbook workbook = new XSSFWorkbook(file);
        Maison maison;


        try {

            Sheet sheet = workbook.getSheetAt(0);

            int i = 1;
            Row row = sheet.getRow(i);
            Cell cell;
            while (row != null) {
                Beneficiaire beneficiaire = new Beneficiaire();
                maison = maisonService.findById((long)row.getCell(0).getNumericCellValue()) ;
                beneficiaire.setMaison(maison);
                if(row.getCell(1)!=null)
                    beneficiaire.setNom(row.getCell(1).getStringCellValue());

                if(row.getCell(3)!=null)
                    beneficiaire.setSituationResident(row.getCell(3).getStringCellValue());

                if(row.getCell(4)!=null)
                    beneficiaire.setEtage(row.getCell(4).getStringCellValue());
                if(row.getCell(5)!=null)
                    beneficiaire.setCin(row.getCell(5).getStringCellValue());

                if(row.getCell(7)!=null)
                    beneficiaire.setSituationFamiliale(row.getCell(7).getStringCellValue());
                if(row.getCell(8)!=null)
                    beneficiaire.setNombreEnfant((int)row.getCell(8).getNumericCellValue());

                if(row.getCell(9)!=null)
                    beneficiaire.setObservations(row.getCell(9).getStringCellValue());

                beneficiaireRepository.save(beneficiaire);
                i++;
                row = sheet.getRow(i);

                System.out.println(i);
            }


        }
        finally {

        }

    }


    public void saveTirage() throws IOException {
        FileInputStream file = new FileInputStream(new File(System.getProperty("user.dir")+"/dueh.xlsx"));
        Workbook workbook = new XSSFWorkbook(file);

        try {
            Sheet sheet = workbook.getSheetAt(4);
            int i = 1;
            Row row = sheet.getRow(i);
            while (row != null) {
                Tirage tirage = new Tirage();
                if(row.getCell(1)!=null)
                    tirage.setNombreAppartement((int) row.getCell(1).getNumericCellValue());

                if(row.getCell(2)!=null)
                    tirage.setDateTirage(row.getCell(2).getDateCellValue());
                tirageRepository.save(tirage);
                i++;
                row = sheet.getRow(i);
                System.out.println(i);
            }
        }
        finally {

        }

    }

}
