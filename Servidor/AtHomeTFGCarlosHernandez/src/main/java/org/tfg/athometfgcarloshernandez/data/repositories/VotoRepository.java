package org.tfg.athometfgcarloshernandez.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tfg.athometfgcarloshernandez.data.model.EventoEntity;
import org.tfg.athometfgcarloshernandez.data.model.VotoEntity;

import java.util.Optional;

@Repository
public interface VotoRepository extends JpaRepository<VotoEntity, Integer> {
    Optional<VotoEntity> findByEvento(EventoEntity evento);
}
