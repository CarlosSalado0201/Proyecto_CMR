package com.mx.CMR.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "equipos")
@Data
public class Equipos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;


    @Column(name = "marca")
    private String marca;

    @Column(name = "precio")
    private Double precio;

    @ManyToOne
    @JoinColumn(name = "modelo_id", nullable = false)
    @JsonIgnoreProperties({"equipos", "materiales"})
    private Modelos modelo;

}