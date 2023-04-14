package com.prefbm.tombola.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name ="participation")
@Data
@NoArgsConstructor
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participation_id", nullable = false)
    private Long participationId;
    @Basic
    @Column(name = "resultat", nullable = false)
    private boolean resultat;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tirage_id",referencedColumnName = "tirage_id",nullable = true)
    private Tirage tirage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "beneficiaire_id",referencedColumnName = "beneficiaire_id",nullable = true)
    private Beneficiaire beneficiaire;

    public Participation(boolean resultat, Tirage tirage, Beneficiaire beneficiaire) {
        this.resultat = resultat;
        this.tirage = tirage;
        this.beneficiaire = beneficiaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participation that = (Participation) o;
        return resultat == that.resultat && participationId.equals(that.participationId) && tirage.equals(that.tirage) && beneficiaire.equals(that.beneficiaire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participationId, resultat, tirage, beneficiaire);
    }

    @Override
    public String toString() {
        return "Participation{" +
                "participationId=" + participationId +
                ", resultat=" + resultat +
                ", tirage=" + tirage +
                ", beneficiaire=" + beneficiaire +
                '}';
    }
}
