package com.biblioteca.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.demo.entity.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long>{
    
    long countBySocioIdAndFechaDevolucionNull(Long socioId);
    List<Prestamo> findAllBySocioIdAndFechaDevolucionNull(Long idSocio);
    List<Prestamo> findAllByFechaDevolucionNull();
     List<Prestamo> findAllByOrderByIdAsc();
     List<Prestamo> findAllByFechaDevolucionNullOrderByIdAsc();
}
