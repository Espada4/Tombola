package com.prefbm.tombola.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    @Column(name = "cin", nullable = true, length = 10)
    private String cin;

    @Basic
    @Column(name = "prenom", nullable = false, length = 40)
    private String prenom;
    @Basic
    @Column(name = "nom", nullable = false, length = 40)
    private String nom;
    @Basic
    @Column(name = "situation_familiale", nullable = false, length = 30)
    private String situationFamiliale;
    @Basic
    @Column(name = "nombre_enfant", nullable = false)
    private int nombreEnfant;

    @Basic
    @Column(name = "situation_resident", nullable = false, length = 25)
    private String situationResident;

    @Basic
    @Column(name = "type_residence", nullable = true, length = 25)
    private String typeResidence;
    @Basic
    @Column(name = "etage", nullable = true, length = 25)
    private String etage;

    @Basic
    @Column(name = "observations", nullable = true, length = 100)
    private String Observations;

    Boolean resultat=true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maison_id",referencedColumnName = "maison_id",nullable = false)
    private Maison maison;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cloture_id",referencedColumnName = "cloture_id",nullable = true)
    private Cloture cloture;

    @OneToMany(mappedBy = "beneficiaire", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<Participation> participations = new HashSet<>();

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
        return nombreEnfant == that.nombreEnfant && beneficiaireId.equals(that.beneficiaireId) && Objects.equals(cin, that.cin) && prenom.equals(that.prenom) && nom.equals(that.nom) && situationFamiliale.equals(that.situationFamiliale) && situationResident.equals(that.situationResident) && Objects.equals(typeResidence, that.typeResidence) && Objects.equals(etage, that.etage) && Objects.equals(Observations, that.Observations) && maison.equals(that.maison);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beneficiaireId, cin, prenom, nom, situationFamiliale, nombreEnfant, situationResident, typeResidence, etage);
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
                ", Observations='" + Observations + '\'' +
                '}';
    }
}
