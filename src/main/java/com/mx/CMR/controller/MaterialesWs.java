package com.mx.CMR.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mx.CMR.model.Materiales;
import com.mx.CMR.service.MaterialesServiceImp;

@RestController
@RequestMapping("MaterialesWebService")
@CrossOrigin
public class MaterialesWs {

    @Autowired
    private MaterialesServiceImp materialesService;

    @GetMapping("mostrar")
    public List<Materiales> mostrar() {
        return materialesService.mostrar();
    }
    @PostMapping("guardar")
    public ResponseEntity<?> guardar(@RequestBody Materiales material) {
        // Guardar y obtener el material guardado
        Materiales materialGuardado = materialesService.guardar(material);

        if (materialGuardado == null) {
            // Aquí puedes manejar error, nombre duplicado, etc.
            return new ResponseEntity<>("El nombre ya existe, no se puede guardar", HttpStatus.CONFLICT);
        }

        // Retorna el objeto guardado con status 201 y JSON válido
        return new ResponseEntity<>(materialGuardado, HttpStatus.CREATED);
    }



    @PostMapping("buscarPorId")
    public ResponseEntity<?> buscarPorId(@RequestBody Materiales material) {
        Materiales encontrado = materialesService.buscar(material.getId());
        if (encontrado == null) {
            return new ResponseEntity<>("No se encontró el material", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(encontrado, HttpStatus.OK);
    }

    @PutMapping("editar")
    public ResponseEntity<?> editar(@RequestBody Materiales material) {
        boolean actualizado = materialesService.editar(material);
        if (!actualizado) {
            return new ResponseEntity<>("No se puede editar: ID no existe o nombre duplicado", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(material, HttpStatus.OK);
    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) {
        materialesService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("buscarPorModelo")
    public List<Materiales> buscarPorModelo(@RequestBody Materiales material) {
        return materialesService.buscarPorModelo(material.getModelo().getId());
    }
    

}
