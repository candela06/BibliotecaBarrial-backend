package com.biblioteca.demo.controller;

import com.biblioteca.demo.service.LibroService;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.demo.entity.Libro;
import com.biblioteca.demo.repository.LibroRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController 
@RequestMapping("/libros")
public class LibroController {

    private final LibroService libroService;
    private final LibroRepository libroRepository;

    LibroController(LibroRepository libroRepository, LibroService libroService) {
        this.libroRepository = libroRepository;
        this.libroService = libroService;
    }

    // registrar Libro
    @PostMapping
    public Libro createLibro(@RequestBody Libro libro){
        return libroService.createLibro(libro);
    }


    // catalogo completo de libros
    @GetMapping
    public List<Libro> getAllLibros(){
        return libroRepository.findAll();
    }

    // obtener un libro por su ID
    @GetMapping("/{id}")
    public Libro getLibroById(@PathVariable Long id){
        return libroRepository.findById(id).orElseThrow(() -> new RuntimeException("Libro " + id + " not found"));
    }

    // actualizar un libro
    @PutMapping("/{id}")
    public Libro updateLibro(@PathVariable Long id, @RequestBody Libro libroDetails){
        return libroService.updateLibro(id, libroDetails);
    }

    // eliminar un libro
    @DeleteMapping("/{id}")
    public boolean deleteLibro(@PathVariable Long id){
        Libro libro = libroRepository.findById(id).orElseThrow(() -> new RuntimeException("Libro " + id + " not found"));

        libroRepository.delete(libro);
        return true;
    }

}
