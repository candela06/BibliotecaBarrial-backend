package com.biblioteca.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.demo.entity.Prestamo;
import com.biblioteca.demo.repository.PrestamoRepository;
import com.biblioteca.demo.service.PrestamoService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;





@RestController 
@RequestMapping("/prestamos")
public class PrestamoController {
    private final PrestamoRepository prestamoRepository;
    private final PrestamoService prestamoService;

    PrestamoController(PrestamoRepository prestamoRepository, PrestamoService prestamoService){
        this.prestamoRepository = prestamoRepository;
        this.prestamoService = prestamoService;
    }

    // registrar prestamo
    @PostMapping()
    public Prestamo realizarPrestamo(@RequestBody Prestamo prestamo){
        return prestamoService.realizarPrestamo(prestamo);
    }

    // ver todos los prestamos

    @GetMapping
    public List<Prestamo> getPrestamos(){
        return prestamoRepository.findAllByOrderByIdAsc();
    }
    
    // todos los prestamos activos del socio
    @GetMapping("/{idSocio}")
    public List<Prestamo> getPrestamosActivosDeSocio(@PathVariable Long idSocio) {
        return prestamoRepository.findAllBySocioIdAndFechaDevolucionNull(idSocio);
    }
    
    // devolver prestamo
    @PatchMapping("{idPrestamo}")
    public Prestamo devolver(@PathVariable Long idPrestamo){
        return prestamoService.devolver(idPrestamo);
    }

    @GetMapping("activos")
    public List<Prestamo> gPrestamosActivos(){
        return prestamoRepository.findAllByFechaDevolucionNullOrderByIdAsc();
    }
    

}
