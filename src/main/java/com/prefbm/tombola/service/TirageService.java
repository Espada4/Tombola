package com.prefbm.tombola.service;

import com.prefbm.tombola.entity.Beneficiaire;
import com.prefbm.tombola.entity.Participation;
import com.prefbm.tombola.entity.Tirage;
import com.prefbm.tombola.repository.BeneficiaireRepository;
import com.prefbm.tombola.repository.ParticipationRepository;
import com.prefbm.tombola.repository.TirageRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class TirageService {
    @Autowired
    TirageRepository tirageRepository;
    @Autowired
    ParticipationRepository participationRepository;
    @Autowired
    BeneficiaireRepository beneficiaireRepository;
    public void save(Tirage tirage){
        tirageRepository.save(tirage);
        Participation p;
        //Tirage tirage1 = tirageRepository.findById(tirage.getTirageId()).get();
        System.out.println("-----------------------------------------------------------------"+tirage.getTirageId());
        for(Beneficiaire b:beneficiaireRepository.findParticipantsNextTirage()){
            p=new Participation(false, tirage, b);
            participationRepository.save(p);
        }
    }

    public Tirage findById(Long tirageId){
        return tirageRepository.findById(tirageId).orElseThrow(() -> new EntityNotFoundException("Tirage with id" + tirageId + " Not Found"));
    }

    public List<Tirage> findAll(){
        return tirageRepository.findAll();
    }

    public void delete(Long tirageId){

        tirageRepository.deleteById(tirageId);
    }
}
