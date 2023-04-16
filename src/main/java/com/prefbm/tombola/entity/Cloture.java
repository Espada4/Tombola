package com.prefbm.tombola.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;
import java.util.stream.Collectors;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date clotureDate;
    @Basic
    @Column(name = "pv_cloture", nullable = false, length = 40)
    private String pvCloture;


    @OneToMany(mappedBy = "cloture", fetch = FetchType.EAGER)
    private List<Beneficiaire> beneficiaires = new ArrayList<>();

    public Cloture(Date clotureDate, String pvCloture, List<Beneficiaire> beneficiaires) {
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

    public List<Beneficiaire> getBeneficiaires() {
        return beneficiaires.stream().distinct().collect(Collectors.toList());
    }
}
