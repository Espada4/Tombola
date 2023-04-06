package com.prefbm.tombola.repository;

import com.prefbm.tombola.entity.Maison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MaisonRepository extends JpaRepository<Maison, Long> {
    @Query("select m from Maison m where m.recensement.recensementId = :recensementId")
    List<Maison> findMaisonsByRecensementId(@Param("recensementId") Long recensementId);

    @Query("SELECT m FROM Maison m WHERE NOT EXISTS " +
            "(SELECT b FROM Beneficiaire b WHERE b.maison = m AND b.cloture IS NULL)")
    List<Maison> findMaisonsClosed();

    @Query("SELECT m FROM Maison m WHERE EXISTS " +
            "(SELECT b FROM Beneficiaire b WHERE b.maison = m AND b.cloture IS NULL)")
    List<Maison> findMaisonsNotClosed();

   /* "SELECT b FROM Beneficiaire b WHERE EXISTS"+
            "(SELECT b FROM Beneficiaire b WHERE b.maison = m AND b.cloture IS NULL)")
    */

}