package com.prefbm.tombola.runner;

import com.prefbm.tombola.entity.*;
import com.prefbm.tombola.repository.BeneficiaireRepository;
import com.prefbm.tombola.repository.ParticipationRepository;
import com.prefbm.tombola.repository.RecensementRepository;
import com.prefbm.tombola.service.ClotureService;
import com.prefbm.tombola.service.MaisonService;
import com.prefbm.tombola.service.TirageService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;


//@Component
public class ExcelRunner implements CommandLineRunner {

    @Autowired
    RecensementRepository recensementRepository;

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
        this.saveMaisons();
        this.saveBeneficiaires();

    }


    public void saveMaisons() throws IOException {
        Calendar c = Calendar.getInstance();
        c.set(2022, 3, 16);
        Date date= c.getTime();
        Recensement recensement = new Recensement("درب خليفة", date, 45, "pvRecencement1");
        recensementRepository.save(recensement);
        FileInputStream file = new FileInputStream(new File("E://due.xlsx"));
        Workbook workbook = new XSSFWorkbook(file);
        Maison maison = new Maison();
        maison.setRecensement(recensement);


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

    public static void main(String[] args) throws IOException {

    }

    public void saveBeneficiaires() throws IOException {
        FileInputStream file = new FileInputStream(new File("E://dueh.xlsx"));
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

}
