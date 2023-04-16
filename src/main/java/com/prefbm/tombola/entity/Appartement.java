package com.prefbm.tombola.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "appartement")
public class Appartement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appartement_id", nullable = false)
    private Long appartementId;

    @Column(name = "adresse", length = 80)
    private String adresse;

    @Column(name = "numero_groupe", length = 10)
    private String numeroGroupe;

    @Column(name = "numero_immeuble", length = 10)
    private String numeroImmeuble;

    @Column(name = "numero_appartement", length = 10)
    private int numeroAppartement;

    @Column(name = "etage", length = 10)
    private String etage;
    @Column(name = "titre_foncier", length = 50)
    private String titreFoncier;
    @OneToMany(mappedBy = "appartement", fetch = FetchType.EAGER)
    List<Participation> participations = new ArrayList<>();

    // Another relationship to specify in order to know to which recensement they belong before the drawings


    public Appartement(String numeroGroupe, String adresse, String numeroImmeuble, int numeroAppatement, String etage, String titreFoncier) {
        this.numeroGroupe = numeroGroupe;
        this.adresse = adresse;
        this.numeroImmeuble = numeroImmeuble;
        this.numeroAppartement = numeroAppatement;
        this.etage = etage;
        this.titreFoncier = titreFoncier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appartement that = (Appartement) o;
        return Objects.equals(appartementId, that.appartementId) && Objects.equals(numeroGroupe, that.numeroGroupe) && Objects.equals(adresse, that.adresse) && Objects.equals(numeroImmeuble, that.numeroImmeuble) && Objects.equals(numeroAppartement, that.numeroAppartement) && Objects.equals(etage, that.etage) && Objects.equals(titreFoncier, that.titreFoncier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appartementId, numeroGroupe, adresse, numeroImmeuble, numeroAppartement, etage, titreFoncier);
    }

    @Override
    public String toString() {
        return "Appartement{" +
                "appartementId=" + appartementId +
                ", adresse='" + adresse + '\'' +
                ", numeroGroupe='" + numeroGroupe + '\'' +
                ", numeroImmeuble='" + numeroImmeuble + '\'' +
                ", numeroAppatement='" + numeroAppartement + '\'' +
                ", etage='" + etage + '\'' +
                ", titreFoncier='" + titreFoncier + '\'' +
                '}';
    }
}