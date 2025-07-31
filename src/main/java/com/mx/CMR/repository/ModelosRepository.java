package com.mx.CMR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mx.CMR.model.Modelos;

public interface ModelosRepository extends JpaRepository<Modelos, Integer> {
    Modelos findByNombre(String nombre);

    @Query("SELECT MAX(m.id) FROM Modelos m")
    Integer findMaxId();

    List<Modelos> findByCategoriaId(int categoriaId);
}
