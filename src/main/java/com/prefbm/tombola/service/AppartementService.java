package com.prefbm.tombola.service;


import com.prefbm.tombola.entity.Appartement;
import com.prefbm.tombola.repository.AppartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AppartementService {
    @Autowired
    AppartementRepository appartementRepository;
    Appartement findById(Long appartementId){
        return appartementRepository.findById(appartementId).get();
    }
    public void save(Appartement appartement){
        appartementRepository.save(appartement);
    }
    public List<Appartement> findAll(){
        return appartementRepository.findAll();
    }
    public void delete(Long appartementId){
        appartementRepository.deleteById(appartementId);
    }

    List<Appartement> findByVacantAppartements(){
        return null;
    }
}
