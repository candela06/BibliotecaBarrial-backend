package com.biblioteca.demo.service;

import org.springframework.stereotype.Service;

import com.biblioteca.demo.entity.Ejemplar;
import com.biblioteca.demo.entity.Estado;
import com.biblioteca.demo.repository.EjemplarRepository;
import com.biblioteca.demo.repository.LibroRepository;

@Service 
public class EjemplarService {
    private final EjemplarRepository ejemplarRepository;
    private final LibroRepository libroRepository;

    EjemplarService(EjemplarRepository ejemplarRepository, LibroRepository libroRepository){
        this.ejemplarRepository = ejemplarRepository;
        this.libroRepository = libroRepository;
    }

    public Ejemplar createEjemplar(Ejemplar ejemplar){
        Long libroId = ejemplar.getLibro().getId();
        if (!libroRepository.existsById(libroId)){
            throw new RuntimeException ("El libro no existe");
        }

        return ejemplarRepository.save(ejemplar);
    }

    public Ejemplar darDeBaja(Long id){
        Ejemplar ejemplar = ejemplarRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("El ejemplar no existe"));

        if (ejemplar.getEstado() == Estado.PRESTADO) {
            throw new RuntimeException("No se puede dar de baja un ejemplar prestado");
        }

        ejemplar.setEstado(Estado.BAJA);
        return ejemplarRepository.save(ejemplar);  
    }

    public Ejemplar setDisponible(Long idEjemplar){
        Ejemplar ejemplar = ejemplarRepository.findById(idEjemplar)
            .orElseThrow(() -> new RuntimeException("El ejemplar no existe"));

        if (ejemplar.getEstado() != Estado.BAJA) {
            throw new RuntimeException("Solo se puede poner disponible un ejemplar dado de baja");
        }
        
        ejemplar.setEstado(Estado.DISPONIBLE);
        return ejemplarRepository.save(ejemplar);
    }

    public Ejemplar setDisponiblePorDevolucion(Long idEjemplar){
        Ejemplar ejemplar = ejemplarRepository.findById(idEjemplar)
            .orElseThrow(() -> new RuntimeException("El ejemplar no existe"));

        if (ejemplar.getEstado() != Estado.PRESTADO) {
            throw new RuntimeException("Solo se puede devolver un ejemplar prestado");
        }

        ejemplar.setEstado(Estado.DISPONIBLE);
        return ejemplarRepository.save(ejemplar);
    }

}
