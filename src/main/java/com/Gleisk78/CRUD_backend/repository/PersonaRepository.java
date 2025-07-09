package com.Gleisk78.CRUD_backend.repository;

import com.Gleisk78.CRUD_backend.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // Necesario para el método findByPaisId

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

    // Método personalizado: Spring Data JPA lo implementa automáticamente
    // Busca todas las personas que tienen un determinado ID de país
    List<Persona> findByPaisId(Long paisId);
}