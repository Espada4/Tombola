package com.prefbm.tombola.runner;

import com.prefbm.tombola.entity.*;
import com.prefbm.tombola.repository.*;
import com.prefbm.tombola.service.ClotureService;
import com.prefbm.tombola.service.TirageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
public class MyRunner implements CommandLineRunner {

    @Autowired
    RecensementRepository recensementRepository;

    @Autowired
    MaisonRepository maisonRepository;
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

        Recensement recensement = new Recensement(new Date(),45,"pvRecencement1");
        recensementRepository.save(recensement);
        Maison maison1= new Maison("Wlad lmekki","abdkrim khatabi",3,127,3,recensement);
        Maison maison2= new Maison("waratati","Ghandi",5,245,3,recensement);
        Maison maison3= new Maison("waratatoh","Derb sultan",1,13,3,recensement);
        recensement.getMaisons().add(maison1);
        //Save maison
        maisonRepository.save(maison1);
        recensement.getMaisons().add(maison2);
        maisonRepository.save(maison2);
        recensement.getMaisons().add(maison3);
        maisonRepository.save(maison3);
        Beneficiaire beneficiaire1 = new Beneficiaire("C111","Mohammed","WeldMo","veuf",2,"loyé","sta7",maison1);
        Beneficiaire beneficiaire2 = new Beneficiaire("C222","Mohammed2","WeldMo2","veuf",2,"loyé","sta7",maison1);
        Beneficiaire beneficiaire3 = new Beneficiaire("c333","Mohammed3","WeldMo3","veuf",2,"loyé","sta7",maison1);
        Beneficiaire beneficiaire4 = new Beneficiaire("C444","Mohammed4","WeldMo4","veuf",2,"loyé","sta7",maison2);
        Beneficiaire beneficiaire5 = new Beneficiaire("C555","Mohammed5","WeldMo5","veuf",2,"loyé","sta7",maison2);
        Beneficiaire beneficiaire6 = new Beneficiaire("C666","Mohammed6","WeldMo6","veuf",2,"loyé","sta7",maison2);
        Beneficiaire beneficiaire7 = new Beneficiaire("C777","Mohammed7","WeldMo7","veuf",2,"loyé","sta7",maison3);
        Beneficiaire beneficiaire8 = new Beneficiaire("C888","Mohammed8","WeldMo8","veuf",2,"loyé","sta7",maison3);
        Beneficiaire beneficiaire9 = new Beneficiaire("C999","Mohammed","WeldMo","veuf",2,"loyé","sta7",maison3);

        beneficiaireRepository.save(beneficiaire1);
        beneficiaireRepository.save(beneficiaire2);
        beneficiaireRepository.save(beneficiaire3);
        beneficiaireRepository.save(beneficiaire4);
        beneficiaireRepository.save(beneficiaire5);
        beneficiaireRepository.save(beneficiaire6);
        beneficiaireRepository.save(beneficiaire7);
        beneficiaireRepository.save(beneficiaire8);
        beneficiaireRepository.save(beneficiaire9);

        Set<Beneficiaire> set1 = new HashSet<>();
        set1.add(beneficiaire3);set1.add(beneficiaire2);
        Cloture cloture1 = new Cloture(new Date(),"pvCloture1",set1);
        /*cloture1.getBeneficiaires().add(beneficiaire2);
        cloture1.getBeneficiaires().add(beneficiaire3);*/

        Set<Beneficiaire> set2 = new HashSet<>(); set2.add(beneficiaire1);
        Cloture cloture2 = new Cloture(new Date(),"pvCloture2",set2);

        Set<Beneficiaire> set3 = new HashSet<>(); set3.add(beneficiaire5);
        Cloture cloture3 = new Cloture(new Date(),"pvCloture3",set3);


        clotureService.save(cloture1);

        clotureService.save(cloture2);

        clotureService.save(cloture3);
        ////////////////////////////////////////////////////////////test searchs Maison////////////////////////////////////

        List<Maison> maisons  = maisonRepository.findMaisonsClosed();
        System.out.println("the size is"+maisons.size());
        System.out.println(maisons.get(0));

        maisons  = maisonRepository.findMaisonsByRecensementId(1L);
        for(Maison maison:maisons) System.out.println(maison);

        //////////////////////////////////Beneficiaire searchs ///////////////////////////////////////////////////////////
        List<Beneficiaire> beneficiaires = beneficiaireRepository.findBeneficiaireByMaisonID(3L);
        System.out.println(beneficiaires.size());
        for(Beneficiaire beneficiaire:beneficiaires) System.out.println(beneficiaire);


        Tirage tirage1 = new Tirage(new Date(),3);
        Tirage tirage2 = new Tirage(new Date(),2);
        tirageService.save(tirage1);

        Participation p1 = participationRepository.findById(1L).get();
        Participation p2 = participationRepository.findById(2L).get();
        Participation p3 = participationRepository.findById(5L).get();

        p1.setResultat(true); participationRepository.save(p1);
        p2.setResultat(true); participationRepository.save(p2);
        p3.setResultat(true); participationRepository.save(p3);

        tirageService.save(tirage2);


        System.out.println("-----------------------------------------Par tirage Id------------------------------------");
        beneficiaires = beneficiaireRepository.findParticipantsNextTirage();
        System.out.println("---------------------------------------The size is : "+ beneficiaires.size());
        for(Beneficiaire beneficiaire:beneficiaires) System.out.println(beneficiaire);

        System.out.println("participation b1 :"+participationRepository.participationCountByBeneficiaire(beneficiaire1.getBeneficiaireId()));
        System.out.println("participation b2 :"+participationRepository.participationCountByBeneficiaire(beneficiaire4.getBeneficiaireId()));

        System.out.println("Tirage 1 :"+participationRepository.numberOfParticipantByTirage(tirage1.getTirageId()));
        System.out.println("Tirage 2 :"+participationRepository.numberOfParticipantByTirage(tirage2.getTirageId()));

    }
}
