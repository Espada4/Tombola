package com.prefbm.tombola.service;

import com.prefbm.tombola.entity.Beneficiaire;
import com.prefbm.tombola.entity.Participation;
import com.prefbm.tombola.entity.Tirage;
import com.prefbm.tombola.repository.BeneficiaireRepository;
import com.prefbm.tombola.repository.ParticipationRepository;
import com.prefbm.tombola.repository.TirageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TirageService {
    @Autowired
    TirageRepository tirageRepository;
    @Autowired
    ParticipationRepository participationRepository;
    @Autowired
    BeneficiaireRepository beneficiaireRepository;
    public void save(Tirage tirage){
        tirageRepository.save(tirage);
        for(Beneficiaire b:beneficiaireRepository.findParticipantsNextTirage()){
            participationRepository.save(new Participation(false,tirage,b));
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
