package com.Gleisk78.CRUD_backend.controller;

import com.Gleisk78.CRUD_backend.entity.Persona;
import com.Gleisk78.CRUD_backend.service.PersonaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger; // Importamos la interfaz de logger
import org.slf4j.LoggerFactory; // Importamos la fábrica para obtener el logger

import java.util.List;
import java.util.Optional;

@CrossOrigin(maxAge = 3600) // Permite peticiones desde tu frontend (ej. Angular)
@RestController // Indica que es un controlador REST
@RequestMapping("/api/v1/personas") // Prefijo de URL para todos los endpoints de Persona
public class PersonaController {

    // Inicializamos el logger para esta clase. Nos ayudará a ver qué está sucediendo.
    private static final Logger logger = LoggerFactory.getLogger(PersonaController.class);

    private final PersonaServicio personaServicio; // Inyección de dependencia

    @Autowired
    public PersonaController(PersonaServicio personaServicio) {
        this.personaServicio = personaServicio;
        logger.info("PersonaController inicializado y listo para manejar peticiones.");
    }

    // GET: Obtener todas las personas
    // Endpoint: GET /api/v1/personas
    @GetMapping
    public ResponseEntity<List<Persona>> getAllPersonas() {
        logger.info("Recibida petición GET para obtener todas las personas.");
        List<Persona> personas = personaServicio.getAllPersonas();
        logger.debug("Se encontraron {} personas en la base de datos.", personas.size());
        return ResponseEntity.ok(personas); // Retorna 200 OK con la lista de personas
    }

    // GET: Obtener una persona por ID
    // Endpoint: GET /api/v1/personas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Persona> getPersonaById(@PathVariable("id") Long id) {
        logger.info("Recibida petición GET para obtener persona con ID: {}.", id);
        Optional<Persona> persona = personaServicio.getPersonaById(id);
        return persona.map(p -> {
            logger.info("Persona con ID {} encontrada.", id);
            return ResponseEntity.ok(p); // Si la persona existe, retorna 200 OK
        }).orElseGet(() -> {
            logger.warn("Persona con ID {} no encontrada.", id);
            return ResponseEntity.notFound().build(); // Si no, 404 Not Found
        });
    }

    // POST: Crear una nueva persona
    // Endpoint: POST /api/v1/personas
    @PostMapping
    public ResponseEntity<Persona> createPersona(@RequestBody Persona persona) {
        logger.info("Recibida petición POST para crear una nueva persona.");
        // @RequestBody mapea el JSON del cuerpo de la petición a un objeto Persona
        Persona nuevaPersona = personaServicio.createPersona(persona);
        logger.info("Persona creada exitosamente con ID: {}.", nuevaPersona.getId());
        return new ResponseEntity<>(nuevaPersona, HttpStatus.CREATED); // Retorna 201 Created
    }

    // PUT: Actualizar una persona existente
    // Endpoint: PUT /api/v1/personas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Persona> updatePersona(@PathVariable("id") Long id, @RequestBody Persona persona) {
        logger.info("Recibida petición PUT para actualizar persona con ID: {}.", id);
        try {
            Persona updatedPersona = personaServicio.updatePersona(id, persona);
            logger.info("Persona con ID {} actualizada exitosamente.", id);
            return ResponseEntity.ok(updatedPersona); // Retorna 200 OK
        } catch (RuntimeException e) {
            // Asume que la excepción indica que no se encontró la persona
            logger.error("Error al actualizar persona con ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found
        }
    }

    // DELETE: Eliminar una persona por ID
    // Endpoint: DELETE /api/v1/personas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePersona(@PathVariable("id") Long id) {
        logger.info("Recibida petición DELETE para eliminar persona con ID: {}.", id);
        try {
            personaServicio.deletePersona(id);
            logger.info("Persona con ID {} eliminada exitosamente.", id);
            return ResponseEntity.ok("Persona con ID " + id + " eliminada exitosamente."); // Retorna 200 OK
        } catch (RuntimeException e) {
            // Asume que la excepción indica que no se encontró la persona
            logger.error("Error al eliminar persona con ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND); // Retorna 404 Not Found
        }
    }
}
