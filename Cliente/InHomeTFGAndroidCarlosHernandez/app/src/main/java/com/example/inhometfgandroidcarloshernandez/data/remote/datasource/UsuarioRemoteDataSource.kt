package com.example.inhometfgandroidcarloshernandez.data.remote.datasource

import com.example.inhometfgandroidcarloshernandez.data.model.request.LoginRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.LoginResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.remote.apiServices.UsuarioService
import com.example.inhometfgandroidcarloshernandez.data.remote.util.NetworkResult
import javax.inject.Inject

class UsuarioRemoteDataSource @Inject constructor(
    private val usuarioService: UsuarioService
) :BaseApiResponse() {
    suspend fun login(loginRequestDTO: LoginRequestDTO): NetworkResult<LoginResponseDTO> =
        safeApiCall{ usuarioService.login(loginRequestDTO) }
}