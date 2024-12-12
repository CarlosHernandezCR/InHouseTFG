package com.example.inhometfgandroidcarloshernandez.domain.usecases.seleccionarcasa

import com.example.inhometfgandroidcarloshernandez.data.repositories.CasaRepository
import javax.inject.Inject

class UnirseCasaUseCase @Inject constructor(
    private val casaRepository: CasaRepository
){
    operator fun invoke(idUsuario: String, codigoInvitacion: String) = casaRepository.unirseCasa(idUsuario, codigoInvitacion)
}