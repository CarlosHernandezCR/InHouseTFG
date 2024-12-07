package org.tfg.inhometfgcarloshernandez.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tfg.inhometfgcarloshernandez.data.model.MuebleEntity;

import java.util.List;

@Repository
public interface MueblesRepository  extends JpaRepository<MuebleEntity, Integer> {

    List<MuebleEntity> findByNombreHabitacion(String nombreHabitacion);

    MuebleEntity findByNombre(String nombreMueble);
}
