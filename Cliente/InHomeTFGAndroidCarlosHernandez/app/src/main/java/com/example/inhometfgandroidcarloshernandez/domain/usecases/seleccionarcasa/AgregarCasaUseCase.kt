package com.example.inhometfgandroidcarloshernandez.domain.usecases.seleccionarcasa

import com.example.inhometfgandroidcarloshernandez.data.repositories.CasaRepository
import javax.inject.Inject

class AgregarCasaUseCase @Inject constructor(
    private val casaRepository: CasaRepository
){
    operator fun invoke( idUsuario: String,nombre: String, direccion: String, codigoPostal: String) = casaRepository.agregarCasa(idUsuario, nombre, direccion, codigoPostal)
}