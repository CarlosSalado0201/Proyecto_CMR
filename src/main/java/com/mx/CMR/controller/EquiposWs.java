package com.mx.CMR.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mx.CMR.model.Categorias;
import com.mx.CMR.model.Equipos;
import com.mx.CMR.service.EquiposServiceImp;

@RestController
@RequestMapping("EquiposWebService")
@CrossOrigin
public class EquiposWs {

    @Autowired
    private EquiposServiceImp equiposService;

    @GetMapping("/mostrar")
    public List<Equipos> mostrar() {
        return equiposService.mostrar();
    }

    @PostMapping("/buscarPorCategoria")
    public List<Equipos> buscarPorCategoria(@RequestBody Categorias categoria) {
        return equiposService.buscarPorCategoria(categoria.getId());
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardar(@RequestBody Equipos equipo) {
        String respuesta = equiposService.guardar(equipo);

        if ("equipoYaExiste".equals(respuesta)) {
            return new ResponseEntity<>("El equipo ya existe, no se puede guardar", HttpStatus.CONFLICT);
        }

        // Puedes devolver el equipo guardado si quieres (se puede cambiar el servicio para devolverlo)
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    // Cambié a GET y con PathVariable para buscar por id (no por nombre, que no existe)
    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Equipos equipo = equiposService.buscar(id);
        if (equipo == null) {
            return new ResponseEntity<>("No se encontró el equipo", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(equipo, HttpStatus.OK);
    }

    // Si quieres buscar por marca y modelo (no nombre), mejor define un DTO o busca por parámetros
    // Aquí te dejo una idea para buscar por marca y modelo vía POST con JSON
    @PostMapping("/buscarPorMarcaYModelo")
    public ResponseEntity<?> buscarPorMarcaYModelo(@RequestBody Equipos equipo) {
        Equipos resultado = equiposService.buscarPorMarcaYModelo(equipo.getMarca(), equipo.getModelo());
        if (resultado == null) {
            return new ResponseEntity<>("No se encontró el equipo", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    @PutMapping("/editar")
    public ResponseEntity<?> editar(@RequestBody Equipos equipo) {
        boolean actualizado = equiposService.editar(equipo);
        if (!actualizado) {
            return new ResponseEntity<>("No se puede editar: ID no existe", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(equipo, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) {
        equiposService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
