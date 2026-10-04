package com.biblioteca.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.demo.entity.Ejemplar;
import com.biblioteca.demo.repository.EjemplarRepository;
import com.biblioteca.demo.service.EjemplarService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping("/ejemplares")
public class EjemplarController {

    private final  EjemplarRepository ejemplarRepository;
    private final EjemplarService ejemplarService;

    EjemplarController(EjemplarRepository ejemplarRepository, EjemplarService ejemplarService ) {
        this.ejemplarRepository = ejemplarRepository;
        this.ejemplarService = ejemplarService;
    }

    // registrar un ejemplar
    @PostMapping
    public Ejemplar createEjemplar(@RequestBody Ejemplar ejemplar){
        return ejemplarService.createEjemplar(ejemplar);
    }

    @GetMapping
    public List<Ejemplar> getAllEjemplar(){
        return  ejemplarRepository.findAllByOrderByIdAsc();
    }

    @GetMapping("/{id}")
    public Ejemplar getEjemplarById(@PathVariable Long id){
        return ejemplarRepository.findById(id).orElseThrow(() -> new RuntimeException("Ejemplar not found"));
    }

    @PatchMapping("/{id}")
    public Ejemplar darDeBaja(@PathVariable Long id){
        return ejemplarService.darDeBaja(id);
    }

    @PatchMapping("/disponible/{id}")
    public Ejemplar setDisponible (@PathVariable Long id){
        return ejemplarService.setDisponible(id);
    }

    

}
