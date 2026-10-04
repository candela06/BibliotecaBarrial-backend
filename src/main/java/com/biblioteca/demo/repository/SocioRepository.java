package com.biblioteca.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.demo.entity.Socio;

public interface SocioRepository extends JpaRepository<Socio, Long> {

    public boolean existsByEmail(String email);

}
