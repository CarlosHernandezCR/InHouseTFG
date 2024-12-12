package com.example.inhometfgandroidcarloshernandez.domain.usecases.estados

import com.example.inhometfgandroidcarloshernandez.data.repositories.UsuarioRepository
import javax.inject.Inject

class ChangeStateUseCase @Inject constructor(
    private val usuarioRepository: UsuarioRepository
){
    operator fun invoke(estado: String, idCasa:String, idUsuario: String) = usuarioRepository.cambiarEstado(estado,idCasa,idUsuario)
}