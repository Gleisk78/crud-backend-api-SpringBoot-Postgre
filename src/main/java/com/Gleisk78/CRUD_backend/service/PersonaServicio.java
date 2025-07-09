package com.Gleisk78.CRUD_backend.service;

import com.Gleisk78.CRUD_backend.entity.Persona;

import java.util.List;
import java.util.Optional;

public interface PersonaServicio {

    List<Persona> getAllPersonas();

    Optional<Persona> getPersonaById(Long id);

    Persona createPersona(Persona persona);

    Persona updatePersona(Long id, Persona persona);

    void deletePersona(Long id);
}