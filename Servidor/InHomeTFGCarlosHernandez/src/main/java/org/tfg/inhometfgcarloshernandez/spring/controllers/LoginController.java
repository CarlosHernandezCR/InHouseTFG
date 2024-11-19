//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.tfg.inhometfgcarloshernandez.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tfg.inhometfgcarloshernandez.domain.modelo.Usuario;
import org.tfg.inhometfgcarloshernandez.domain.modelo.mappers.UsuarioMappers;
import org.tfg.inhometfgcarloshernandez.domain.servicios.UsuarioServicios;
import org.tfg.inhometfgcarloshernandez.spring.common.constantes.ConstantesServer;
import org.tfg.inhometfgcarloshernandez.spring.model.request.LoginRequestDTO;
import org.tfg.inhometfgcarloshernandez.spring.model.response.LoginResponseDTO;

@RestController
@RequestMapping({ConstantesServer.LOGINPATH})
public class LoginController {
    private final UsuarioServicios usuarioService;

    @Autowired
    public LoginController(UsuarioServicios usuarioServicios) {
        this.usuarioService = usuarioServicios;
    }

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        Usuario usuario = this.usuarioService.findByEmail(loginRequest.getCorreo());
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            LoginResponseDTO responseDTO = new LoginResponseDTO(usuario.getEstado());
            return ResponseEntity.ok(responseDTO);
        }
    }
}