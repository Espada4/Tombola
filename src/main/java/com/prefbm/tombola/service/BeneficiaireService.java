package com.prefbm.tombola.service;

import com.prefbm.tombola.entity.Beneficiaire;
import com.prefbm.tombola.entity.Maison;
import com.prefbm.tombola.repository.BeneficiaireRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeneficiaireService {
    @Autowired
    BeneficiaireRepository beneficiaireRepository;
    public void save(Beneficiaire beneficiaire){
        beneficiaireRepository.save(beneficiaire);
    }

    public Beneficiaire findById(Long id){
        return beneficiaireRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Beneficiaire with id" + id + " Not Found"));
    }
    public List<Beneficiaire> findAll(){
        return beneficiaireRepository.findAll();
    }
    public void delete(Long id){
        beneficiaireRepository.deleteById(id);
    }

    public List<Beneficiaire> findByCin(String keyword){
        return beneficiaireRepository.findByCinContainsAllIgnoreCase( keyword);
    }

    public List<Beneficiaire> findNextParticipants(){
        return beneficiaireRepository.findParticipantsNextTirage();
    }

    public List<Beneficiaire> findByTirageId(Long tirageId){return beneficiaireRepository.findBeneficiaireByTirageId(tirageId);}
    public List<Beneficiaire> findByNotClosed(){return beneficiaireRepository.FindNonClosed();}


}
