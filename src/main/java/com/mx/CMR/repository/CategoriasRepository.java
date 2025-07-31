package com.mx.CMR.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mx.CMR.model.Categorias;

public interface CategoriasRepository extends JpaRepository<Categorias, Integer> {
    Categorias findByNombre(String nombre);

    @Query("SELECT MAX(c.id) FROM Categorias c")
    Integer findMaxId();
    boolean existsByNombre(String nombre); // 👈 este método permite validar por nombre

}
