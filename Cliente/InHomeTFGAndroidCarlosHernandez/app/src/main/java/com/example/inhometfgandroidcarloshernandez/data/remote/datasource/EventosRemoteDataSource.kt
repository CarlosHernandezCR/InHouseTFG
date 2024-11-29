package com.example.inhometfgandroidcarloshernandez.data.remote.datasource

import com.example.inhometfgandroidcarloshernandez.data.model.request.DiasConEventosRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.request.EventosEnDiaRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.DiasConEventosResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.EventosEnDiaResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.remote.apiServices.EventosService
import com.example.inhometfgandroidcarloshernandez.data.remote.util.NetworkResult
import javax.inject.Inject

class EventosRemoteDataSource @Inject constructor(
    private val eventosService: EventosService
) :BaseApiResponse(){
    suspend fun cargarEventosDelMes(diasConEventosRequestDTO: DiasConEventosRequestDTO): NetworkResult<DiasConEventosResponseDTO> =
        safeApiCall{ eventosService.cargarEventosDelMes(diasConEventosRequestDTO) }

    suspend fun cargarEventosDelDia(eventosEnDiaRequestDTO: EventosEnDiaRequestDTO): NetworkResult<EventosEnDiaResponseDTO> =
        safeApiCall{ eventosService.cargarEventosDelDia(eventosEnDiaRequestDTO) }
}