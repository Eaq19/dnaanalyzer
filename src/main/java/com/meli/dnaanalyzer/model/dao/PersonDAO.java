package com.meli.dnaanalyzer.model.dao;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "Person")
public class PersonDAO {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String dna;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private TypeDAO type;
}
