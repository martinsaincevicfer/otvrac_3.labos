package com.otvrac.backend.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Transactional
@Table(name = "serije")
public class Serije {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "serije_id_gen")
    @SequenceGenerator(name = "serije_id_gen", sequenceName = "serije_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "naslov", nullable = false)
    private String naslov;

    @Column(name = "zanr")
    private String zanr;

    @Column(name = "godina_izlaska")
    private Integer godinaIzlaska;

    @Column(name = "ocjena")
    private Double ocjena;

    @Column(name = "broj_sezona")
    private Integer brojSezona;

    @Column(name = "jezik", length = 100)
    private String jezik;

    @Column(name = "autor")
    private String autor;

    @Column(name = "mreza", length = 100)
    private String mreza;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "serija", fetch = FetchType.EAGER)
    @JsonManagedReference("serija-epizode")
    private List<Epizode> epizode = new ArrayList<>();
}