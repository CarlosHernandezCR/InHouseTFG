package org.tfg.inhometfgcarloshernandez.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tfg.inhometfgcarloshernandez.domain.servicios.UsuarioServicios;
import org.tfg.inhometfgcarloshernandez.spring.common.constantes.ConstantesServer;
import org.tfg.inhometfgcarloshernandez.spring.model.request.LoginRequestDTO;
import org.tfg.inhometfgcarloshernandez.spring.model.request.RefreshTokenRequestDTO;
import org.tfg.inhometfgcarloshernandez.spring.model.response.AccessTokenResponseDTO;
import org.tfg.inhometfgcarloshernandez.spring.model.response.LoginResponseDTO;

@RestController
@RequestMapping({ConstantesServer.LOGINPATH})
public class LoginController {
    private final UsuarioServicios usuarioServicios;

    @Autowired
    public LoginController(UsuarioServicios usuarioServicios) {
        this.usuarioServicios = usuarioServicios;
    }

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(usuarioServicios.doLogin(loginRequestDTO.getIdentificador(), loginRequestDTO.getPassword()));
    }

    @PostMapping(ConstantesServer.REFRESH_TOKEN_PATH)
    public ResponseEntity<AccessTokenResponseDTO> refreshToken(@RequestBody RefreshTokenRequestDTO refreshToken) {
        String accessTokenResult = usuarioServicios.refreshTokens(refreshToken.getRefreshToken());
        return ResponseEntity.ok(new AccessTokenResponseDTO(accessTokenResult));
    }

}
