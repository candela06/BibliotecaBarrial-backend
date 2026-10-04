package com.biblioteca.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.demo.entity.Ejemplar;

public interface EjemplarRepository extends JpaRepository<Ejemplar, Long>{
    List<Ejemplar> findAllByOrderByIdAsc();
}
