package com.Gleisk78.CRUD_backend.controller;

import com.Gleisk78.CRUD_backend.entity.Pais;
import com.Gleisk78.CRUD_backend.service.PaisServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger; // Importamos la interfaz de logger
import org.slf4j.LoggerFactory; // Importamos la fábrica para obtener el logger

import java.util.List;
import java.util.Optional;

@CrossOrigin(maxAge = 3600) // Permite peticiones desde otros dominios (ej. tu frontend)
@RestController // Indica que es un controlador REST
@RequestMapping("/api/v1/paises") // Prefijo de URL para todos los endpoints de País
public class PaisController {

    // Inicializamos el logger para esta clase. Nos ayudará a ver qué está sucediendo.
    private static final Logger logger = LoggerFactory.getLogger(PaisController.class);

    private final PaisServicio paisServicio; // Inyección de dependencia

    @Autowired
    public PaisController(PaisServicio paisServicio) {
        this.paisServicio = paisServicio;
        logger.info("PaisController inicializado y listo para manejar peticiones.");
    }

    // GET: Obtener todos los países
    // Endpoint: GET /api/v1/paises
    @GetMapping
    public ResponseEntity<List<Pais>> getAllPaises() {
        logger.info("Recibida petición GET para obtener todos los países.");
        List<Pais> paises = paisServicio.getAllPaises();
        logger.debug("Se encontraron {} países en la base de datos.", paises.size());
        return ResponseEntity.ok(paises); // Retorna 200 OK con la lista de países
    }

    // GET: Obtener un país por ID
    // Endpoint: GET /api/v1/paises/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Pais> getPaisById(@PathVariable("id") Long id) {
        logger.info("Recibida petición GET para obtener país con ID: {}.", id);
        Optional<Pais> pais = paisServicio.getPaisById(id);
        return pais.map(p -> {
            logger.info("País con ID {} encontrado.", id);
            return ResponseEntity.ok(p); // Si el país existe, retorna 200 OK
        }).orElseGet(() -> {
            logger.warn("País con ID {} no encontrado.", id);
            return ResponseEntity.notFound().build(); // Si no, 404 Not Found
        });
    }

    // POST: Crear un nuevo país
    // Endpoint: POST /api/v1/paises
    @PostMapping
    public ResponseEntity<Pais> createPais(@RequestBody Pais pais) {
        logger.info("Recibida petición POST para crear un nuevo país.");
        Pais nuevoPais = paisServicio.createPais(pais);
        logger.info("País creado exitosamente con ID: {}.", nuevoPais.getId());
        return new ResponseEntity<>(nuevoPais, HttpStatus.CREATED); // Retorna 201 Created
    }

    // PUT: Actualizar un país existente
    // Endpoint: PUT /api/v1/paises/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Pais> updatePais(@PathVariable("id") Long id, @RequestBody Pais pais) {
        logger.info("Recibida petición PUT para actualizar país con ID: {}.", id);
        try {
            Pais updatedPais = paisServicio.updatePais(id, pais);
            logger.info("País con ID {} actualizado exitosamente.", id);
            return ResponseEntity.ok(updatedPais); // Retorna 200 OK
        } catch (RuntimeException e) {
            // Asume que la excepción indica que no se encontró el país
            logger.error("Error al actualizar país con ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found
        }
    }

    // DELETE: Eliminar un país por ID
    // Endpoint: DELETE /api/v1/paises/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePais(@PathVariable("id") Long id) {
        logger.info("Recibida petición DELETE para eliminar país con ID: {}.", id);
        try {
            paisServicio.deletePais(id);
            logger.info("País con ID {} eliminado exitosamente.", id);
            return ResponseEntity.ok("País con ID " + id + " eliminado exitosamente."); // Retorna 200 OK
        } catch (RuntimeException e) {
            // Si el mensaje indica que hay personas asociadas, retorna 409 Conflict.
            // Si no (ej. país no encontrado), retorna 404 Not Found.
            HttpStatus status = e.getMessage().contains("asociadas") ? HttpStatus.CONFLICT : HttpStatus.NOT_FOUND;
            logger.error("Error al eliminar país con ID {}. Causa: {}. Status HTTP: {}", id, e.getMessage(), status.value());
            return new ResponseEntity<>(e.getMessage(), status);
        }
    }
}
