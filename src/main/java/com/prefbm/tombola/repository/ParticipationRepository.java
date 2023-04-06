package com.prefbm.tombola.repository;

import com.prefbm.tombola.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    @Query("select count(p) from Participation p where p.beneficiaire.beneficiaireId = :beneficiaireId")
    long participationCountByBeneficiaire(@Param("beneficiaireId") Long beneficiaireId);

    @Query("select count(p) from Participation p where p.tirage.tirageId = :tirageId")
    long numberOfParticipantByTirage(@Param("tirageId") Long tirageId);




}