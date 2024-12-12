package com.example.inhometfgandroidcarloshernandez.data.remote.apiServices

import com.example.inhometfgandroidcarloshernandez.common.Constantes
import com.example.inhometfgandroidcarloshernandez.common.ConstantesPaths
import com.example.inhometfgandroidcarloshernandez.data.model.request.AgregarCasaRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.request.UnirseCasaRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.GetCasasResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.PantallaEstadosResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CasaService {
    @GET(ConstantesPaths.GET_CASAS)
    suspend fun getCasas(@Query(Constantes.IDUSUARIO) idUsuario: String): Response<GetCasasResponseDTO>

    @POST(ConstantesPaths.AGREGAR_CASA)
    suspend fun agregarCasa(@Body agregarCasaRequestDTO: AgregarCasaRequestDTO): Response<Void>

    @POST(ConstantesPaths.UNIRSE_CASA)
    suspend fun unirseCasa(@Body unirseCasaRequestDTO: UnirseCasaRequestDTO): Response<Void>

    @GET(ConstantesPaths.ESTADOS)
    suspend fun getDatosCasa(@Query(Constantes.IDUSUARIO) idUsuario: String,@Query(Constantes.IDCASA) idCasa: String, @Query(Constantes.TOKEN) token: String): Response<PantallaEstadosResponseDTO>
}