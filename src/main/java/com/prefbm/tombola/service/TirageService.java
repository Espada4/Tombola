package com.prefbm.tombola.service;

import com.prefbm.tombola.entity.Beneficiaire;
import com.prefbm.tombola.entity.Participation;
import com.prefbm.tombola.entity.Tirage;
import com.prefbm.tombola.repository.BeneficiaireRepository;
import com.prefbm.tombola.repository.ParticipationRepository;
import com.prefbm.tombola.repository.TirageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
