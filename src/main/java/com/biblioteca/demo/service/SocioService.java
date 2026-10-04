package com.biblioteca.demo.service;
import org.springframework.stereotype.Service;

import com.biblioteca.demo.entity.Socio;
import com.biblioteca.demo.repository.SocioRepository;

@Service 
public class SocioService {

    private final SocioRepository socioRepository;

    SocioService(SocioRepository socioRepository) {
        this.socioRepository = socioRepository;
    }

    public Socio registrarSocio(Socio socio){
        if (socioRepository.existsByEmail(socio.getEmail())) {
            throw new RuntimeException("El email ya está en uso");
        }

        return socioRepository.save(socio);
    }
}   
