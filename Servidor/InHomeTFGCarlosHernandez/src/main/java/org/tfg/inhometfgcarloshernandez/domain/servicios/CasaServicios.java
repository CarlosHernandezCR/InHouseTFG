package org.tfg.inhometfgcarloshernandez.domain.servicios;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tfg.inhometfgcarloshernandez.data.model.CasaEntity;
import org.tfg.inhometfgcarloshernandez.data.model.UsuarioEntity;
import org.tfg.inhometfgcarloshernandez.data.repositories.CasaRepository;
import org.tfg.inhometfgcarloshernandez.data.repositories.EstadosUsuariosRepository;
import org.tfg.inhometfgcarloshernandez.data.repositories.UsuarioRepository;
import org.tfg.inhometfgcarloshernandez.domain.errores.NotFoundException;
import org.tfg.inhometfgcarloshernandez.domain.model.mappers.CasaMapper;
import org.tfg.inhometfgcarloshernandez.spring.common.constantes.ConstantesError;
import org.tfg.inhometfgcarloshernandez.spring.model.CasaDetallesDTO;
import org.tfg.inhometfgcarloshernandez.spring.model.response.PantallaEstadosResponseDTO;
import org.tfg.inhometfgcarloshernandez.spring.model.response.UsuarioCasaDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CasaServicios {

    private final UsuarioRepository usuarioRepository;
    private final CasaRepository casaRepository;
    private final EstadosUsuariosRepository estadosUsuariosRepository;
    private final CasaMapper casaMapper;

    public PantallaEstadosResponseDTO getDatosPantallaEstados(String idUsuario, String idCasa) {
        UsuarioEntity usuario = usuarioRepository.findById(Integer.parseInt(idUsuario))
                .orElseThrow(() -> new NotFoundException(ConstantesError.ERROR_USUARIO_NO_ENCONTRADO + Integer.parseInt(idUsuario)));

        CasaEntity casa = casaRepository.findById(Integer.parseInt(idCasa))
                .orElseThrow(() -> new NotFoundException(ConstantesError.ERROR_CASA_NO_ENCONTRADA + usuario.getId()));

        List<UsuarioEntity> usuariosCasa = casaRepository.findUsuariosPorCasaId(casa.getId());

        List<String> estadosDisponibles = estadosUsuariosRepository.findEstadosUsuarioPorIdUsuario(Integer.parseInt(idUsuario));
        return mapearDatosUsuario(usuario, casa, usuariosCasa, estadosDisponibles);
    }

    private PantallaEstadosResponseDTO mapearDatosUsuario(UsuarioEntity usuario, CasaEntity casa, List<UsuarioEntity> usuariosCasa, List<String> estados) {
        return new PantallaEstadosResponseDTO(
                usuario.getEstado(),
                casa.getId(),
                casa.getNombre(),
                casa.getDireccion(),
                usuariosCasa.stream()
                        .filter(usuarioLogado -> !usuarioLogado.getId().equals(usuario.getId()))
                        .map(u -> new UsuarioCasaDTO(u.getNombre(), u.getEstado()))
                        .toList(),
                estados
        );
    }


    public ResponseEntity<Void> cambiarEstado(String estado, int id) {
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ConstantesError.ERROR_USUARIO_NO_ENCONTRADO + id));
        usuario.setEstado(estado);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok().build();
    }

    public List<CasaDetallesDTO> getCasas(String idUsuario) {
        return casaRepository.findCasasPorUsuarioId(Integer.parseInt(idUsuario))
                .stream()
                .map(casaMapper::casaEntityToCasaDetallesDTO)
                .toList();
    }
}
