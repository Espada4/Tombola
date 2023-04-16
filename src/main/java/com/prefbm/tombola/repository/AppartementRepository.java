package com.prefbm.tombola.repository;

import com.prefbm.tombola.entity.Appartement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppartementRepository extends JpaRepository<Appartement, Long> {
    @Query("""
            select a from Appartement a inner join a.participations participations
            where participations.appartement.appartementId is null
            order by a.appartementId""")
    List<Appartement> findVacantAppartments();

    @Query("""
            select a from Appartement a inner join a.participations participations
            where participations.appartement.appartementId is not null
            order by a.appartementId""")
    List<Appartement> findOccupiedAppartments();

}