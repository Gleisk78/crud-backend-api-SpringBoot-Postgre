package com.Gleisk78.CRUD_backend.repository;

import com.Gleisk78.CRUD_backend.entity.Pais; // Importa la entidad Pais
import org.springframework.data.jpa.repository.JpaRepository; // Usar JpaRepository para más métodos útiles
import org.springframework.stereotype.Repository;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {
    // JpaRepository ya te da métodos como findById, findAll, save, deleteById para Pais
}