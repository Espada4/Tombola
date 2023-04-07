package com.prefbm.tombola.service;

import com.prefbm.tombola.entity.Recensement;
import com.prefbm.tombola.repository.RecensementRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RecensementService {
    @Autowired
    RecensementRepository recensementRepository;


    public Recensement findById(Long recensementId) {
        return recensementRepository.findById(recensementId).orElseThrow(() -> new EntityNotFoundException("Recensement with id" + recensementId + " Not Found"));
    }
 //Date recensementDate, int recensementNombre, String recencementPV
    public Recensement save(Recensement recensement) {

        return recensementRepository.save(recensement);
    }

    public List<Recensement> findAll(){
        return recensementRepository.findAll();
    }

    public void delete(Long recensementId) {
        recensementRepository.deleteById(recensementId);
    }


}
