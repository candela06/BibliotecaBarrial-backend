package com.biblioteca.demo.controller;
import com.biblioteca.demo.repository.SocioRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.demo.entity.Socio;
import com.biblioteca.demo.service.SocioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController 
@RequestMapping("/socios")
public class SocioController {
    
    private final SocioRepository socioRepository;
    private final SocioService socioService;

    SocioController(SocioService socioService, SocioRepository socioRepository) {
        this.socioService = socioService;
        this.socioRepository = socioRepository;
    }

    // registrar un nuevo socio
    @PostMapping
    public Socio registrarSocio(@RequestBody Socio socio){
        return socioService.registrarSocio(socio);
    }

    // consultar socio por id
    @GetMapping("/{id}")
    public Socio getSocioById(@PathVariable Long id){
        return socioRepository.findById(id).orElseThrow(() -> new RuntimeException("Socio " + id + " not found"));
    }

    // consultar todos los socios
    @GetMapping 
    public List<Socio> getAllSocios(){
        return socioRepository.findAll();
    }
    
    
    

}
