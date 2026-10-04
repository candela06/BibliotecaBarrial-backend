package com.biblioteca.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.demo.entity.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long>{

    public boolean existsByIsbn(String isbn);
}
