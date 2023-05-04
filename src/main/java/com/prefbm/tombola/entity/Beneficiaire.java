package com.prefbm.tombola.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.*;

@Entity
@Table(name ="beneficiaire")
@Data
@NoArgsConstructor
public class Beneficiaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "beneficiaire_id", nullable = false)
    private Long beneficiaireId;
    @Basic
    @Column(name = "cin", nullable = true, length = 30)
    private String cin;

    @Basic
    @Column(name = "prenom", nullable = true, length = 40)
    private String prenom;
    @Basic
    @Column(name = "nom", nullable = true, length = 80)
    private String nom;
    @Basic
    @Column(name = "situation_familiale", nullable = true, length = 40)
    private String situationFamiliale;
    @Basic
    @Column(name = "nombre_enfant", nullable = true)
    private int nombreEnfant;

    @Basic
    @Column(name = "situation_resident", nullable = true, length = 80)
    private String situationResident;

    @Basic
    @Column(name = "type_residence", nullable = true, length = 40)
    private String typeResidence;
    @Basic
    @Column(name = "etage", nullable = true, length = 50)
    private String etage;

    @Basic
    @Column(name = "observations", nullable = true, length = 250)
    private String observations;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maison_id",referencedColumnName = "maison_id",nullable = false)
    private Maison maison;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cloture_id",referencedColumnName = "cloture_id",nullable = true)
    private Cloture cloture;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "participation_id",referencedColumnName = "participation_id",nullable = true)
    private Participation participation;

    public Beneficiaire(String cin,String prenom, String nom, String situationFamiliale, int nombreEnfant, String situationResident, String typeResidence, Maison maison) {
        this.cin = cin;
        this.prenom = prenom;
        this.nom = nom;
        this.situationFamiliale = situationFamiliale;
        this.nombreEnfant = nombreEnfant;
        this.situationResident = situationResident;
        this.typeResidence = typeResidence;
        this.maison = maison;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Beneficiaire that = (Beneficiaire) o;
        return nombreEnfant == that.nombreEnfant && beneficiaireId.equals(that.beneficiaireId) && Objects.equals(cin, that.cin) && prenom.equals(that.prenom) && nom.equals(that.nom) && situationFamiliale.equals(that.situationFamiliale) && situationResident.equals(that.situationResident) && Objects.equals(typeResidence, that.typeResidence) && Objects.equals(etage, that.etage) && Objects.equals(observations, that.observations) && maison.equals(that.maison);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beneficiaireId, cin, prenom, nom, situationFamiliale, nombreEnfant, situationResident, typeResidence, etage);
    }
    public boolean resultatTirages(){
        return participation!=null;
    }

    @Override
    public String toString() {
        return "Beneficiaire{" +
                "beneficiaireId=" + beneficiaireId +
                ", cin='" + cin + '\'' +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", situationFamiliale='" + situationFamiliale + '\'' +
                ", nombreEnfant=" + nombreEnfant +
                ", situationResident='" + situationResident + '\'' +
                ", typeResidence='" + typeResidence + '\'' +
                ", etage='" + etage + '\'' +
                ", Observations='" + observations + '\'' +
                '}';
    }
}
