package com.prefbm.tombola.repository;

import com.prefbm.tombola.entity.Cloture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.util.List;

public interface ClotureRepository extends JpaRepository<Cloture, Long> {
    @Query("""
            select c from Cloture c inner join c.beneficiaires beneficiaires
            where upper(beneficiaires.cin) = upper(:cin)""")
    @Nullable
    Cloture searchClotureByBeneficiaireCin(@Param("cin") String cin);

    @Query("""
            select c from Cloture c inner join c.beneficiaires beneficiaires
            where upper(beneficiaires.prenom) = upper(:prenom) and upper(beneficiaires.nom) = upper(:nom)""")
    @Nullable
    List<Cloture> searchClotureByBeneficiaireNomPrenom(@Param("prenom") String prenom, @Param("nom") String nom);

    @Query("""
            select c from Cloture c inner join c.beneficiaires beneficiaires
            where  beneficiaires.maison.maisonRue = :maisonRue and beneficiaires.maison.maisonNumero = :maisonNumero""")
    List<Cloture> searchClotureByMaison(@Param("maisonRue") int maisonRue, @Param("maisonNumero") int maisonNumero);

}