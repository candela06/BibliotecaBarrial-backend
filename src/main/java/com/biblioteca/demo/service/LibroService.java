package com.biblioteca.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblioteca.demo.entity.Libro;
import com.biblioteca.demo.repository.LibroRepository;

@Service 
public class LibroService {

    private final LibroRepository libroRepository;

    LibroService(LibroRepository libroRepository){
        this.libroRepository = libroRepository;
    }

    public Libro createLibro(Libro libro){
        if (libroRepository.existsByIsbn(libro.getIsbn())) {
            throw new RuntimeException("El ISBN ya está en uso");
        }

        return libroRepository.save(libro);
    }

    @Transactional
public Libro updateLibro(Long id, Libro libroDetails){

    Libro libro = libroRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Libro " + id + " not found"));

    if (libroDetails.getTitulo() == null || libroDetails.getTitulo().trim().isEmpty()) {
        throw new RuntimeException("El título no puede estar vacío");
    }
    if (libroDetails.getAutor() == null || libroDetails.getAutor().trim().isEmpty()) {
        throw new RuntimeException("El autor no puede estar vacío");
    }
    if (libroDetails.getIsbn() == null || libroDetails.getIsbn().trim().isEmpty()) {
        throw new RuntimeException("El ISBN no puede estar vacío");
    }

    // Validar ISBN único (si cambió)
    if (!libroDetails.getIsbn().equals(libro.getIsbn())) {
        if (libroRepository.existsByIsbn(libroDetails.getIsbn())) {
            throw new RuntimeException("El ISBN ya está en uso");
        }
        libro.setIsbn(libroDetails.getIsbn().trim());
    }

    libro.setTitulo(libroDetails.getTitulo().trim());
    libro.setAutor(libroDetails.getAutor().trim());

    return libroRepository.save(libro);
}
}
