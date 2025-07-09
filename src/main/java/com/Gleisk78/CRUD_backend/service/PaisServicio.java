package com.Gleisk78.CRUD_backend.service;

import com.Gleisk78.CRUD_backend.entity.Pais;

import java.util.List;
import java.util.Optional;

public interface PaisServicio {

    List<Pais> getAllPaises();

    Optional<Pais> getPaisById(Long id);

    Pais createPais(Pais pais);

    Pais updatePais(Long id, Pais pais);

    void deletePais(Long id);
}