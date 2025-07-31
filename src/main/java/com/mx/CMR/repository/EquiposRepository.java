package com.mx.CMR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mx.CMR.model.Equipos;
import com.mx.CMR.model.Materiales;
import com.mx.CMR.model.Modelos;

public interface EquiposRepository extends JpaRepository<Equipos, Integer> {
	  List<Equipos> findByModelo_Categoria_Id(int categoriaId);

	@Query("SELECT MAX(e.id) FROM Equipos e")
	Integer findMaxId();

	List<Materiales> findByModelo(Modelos modelo);
	   Equipos findByMarcaAndModelo(String marca, Modelos modelo);

}
