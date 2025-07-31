	package com.mx.CMR.repository;
	
	import java.util.List;
	
	import org.springframework.data.jpa.repository.JpaRepository;
	import org.springframework.data.jpa.repository.Query;
	
	import com.mx.CMR.model.Materiales;
	import com.mx.CMR.model.Modelos;
	import com.mx.CMR.model.Equipos;
	
	public interface MaterialesRepository extends JpaRepository<Materiales, Integer> {
	    Materiales findByNombre(String nombre);
	
	    @Query("SELECT MAX(m.id) FROM Materiales m")
	    Integer findMaxId();
	
	    // Buscar materiales por modelo (no por equipo)
	    List<Materiales> findByModelo(Modelos modelo);
	
	    // Buscar materiales por modelo id
	    List<Materiales> findByModeloId(Integer modeloId);
	    boolean existsByNombre(String nombre); // ← AÑADE ESTA LÍNEA
	    List<Materiales> findByModeloId(int modeloId);
	}