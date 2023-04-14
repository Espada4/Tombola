package com.prefbm.tombola.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;

@Entity
@Table(name ="tirage")
@Setter
@Getter
@NoArgsConstructor
public class Tirage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tirage_id", nullable = false)
    private Long tirageId;
    @Basic
    @Column(name = "date_tirage", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateTirage;
    @Basic
    @Column(name = "nombre_appartement", nullable = true)
    private int nombreAppartement;
    @Basic
    @Column(name = "pv_tirage", nullable = true, length = 40)
    private String pvTirage;

    @OneToMany(mappedBy = "tirage", fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<Participation> participations = new ArrayList<>();


    public Tirage(Date dateTirage, int nombreAppartement) {
        this.dateTirage = dateTirage;
        this.nombreAppartement = nombreAppartement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tirage tirage = (Tirage) o;
        return nombreAppartement == tirage.nombreAppartement && Objects.equals(tirageId, tirage.tirageId) && Objects.equals(dateTirage, tirage.dateTirage) && Objects.equals(pvTirage, tirage.pvTirage) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tirageId, dateTirage, nombreAppartement, pvTirage);
    }

    @Override
    public String toString() {
        return "Tirage{" +
                "tirageId=" + tirageId +
                ", dateTirage=" + dateTirage +
                ", nombreAppartement=" + nombreAppartement +
                ", pvTirage='" + pvTirage + '\'' +
                '}';
    }
}
