package com.Gleisk78.CRUD_backend.service;

import com.Gleisk78.CRUD_backend.entity.Pais;
import com.Gleisk78.CRUD_backend.entity.Persona;
import com.Gleisk78.CRUD_backend.repository.PaisRepository;
import com.Gleisk78.CRUD_backend.repository.PersonaRepository;
import org.slf4j.Logger; // Importamos la interfaz de logger
import org.slf4j.LoggerFactory; // Importamos la fábrica para obtener el logger
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Marca esta clase como un componente de servicio de Spring
public class PersonaServicioImpl implements PersonaServicio {

    // Inicializamos el logger para esta clase, nos ayudará a ver qué está pasando en la lógica de negocio.
    private static final Logger logger = LoggerFactory.getLogger(PersonaServicioImpl.class);

    private final PersonaRepository personaRepository;
    private final PaisRepository paisRepository;

    @Autowired // Inyección de dependencias por constructor (práctica recomendada)
    public PersonaServicioImpl(PersonaRepository personaRepository, PaisRepository paisRepository) {
        this.personaRepository = personaRepository;
        this.paisRepository = paisRepository;
        logger.info("PersonaServicioImpl inicializado y listo para gestionar personas.");
    }

    @Override
    public List<Persona> getAllPersonas() {
        logger.info("Solicitando todas las personas desde el repositorio.");
        List<Persona> personas = (List<Persona>) personaRepository.findAll();
        logger.debug("Se recuperaron {} personas de la base de datos.", personas.size());
        return personas;
    }

    @Override
    public Optional<Persona> getPersonaById(Long id) {
        logger.info("Buscando persona con ID: {}.", id);
        Optional<Persona> persona = personaRepository.findById(id);
        if (persona.isPresent()) {
            logger.debug("Persona con ID {} encontrada: {}.", id, persona.get().getNombre()); // Asumiendo que Persona tiene getNombre()
        } else {
            logger.warn("Persona con ID {} no encontrada en el repositorio.", id);
        }
        return persona;
    }

    @Override
    public Persona createPersona(Persona persona) {
        logger.info("Iniciando creación de una nueva persona: {}.", persona.getNombre()); // Asumiendo que Persona tiene getNombre()

        // Verificamos que el país asociado no sea nulo antes de buscarlo.
        if (persona.getPais() == null || persona.getPais().getId() == null) {
            logger.error("El ID del país es nulo para la creación de la persona. Operación cancelada.");
            throw new RuntimeException("El ID del país no puede ser nulo para crear una persona.");
        }

        // Buscamos el país para asegurarnos de que existe antes de asociarlo a la persona.
        Optional<Pais> paisOptional = paisRepository.findById(persona.getPais().getId());
        if (paisOptional.isEmpty()) {
            logger.error("País con ID {} no encontrado al crear persona. Operación cancelada.", persona.getPais().getId());
            throw new RuntimeException("País con ID " + persona.getPais().getId() + " no encontrado.");
        }
        persona.setPais(paisOptional.get()); // Asignamos el objeto País completo

        Persona savedPersona = personaRepository.save(persona);
        logger.info("Persona '{}' guardada exitosamente con ID: {}.", savedPersona.getNombre(), savedPersona.getId()); // Asumiendo getNombre() y getId()
        return savedPersona;
    }

    @Override
    public Persona updatePersona(Long id, Persona persona) {
        logger.info("Intentando actualizar persona con ID: {}. Datos recibidos: {}.", id, persona.getNombre()); // Asumiendo getNombre()
        Optional<Persona> existingPersonaOptional = personaRepository.findById(id);

        if (existingPersonaOptional.isPresent()) {
            Persona existingPersona = existingPersonaOptional.get();
            logger.debug("Persona existente con ID {} encontrada. Preparando actualización de campos.", id);

            existingPersona.setNombre(persona.getNombre());
            existingPersona.setEdad(persona.getEdad()); // Actualizando campos

            // Verificamos y actualizamos el país asociado, si aplica.
            if (persona.getPais() == null || persona.getPais().getId() == null) {
                logger.error("El ID del país es nulo para la actualización de la persona con ID {}. Operación cancelada.", id);
                throw new RuntimeException("El ID del país no puede ser nulo para actualizar una persona.");
            }
            Optional<Pais> paisOptional = paisRepository.findById(persona.getPais().getId());
            if (paisOptional.isEmpty()) {
                logger.error("País con ID {} no encontrado al actualizar persona con ID {}. Operación cancelada.", persona.getPais().getId(), id);
                throw new RuntimeException("País con ID " + persona.getPais().getId() + " no encontrado para actualizar.");
            }
            existingPersona.setPais(paisOptional.get()); // Asignamos el objeto País completo

            Persona updatedPersona = personaRepository.save(existingPersona);
            logger.info("Persona con ID {} actualizada exitosamente.", updatedPersona.getId());
            return updatedPersona;
        } else {
            logger.error("Fallo al actualizar: Persona con ID {} no encontrada en la base de datos.", id);
            throw new RuntimeException("Persona con ID " + id + " no encontrada para actualizar.");
        }
    }

    @Override
    public void deletePersona(Long id) {
        logger.info("Intentando eliminar persona con ID: {}.", id);
        if (personaRepository.existsById(id)) {
            personaRepository.deleteById(id);
            logger.info("Persona con ID {} eliminada exitosamente del repositorio.", id);
        } else {
            logger.warn("No se pudo eliminar: Persona con ID {} no encontrada en la base de datos.", id);
            throw new RuntimeException("Persona con ID " + id + " no encontrada para eliminar.");
        }
    }
}
