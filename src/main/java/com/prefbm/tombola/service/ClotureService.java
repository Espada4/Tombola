package com.prefbm.tombola.service;

import com.prefbm.tombola.entity.Beneficiaire;
import com.prefbm.tombola.entity.Cloture;
import com.prefbm.tombola.repository.BeneficiaireRepository;
import com.prefbm.tombola.repository.ClotureRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ClotureService {
    @Autowired
    ClotureRepository clotureRepository;
    @Autowired
    BeneficiaireRepository beneficiaireRepository;

    public void save(Cloture cloture){
        for(Beneficiaire beni : cloture.getBeneficiaires()) {
            Beneficiaire ben = beneficiaireRepository.findById(beni.getBeneficiaireId()).get();
            ben.setCloture(cloture);
            beneficiaireRepository.save(ben);
        }
        clotureRepository.save(cloture);

    }

    public Cloture findById(Long id){
        return clotureRepository.findById(id).get();
    }

    public List<Cloture> findAll(){
        return clotureRepository.findAll();
    }
    public void delete(Long clotureId){
        clotureRepository.deleteById(clotureId);
    }


}
