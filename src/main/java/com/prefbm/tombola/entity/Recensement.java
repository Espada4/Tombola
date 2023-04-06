package com.prefbm.tombola.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name ="recensement")
@Data
@NoArgsConstructor
public class Recensement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recensement_id", nullable = false)
    private Long recensementId;


    @Basic
    @Column(name = "recensement_date", nullable = false, length = 45)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date recensementDate;
    @Basic
    @Column(name = "recensement_nombre", nullable = false, length = 45)
    private int recensementNombre;
    @Basic
    @Column(name = "recencement_pv", nullable = true, length = 45)
    private String recensementPV;

    @OneToMany(mappedBy = "recensement", fetch = FetchType.LAZY)
    private Set<Maison> maisons = new HashSet<>();


    public Recensement(Date recensementDate, int recensementNombre, String recencementPV) {
        /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.recensementDate = LocalDate.parse(dateString, formatter);*/
        this.recensementDate = recensementDate;
        this.recensementNombre = recensementNombre;
        this.recensementPV = recencementPV;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recensement that = (Recensement) o;
        return recensementNombre == that.recensementNombre && recensementId.equals(that.recensementId) && recensementDate.equals(that.recensementDate) && Objects.equals(recensementPV, that.recensementPV);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recensementId, recensementDate, recensementNombre, recensementPV);
    }

    @Override
    public String toString() {
        return "Recensement{" +
                "recencementId=" + recensementId +
                ", recensementDate=" + recensementDate +
                ", recensementNombre=" + recensementNombre +
                ", recencementPV='" + recensementPV + '\'' +
                '}';
    }


}
