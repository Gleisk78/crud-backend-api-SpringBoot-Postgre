package com.Gleisk78.CRUD_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "paises") // Mapea a la tabla 'paises' en tu BD
@Data // Genera getters, setters, toString, equals, hashCode (de Lombok)
@NoArgsConstructor // Genera constructor sin argumentos
@AllArgsConstructor // Genera constructor con todos los argumentos
@Builder // Permite construir objetos de forma fluida (ej. Pais.builder().nombre("Chile").build())
public class Pais {

    @Id // Marca esta columna como la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática de IDs por la BD (ej. AUTO_INCREMENT)
    @Column(name = "id_pais") // Nombre exacto de la columna en la BD
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true, length = 100) // Nombre de la columna, no nulo, único, longitud
    private String nombre;
}