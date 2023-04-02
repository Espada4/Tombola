package com.prefbm.tombola.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(name = "recencement_id", nullable = false)
    private Long recencementId;
    @Basic
    @Column(name = "recensement_date", nullable = false, length = 45)
    private Date recensementDate;
    @Basic
    @Column(name = "recensement_nombre", nullable = false, length = 45)
    private int recensementNombre;
    @Basic
    @Column(name = "recencement_pv", nullable = false, length = 45)
    private String recencementPV;

    @OneToMany(mappedBy = "recensement", fetch = FetchType.LAZY)
    private Set<Maison> maisons = new HashSet<>();


    public Recensement(Date recensementDate, int recensementNombre, String recencementPV) {
        this.recensementDate = recensementDate;
        this.recensementNombre = recensementNombre;
        this.recencementPV = recencementPV;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recensement that = (Recensement) o;
        return recensementNombre == that.recensementNombre && recencementId.equals(that.recencementId) && recensementDate.equals(that.recensementDate) && Objects.equals(recencementPV, that.recencementPV);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recencementId, recensementDate, recensementNombre, recencementPV);
    }

    @Override
    public String toString() {
        return "Recensement{" +
                "recencementId=" + recencementId +
                ", recensementDate=" + recensementDate +
                ", recensementNombre=" + recensementNombre +
                ", recencementPV='" + recencementPV + '\'' +
                ", maisons=" + maisons +
                '}';
    }


}
