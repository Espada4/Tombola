package com.prefbm.tombola.service;

import com.prefbm.tombola.entity.Maison;
import com.prefbm.tombola.repository.MaisonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaisonService {
    @Autowired
    MaisonRepository maisonRepository;

    public void save(Maison maison){
        maisonRepository.save(maison);
    }
    public Maison findById(Long id){
        return maisonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Maison with id" + id + " Not Found"));
    }

    public List<Maison> findAll(){
        return maisonRepository.findAll();
    }

    public void delete(Long id){
        maisonRepository.deleteById(id);
    }

}
