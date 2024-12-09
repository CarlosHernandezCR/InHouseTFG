package org.tfg.inhometfgcarloshernandez.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tfg.inhometfgcarloshernandez.common.constantes.Constantes;
import org.tfg.inhometfgcarloshernandez.domain.servicios.UsuarioServicios;
import org.tfg.inhometfgcarloshernandez.spring.common.constantes.ConstantesServer;
import org.tfg.inhometfgcarloshernandez.spring.model.request.RegistroRequestDTO;

@RestController
@RequestMapping({ConstantesServer.REGISTERPATH})
public class RegistroController {
    private final UsuarioServicios usuarioServicios;

    @Autowired
    public RegistroController(UsuarioServicios usuarioServicios) {
        this.usuarioServicios = usuarioServicios;
    }

    @PostMapping
    public ResponseEntity<Void> registro(@RequestBody RegistroRequestDTO registroRequestDTO) {
        usuarioServicios.registro(registroRequestDTO.getNombre(), registroRequestDTO.getPassword(),  registroRequestDTO.getCorreo(),registroRequestDTO.getTelefono());
        return ResponseEntity.ok().build();
    }

    @GetMapping(ConstantesServer.VALIDAR_USUARIO)
    public ResponseEntity<String> validarUsuario(@RequestParam(ConstantesServer.CODIGO) String codigoValidacion) {
        usuarioServicios.validateUser(codigoValidacion);
        return new ResponseEntity<>(Constantes.USUARIO_VALIDADO, HttpStatus.OK);
    }

}
