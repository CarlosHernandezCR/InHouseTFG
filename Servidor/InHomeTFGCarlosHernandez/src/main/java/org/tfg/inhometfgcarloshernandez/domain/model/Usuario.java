package org.tfg.inhometfgcarloshernandez.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario{
    private Integer id;
    private String nombre;
    private String correo;
    private String telefono;
    private String estado;
}