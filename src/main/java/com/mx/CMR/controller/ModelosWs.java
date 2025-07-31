package com.mx.CMR.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.CMR.model.Categorias;
import com.mx.CMR.model.Modelos;
import com.mx.CMR.service.ModelosServiceImp;

@RestController
@RequestMapping("ModelosWebService")
@CrossOrigin
public class ModelosWs {

    @Autowired
    private ModelosServiceImp modelosService;

    @GetMapping("mostrar")
    public List<Modelos> mostrar() {
        return modelosService.mostrar();
    }

    @PostMapping("buscarPorCategoria")
    public List<Modelos> buscarPorCategoria(@RequestBody Categorias categoria) {
        return modelosService.buscarPorCategoria(categoria.getId());
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardar(@RequestBody Modelos modelo) {
        Modelos res = modelosService.guardar(modelo);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("buscarPorNombre/{nombre}")
    public ResponseEntity<?> buscarPorNombre(@PathVariable("nombre") String nombre) {
        Modelos modelo = modelosService.buscarPorNombre(nombre);
        if (modelo == null) {
            return new ResponseEntity<>("No se encontró el modelo", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(modelo, HttpStatus.OK);
    }

    @PostMapping("buscarPorId")
    public Modelos buscarPorId(@RequestBody Modelos modelo) {
        return modelosService.buscar(modelo.getId());
    }

    @PutMapping("editar")
    public ResponseEntity<?> editar(@RequestBody Modelos modelo) {
        boolean actualizado = modelosService.editar(modelo);
        if (!actualizado) {
            return new ResponseEntity<>("No se puede editar: ID no existe", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(modelo, HttpStatus.OK);
    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) {
        modelosService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
