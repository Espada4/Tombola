package com.prefbm.tombola.repository;

import com.prefbm.tombola.entity.Recensement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RecensementRepository extends JpaRepository<Recensement, Long> {
    @Query("select r from Recensement r where r.recensementDate between :recensementDateStart and :recensementDateEnd")
    List<Recensement> findByDateBetween(@Param("recensementDateStart") Date recensementDateStart, @Param("recensementDateEnd") Date recensementDateEnd);

    @Query("select r from Recensement r where r.recensementDate = :recensementDate")
    @Nullable
    Optional<Recensement> findByRecensementDate(@Param("recensementDate") Date recensementDate);



}