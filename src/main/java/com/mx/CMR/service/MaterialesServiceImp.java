package com.mx.CMR.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mx.CMR.model.Materiales;
import com.mx.CMR.model.Modelos;
import com.mx.CMR.model.Equipos;
import com.mx.CMR.repository.MaterialesRepository;
import com.mx.CMR.repository.ModelosRepository;

@Service
public class MaterialesServiceImp {

    @Autowired
    private MaterialesRepository materialesRepo;
    @Autowired
    private ModelosRepository modelosRepo;

    @Transactional(readOnly = true)
    public List<Materiales> mostrar() {
        return materialesRepo.findAll();
    }
    @Transactional
    public Materiales guardar(Materiales material) {
        if (materialesRepo.existsByNombre(material.getNombre())) {
            return null;
        }

        // Verificar si el modelo es válido
        if (material.getModelo() == null || material.getModelo().getId() == null) {
            throw new IllegalArgumentException("El modelo o su ID no pueden ser nulos.");
        }

        // Buscar el modelo existente por ID
        Modelos modelo = modelosRepo.findById(material.getModelo().getId())
            .orElseThrow(() -> new IllegalArgumentException("Modelo con ID " + material.getModelo().getId() + " no encontrado."));

        // Asignar el modelo cargado desde base
        material.setModelo(modelo);

        return materialesRepo.save(material);
    }


    @Transactional(readOnly = true)
    public List<Materiales> buscarPorModeloId(Integer modeloId) {
        return materialesRepo.findByModeloId(modeloId);
    }
    @Transactional(readOnly = true)
    public List<Materiales> buscarPorModelo (int modeloId) {
        return materialesRepo.findByModeloId(modeloId);
    }


    @Transactional(readOnly = true)
    public Materiales buscar(Integer id) {
        return materialesRepo.findById(id).orElse(null);
    }

@Transactional
public boolean editar(Materiales material) {
    Optional<Materiales> optMaterialExistente = materialesRepo.findById(material.getId());
    if (optMaterialExistente.isEmpty()) {
        return false;
    }

    Materiales materialExistente = optMaterialExistente.get();
    // Actualizar solo los campos que quieres modificar
    materialExistente.setNombre(material.getNombre());
    materialExistente.setDescripcion(material.getDescripcion());
    materialExistente.setUnidad(material.getUnidad());
    materialExistente.setCantidad(material.getCantidad());
    materialExistente.setPrecioUnitario(material.getPrecioUnitario());

    // Actualiza la relación Modelo solo si es válida
    if (material.getModelo() != null && material.getModelo().getId() != null) {
        // Opcional: buscar el modelo existente para mayor seguridad
        Modelos modeloExistente = modelosRepo.findById(material.getModelo().getId())
            .orElseThrow(() -> new IllegalArgumentException("Modelo con ID " + material.getModelo().getId() + " no encontrado."));
        materialExistente.setModelo(modeloExistente);
    }

    materialesRepo.save(materialExistente);
    return true;
}

    @Transactional
    public void eliminar(Integer id) {
        materialesRepo.deleteById(id);
    }
    @Transactional(readOnly = true)
    public boolean existeNombre(String nombre) {
        Materiales material = materialesRepo.findByNombre(nombre);
        return material != null;
    }

}
