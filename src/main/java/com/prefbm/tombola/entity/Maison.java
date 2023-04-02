package com.prefbm.tombola.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name ="maison")
@Data
@NoArgsConstructor
public class Maison {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maison_id", nullable = false)
    private Long maisonID;
    @Basic
    @Column(name = "proprietaire", nullable = false, length = 80)
    private String proprietaire;

    @Basic
    @Column(name = "boulevard", nullable = false, length = 60)
    private String boulevard;
    @Basic
    @Column(name = "maison_rue", nullable = false)
    private int maisonRue;
    @Basic
    @Column(name = "maison_numero", nullable = false, length = 60)
    private int maisonNumero;

    @Basic
    @Column(name = "nombre_famille", nullable = true, length = 60)
    private int nombreFamille;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recensement_id",referencedColumnName = "recensement_id",nullable = false)
    private Recensement recensement;

    @OneToMany(mappedBy = "maison", fetch = FetchType.LAZY)
    private Set<Beneficiaire> beneficiaires = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Maison maison = (Maison) o;
        return maisonRue == maison.maisonRue && maisonNumero == maison.maisonNumero && maisonID.equals(maison.maisonID) && proprietaire.equals(maison.proprietaire) && boulevard.equals(maison.boulevard) && recensement.equals(maison.recensement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maisonID, proprietaire, boulevard, maisonRue, maisonNumero, recensement);
    }

    @Override
    public String toString() {
        return "Maison{" +
                "maisonID=" + maisonID +
                ", proprietaire='" + proprietaire + '\'' +
                ", boulevard='" + boulevard + '\'' +
                ", maisonRue=" + maisonRue +
                ", maisonNumero=" + maisonNumero +
                ", recensement=" + recensement +
                '}';
    }
}
