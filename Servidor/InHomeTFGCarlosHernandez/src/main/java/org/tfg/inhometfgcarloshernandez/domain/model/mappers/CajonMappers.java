package org.tfg.inhometfgcarloshernandez.domain.model.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.tfg.inhometfgcarloshernandez.common.constantes.Constantes;
import org.tfg.inhometfgcarloshernandez.data.model.CajonEntity;
import org.tfg.inhometfgcarloshernandez.data.model.UsuarioEntity;
import org.tfg.inhometfgcarloshernandez.spring.model.CajonDTO;

@Mapper(componentModel = Constantes.SPRING)
public interface CajonMappers {

    @Mapping(target = "propietario", source = "propietario", qualifiedByName = "usuarioEntityToString")
    CajonDTO cajonEntityToCajonDTO(CajonEntity cajonEntity);

    @Named("usuarioEntityToString")
    default String usuarioEntityToString(UsuarioEntity usuarioEntity) {
        return usuarioEntity != null ? usuarioEntity.getNombre() : null;
    }

}
