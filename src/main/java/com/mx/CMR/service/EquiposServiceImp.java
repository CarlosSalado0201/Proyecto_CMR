package com.mx.CMR.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mx.CMR.model.Equipos;
import com.mx.CMR.model.Modelos;
import com.mx.CMR.repository.EquiposRepository;

@Service
public class EquiposServiceImp {


    @Autowired
    private EquiposRepository equiposRepo;

    @Transactional(readOnly = true)
    public List<Equipos> mostrar() {
        return (List<Equipos>) equiposRepo.findAll();
    }

    @Transactional
    public String guardar(Equipos equipo) {
        Equipos existente = equiposRepo.findByMarcaAndModelo(equipo.getMarca(), equipo.getModelo());
        if (existente != null) {
            return "equipoYaExiste";
        }

        equiposRepo.save(equipo);
        return "guardadoConExito";
    }

    @Transactional(readOnly = true)
    public List<Equipos> buscarPorCategoria(int idCategoria) {
        // Verifica que tu repositorio tenga este método
       return equiposRepo.findByModelo_Categoria_Id(idCategoria);

    }

    @Transactional(readOnly = true)
    public Equipos buscarPorMarcaYModelo(String marca, Modelos modelo) {
        return equiposRepo.findByMarcaAndModelo(marca, modelo);
    }

    @Transactional(readOnly = true)
    public Equipos buscar(Integer id) {
        return equiposRepo.findById(id).orElse(null);
    }
	
	@Transactional
	public boolean editar(Equipos equipo) {
	    Optional<Equipos> optEquipoExistente = equiposRepo.findById(equipo.getId());
	    if (optEquipoExistente.isEmpty()) {
	        return false;
	    }
	
	    Equipos equipoExistente = optEquipoExistente.get();
	    // Actualizar solo los campos que quieres modificar
	    equipoExistente.setMarca(equipo.getMarca());
	    equipoExistente.setPrecio(equipo.getPrecio());
	    // Si la relación con Modelo puede cambiar, actualízala así:
	    if (equipo.getModelo() != null && equipo.getModelo().getId() != null) {
	        equipoExistente.setModelo(equipo.getModelo());
	    }
	    // No modifiques otras relaciones para evitar eliminar datos hijos
	
	    equiposRepo.save(equipoExistente);
	    return true;
	}

    @Transactional
    public void eliminar(Integer id) {
        equiposRepo.deleteById(id);
    }

}
