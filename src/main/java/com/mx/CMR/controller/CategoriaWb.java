package com.mx.CMR.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mx.CMR.model.Categorias;
import com.mx.CMR.service.CategoriasServiceImp;

@RestController
@RequestMapping("CategoriasWebService")
@CrossOrigin
public class CategoriaWb {

    @Autowired
    private CategoriasServiceImp categoriasService;

    @GetMapping("mostrar")
    public List<Categorias> mostrar() {
        return categoriasService.mostrar();
    }
    @PostMapping("/guardar")
    public ResponseEntity<?> guardar(@RequestBody Categorias categoria) {
        Object respuesta = categoriasService.guardar(categoria);

        if (respuesta instanceof String) {
            if ("nombreYaExiste".equals(respuesta)) {
                return new ResponseEntity<>("El nombre de categoría ya existe, no se puede guardar", HttpStatus.CONFLICT);
            }
            // Aquí puedes manejar otros strings si quieres
        }

        // Si es objeto guardado, devuelve con CREATED y el objeto
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }


    @GetMapping("buscarPorNombre/{nombre}")
    public ResponseEntity<?> buscarPorNombre(@PathVariable("nombre") String nombre) {
        Categorias categoria = categoriasService.buscarPorNombre(nombre);
        if (categoria == null) {
            return new ResponseEntity<>("No se encontró la categoría", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categoria, HttpStatus.OK);
    }

    @PostMapping("buscar")
    public Categorias buscar(@RequestBody Categorias categoria) {
        return categoriasService.buscarXid(categoria.getId());
    }
    

    @PutMapping("editar")
    public ResponseEntity<?> editar(@RequestBody Categorias categoria) {
        boolean actualizado = categoriasService.editar(categoria);

        if (!actualizado) {
            return new ResponseEntity<>("No se puede editar: ID no existe o nombre duplicado", HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(categoria, HttpStatus.OK);
        }
    }

    @PostMapping("eliminar")
    public ResponseEntity<String> eliminar(@RequestBody Categorias categoria) {
        boolean eliminado = categoriasService.eliminar(categoria.getId());

        if (eliminado) {
            return new ResponseEntity<>("Categoría eliminada con éxito", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No existe esa categoría", HttpStatus.NOT_FOUND);
        }
    }
}
