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
public class PaisServicioImpl implements PaisServicio {

    // Inicializamos el logger para esta clase, nos ayudará a ver qué está pasando en la lógica de negocio.
    private static final Logger logger = LoggerFactory.getLogger(PaisServicioImpl.class);

    private final PaisRepository paisRepository;
    private final PersonaRepository personaRepository; // Se inyecta para verificar referencias antes de eliminar

    @Autowired // Inyección de dependencias por constructor (práctica recomendada)
    public PaisServicioImpl(PaisRepository paisRepository, PersonaRepository personaRepository) {
        this.paisRepository = paisRepository;
        this.personaRepository = personaRepository;
        logger.info("PaisServicioImpl inicializado y listo para gestionar países.");
    }

    @Override
    public List<Pais> getAllPaises() {
        logger.info("Solicitando todos los países desde el repositorio.");
        List<Pais> paises = paisRepository.findAll();
        logger.debug("Se recuperaron {} países de la base de datos.", paises.size());
        return paises;
    }

    @Override
    public Optional<Pais> getPaisById(Long id) {
        logger.info("Buscando país con ID: {}.", id);
        Optional<Pais> pais = paisRepository.findById(id);
        if (pais.isPresent()) {
            logger.debug("País con ID {} encontrado: {}.", id, pais.get().getNombre());
        } else {
            logger.warn("País con ID {} no encontrado en el repositorio.", id);
        }
        return pais;
    }

    @Override
    public Pais createPais(Pais pais) {
        logger.info("Iniciando creación de un nuevo país: {}.", pais.getNombre());
        // Aquí podrías añadir más lógica de validación antes de guardar
        Pais savedPais = paisRepository.save(pais);
        logger.info("País '{}' guardado exitosamente con ID: {}.", savedPais.getNombre(), savedPais.getId());
        return savedPais;
    }

    @Override
    public Pais updatePais(Long id, Pais pais) {
        logger.info("Intentando actualizar país con ID: {}. Datos recibidos: {}.", id, pais.getNombre());
        Optional<Pais> existingPaisOptional = paisRepository.findById(id);

        if (existingPaisOptional.isPresent()) {
            Pais existingPais = existingPaisOptional.get();
            logger.debug("País existente con ID {} encontrado. Preparando actualización.", id);

            existingPais.setNombre(pais.getNombre()); // Actualiza el nombre

            Pais updatedPais = paisRepository.save(existingPais);
            logger.info("País con ID {} actualizado exitosamente a: {}.", updatedPais.getId(), updatedPais.getNombre());
            return updatedPais;
        } else {
            logger.error("Fallo al actualizar: País con ID {} no encontrado en la base de datos.", id);
            throw new RuntimeException("País con ID " + id + " no encontrado para actualizar.");
        }
    }

    @Override
    public void deletePais(Long id) {
        logger.info("Intentando eliminar país con ID: {}.", id);

        // Lógica clave: verificar si hay personas asociadas antes de eliminar el país.
        List<Persona> personasAsociadas = personaRepository.findByPaisId(id);

        if (!personasAsociadas.isEmpty()) {
            logger.warn("No se puede eliminar el país con ID {} porque tiene {} personas asociadas. Operación cancelada.", id, personasAsociadas.size());
            throw new RuntimeException("No se puede eliminar el país con ID " + id + " porque tiene personas asociadas. Elimina las personas primero.");
        }

        // Si no hay personas asociadas, procedemos a verificar la existencia y eliminar.
        if (paisRepository.existsById(id)) {
            paisRepository.deleteById(id);
            logger.info("País con ID {} eliminado exitosamente del repositorio.", id);
        } else {
            logger.warn("No se pudo eliminar: País con ID {} no encontrado en la base de datos.", id);
            throw new RuntimeException("País con ID " + id + " no encontrado para eliminar.");
        }
    }
}