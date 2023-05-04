package com.prefbm.tombola.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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

    @OneToMany(mappedBy = "participation", fetch = FetchType.EAGER)
    private List<Beneficiaire> beneficiaires;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appartement_id",referencedColumnName = "appartement_id",nullable = true)
    private Appartement appartement;

    @Basic
    @Column(name = "num_dossier", nullable = true, length = 60)
    private String num_dossier;

    public Participation(boolean resultat, Tirage tirage, List<Beneficiaire> beneficiaires, String num_dossier) {
        this.resultat = resultat;
        this.tirage = tirage;
        this.num_dossier = num_dossier;
        this.beneficiaires = beneficiaires;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participation that = (Participation) o;
        return resultat == that.resultat && num_dossier == that.num_dossier && participationId.equals(that.participationId) && tirage.equals(that.tirage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participationId, resultat, tirage);
    }

    @Override
    public String toString() {
        return "Participation{" +
                "participationId=" + participationId +
                ", resultat=" + resultat +
                ", num_dossier=" + num_dossier +
                ", tirage=" + tirage +
                '}';
    }
}
