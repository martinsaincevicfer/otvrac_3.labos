package com.otvrac.backend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "epizode")
public class Epizode {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "epizode_id_gen")
    @SequenceGenerator(name = "epizode_id_gen", sequenceName = "epizode_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "serija_id")
    @JsonBackReference("serija-epizode")
    private Serije serija;

    @Column(name = "naziv_epizode", nullable = false)
    private String nazivEpizode;

    @Column(name = "sezona")
    private Integer sezona;

    @Column(name = "broj_epizode")
    private Integer brojEpizode;

    @Column(name = "datum_emitiranja")
    private LocalDate datumEmitiranja;

    @Column(name = "trajanje")
    private Integer trajanje;

    @Column(name = "ocjena")
    private Double ocjena;

    @Column(name = "scenarist")
    private String scenarist;

    @Column(name = "redatelj")
    private String redatelj;
}