package com.Gleisk78.CRUD_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn; // Para la clave foránea
import jakarta.persistence.ManyToOne; // Para la relación Many-to-One
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "personas") // Mapea a la tabla 'personas' en tu BD
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona") // Nombre exacto de la columna en la BD
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "edad")
    private Integer edad;

    // Relación Many-to-One con la entidad Pais
    @ManyToOne // Muchas Personas pueden pertenecer a Un solo País
    @JoinColumn(name = "id_pais", nullable = false) // 'id_pais' es la columna FK en la tabla 'personas'
    // nullable = false significa que una persona SIEMPRE debe tener un país
    private Pais pais; // La clave foránea se mapea a un objeto Pais
}