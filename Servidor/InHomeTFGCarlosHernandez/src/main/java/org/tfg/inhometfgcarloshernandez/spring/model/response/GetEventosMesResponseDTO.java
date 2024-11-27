package org.tfg.inhometfgcarloshernandez.spring.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetEventosMesResponseDTO {
    private List<Integer> diasConEvento;
}
