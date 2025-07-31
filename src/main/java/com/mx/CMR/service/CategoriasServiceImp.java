package com.mx.CMR.service;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mx.CMR.model.Categorias;
import com.mx.CMR.repository.CategoriasRepository;

@Service
public class CategoriasServiceImp {

    @Autowired
    private CategoriasRepository categoriasRepo;

    @Transactional(readOnly = true)
    public List<Categorias> mostrar() {
        return categoriasRepo.findAll();
    }
    @Transactional // Sin readOnly porque vas a modificar
    public Object guardar(Categorias categoria) {
        // Validar si ya existe nombre (no ID, porque es autoincremental)
        if (categoriasRepo.findByNombre(categoria.getNombre()) != null) {
            return "nombreYaExiste";
        }

        // Al guardar, el ID se genera automáticamente y la entidad queda con el ID nuevo
        Categorias categoriaGuardada = categoriasRepo.save(categoria);
        return categoriaGuardada;  // Devuelve el objeto con ID ya generado
    }

    @Transactional(readOnly = true)
    public Categorias buscarXid(Integer id) {
        return categoriasRepo.findById(id).orElse(null);
    }

    @Transactional
    public boolean editar(Categorias categoria) {
        Optional<Categorias> optCategoriaExistente = categoriasRepo.findById(categoria.getId());
        if (optCategoriaExistente.isEmpty()) {
            return false;
        }

        Categorias categoriaExistente = optCategoriaExistente.get();
        categoriaExistente.setNombre(categoria.getNombre());
        categoriasRepo.save(categoriaExistente);
        return true;
    }


    @Transactional(readOnly = true)
    public Categorias buscarPorNombre(String nombre) {
        return categoriasRepo.findByNombre(nombre);
    }
    @Transactional
    public boolean eliminar(Integer id) {
        if (!categoriasRepo.existsById(id)) {
            return false;
        }
        categoriasRepo.deleteById(id);
        return true;
    }
}
