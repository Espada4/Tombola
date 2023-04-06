package com.prefbm.tombola.repository;

import com.prefbm.tombola.entity.Beneficiaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.util.List;

public interface BeneficiaireRepository extends JpaRepository<Beneficiaire, Long> {
    @Query("select b from Beneficiaire b where b.maison.maisonID = :maisonID")
    List<Beneficiaire> findBeneficiaireByMaisonID(@Param("maisonID") Long maisonID);

    @Nullable
    Beneficiaire findByCinAllIgnoreCase(String cin);

    @Query("""
            select b from Beneficiaire b inner join b.participations participations
            where participations.tirage.tirageId = :tirageId""")
    List<Beneficiaire> FindBeneficiaireByTirageId(@Param("tirageId") Long tirageId);

    @Query("""
            select b from Beneficiaire b inner join b.participations participations
            where participations.tirage.tirageId = :tirageId and participations.resultat = true""")
    List<Beneficiaire> findWinnersByTirageId(@Param("tirageId") Long tirageId);

    @Query("select b from Beneficiaire b inner join b.participations participations where participations.resultat = true")
    List<Beneficiaire> findAllWinners();

    @Query("""
            select b from Beneficiaire b WHERE  NOT EXISTS
            (select p from Participation p where p.beneficiaire = b AND p.resultat=true)  """)
    List<Beneficiaire> findParticipantsNextTirage();

    List<Beneficiaire> findByCinContainsAllIgnoreCase(String cin);











}