package org.tfg.athometfgcarloshernandez.spring.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VotarRequestDTO {
    private int idUsuario;
    private int idEvento;

}
