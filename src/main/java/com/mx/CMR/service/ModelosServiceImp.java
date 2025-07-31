package com.mx.CMR.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mx.CMR.model.Modelos;
import com.mx.CMR.repository.ModelosRepository;

@Service
public class ModelosServiceImp {

    @Autowired
    private ModelosRepository modelosRepo;

    @Transactional(readOnly = true)
    public List<Modelos> mostrar() {
        return modelosRepo.findAll();
    }

    @Transactional
    public Modelos guardar(Modelos modelo) {
        return modelosRepo.save(modelo);
    }

    @Transactional(readOnly = true)
    public List<Modelos> buscarPorCategoria(int idCategoria) {
        return modelosRepo.findByCategoriaId(idCategoria);
    }

    @Transactional(readOnly = true)
    public Modelos buscarPorNombre(String nombre) {
        return modelosRepo.findByNombre(nombre);
    }

    @Transactional(readOnly = true)
    public Modelos buscar(Integer id) {
        return modelosRepo.findById(id).orElse(null);
    }

	@Transactional
	public boolean editar(Modelos modelo) {
	    Optional<Modelos> optModeloExistente = modelosRepo.findById(modelo.getId());
	    if (optModeloExistente.isEmpty()) {
	        return false;
	    }
	
	    Modelos modeloExistente = optModeloExistente.get();
	    // Actualiza solo los campos que quieres modificar
	    modeloExistente.setNombre(modelo.getNombre());
	    modeloExistente.setCapacidad(modelo.getCapacidad());
	    // No toques la categoría ni las relaciones para evitar eliminar datos hijos
	
	    modelosRepo.save(modeloExistente);
	    return true;
	}

    @Transactional
    public void eliminar(Integer id) {
        modelosRepo.deleteById(id);
    }
}
