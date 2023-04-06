package com.prefbm.tombola.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name ="cloture")
@Data
@NoArgsConstructor
public class Cloture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cloture_id", nullable = false)
    private Long clotureId;
    @Basic
    @Column(name = "cloture_date", nullable = false)
    private Date clotureDate;
    @Basic
    @Column(name = "pv_cloture", nullable = false, length = 40)
    private String pvCloture;

    @OneToMany(mappedBy = "cloture", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Set<Beneficiaire> beneficiaires = new HashSet<>();

    public Cloture(Date clotureDate, String pvCloture, Set<Beneficiaire> beneficiaires) {
        this.clotureDate = clotureDate;
        this.pvCloture = pvCloture;
        this.beneficiaires = beneficiaires;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cloture cloture = (Cloture) o;
        return clotureId.equals(cloture.clotureId) && clotureDate.equals(cloture.clotureDate) && pvCloture.equals(cloture.pvCloture) && Objects.equals(beneficiaires, cloture.beneficiaires);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clotureId, clotureDate, pvCloture, beneficiaires);
    }

    @Override
    public String toString() {
        return "Cloture{" +
                "clotureId=" + clotureId +
                ", clotureDate=" + clotureDate +
                ", pvCloture='" + pvCloture + '\'' +
                ", beneficiaires=" + beneficiaires +
                '}';
    }
}
