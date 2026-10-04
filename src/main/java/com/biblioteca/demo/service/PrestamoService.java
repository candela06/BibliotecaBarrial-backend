package com.biblioteca.demo.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.biblioteca.demo.entity.Ejemplar;
import com.biblioteca.demo.entity.Estado;
import com.biblioteca.demo.entity.Prestamo;
import com.biblioteca.demo.entity.Socio;
import com.biblioteca.demo.repository.EjemplarRepository;
import com.biblioteca.demo.repository.PrestamoRepository;
import com.biblioteca.demo.repository.SocioRepository;

@Service 
public class PrestamoService {

    private final EjemplarService ejemplarService;
    private final PrestamoRepository prestamoRepository;
    private final EjemplarRepository ejemplarRepository;
    private final SocioRepository socioRepository;

    PrestamoService(PrestamoRepository prestamoRepository, EjemplarRepository ejemplarRepository, SocioRepository socioRepository, EjemplarService ejemplarService){
        this.prestamoRepository = prestamoRepository;
        this.ejemplarRepository = ejemplarRepository;
        this.socioRepository = socioRepository;
        this.ejemplarService = ejemplarService;
    }

    public Prestamo createPrestamo(Ejemplar ejemplar, Socio socio){
        Prestamo prestamo = new Prestamo();
        prestamo.setSocio(socio);
        prestamo.setEjemplar(ejemplar);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaLimite(LocalDate.now().plusDays(14)); // 14 días de límite
        
        ejemplar.setEstado(Estado.PRESTADO);
        ejemplarRepository.save(ejemplar);
        return prestamo;
    }

    // realizar un prestamo
    public Prestamo realizarPrestamo(Prestamo prestamo){
        Ejemplar ejemplar = ejemplarRepository.findById(prestamo.getEjemplar().getId())
            .orElseThrow(() -> new RuntimeException("El ejemplar no existe"));
        
        Socio socio = socioRepository.findById(prestamo.getSocio().getId())
            .orElseThrow(() -> new RuntimeException("El socio no existe"));

        // Validar que el ejemplar esté disponible
        if (ejemplar.getEstado() == Estado.BAJA) {
            throw new RuntimeException("El ejemplar está en BAJA y no puede ser prestado");
        }
        
        if (ejemplar.getEstado() == Estado.PRESTADO) {
            throw new RuntimeException("El ejemplar ya está prestado");
        }

        // Verificar que el socio no tenga más de 3 préstamos activos
        long prestamoActivos = prestamoRepository.countBySocioIdAndFechaDevolucionNull(socio.getId());
        if (prestamoActivos >= 3) {
            throw new RuntimeException("El socio no puede tener más de 3 préstamos activos");
        }

        return prestamoRepository.save(createPrestamo(ejemplar, socio));
    }


    public Prestamo devolver(Long idPrestamo){
        Prestamo prestamo = prestamoRepository.findById(idPrestamo)
            .orElseThrow(() -> new RuntimeException("El prestamo no existe"));

        Ejemplar ejemplar = prestamo.getEjemplar();

        if (ejemplar.getEstado() != Estado.PRESTADO) {
            throw new RuntimeException("El ejemplar no está prestado");
        }

        if (prestamo.getFechaDevolucion() != null) {
            throw new RuntimeException("Este préstamo ya fue devuelto");
        }

        prestamo.setFechaDevolucion(LocalDate.now());
        ejemplarService.setDisponiblePorDevolucion(ejemplar.getId());

        return prestamoRepository.save(prestamo);
    }

    
}
