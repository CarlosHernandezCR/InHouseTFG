package com.example.athometfgandroidcarloshernandez.ui.framework.screens.inmuebles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athometfgandroidcarloshernandez.common.Constantes
import com.example.athometfgandroidcarloshernandez.common.ConstantesError
import com.example.athometfgandroidcarloshernandez.data.model.CajonDTO
import com.example.athometfgandroidcarloshernandez.data.remote.util.NetworkResult
import com.example.athometfgandroidcarloshernandez.domain.usecases.inmuebles.AgregarCajonUseCase
import com.example.athometfgandroidcarloshernandez.domain.usecases.inmuebles.AgregarHabitacionUseCase
import com.example.athometfgandroidcarloshernandez.domain.usecases.inmuebles.AgregarMuebleUseCase
import com.example.athometfgandroidcarloshernandez.domain.usecases.inmuebles.BorrarCajonUseCase
import com.example.athometfgandroidcarloshernandez.domain.usecases.inmuebles.GetDatosHabitacionesUseCase
import com.example.athometfgandroidcarloshernandez.domain.usecases.inmuebles.GetUsuariosUseCase
import com.example.athometfgandroidcarloshernandez.ui.framework.screens.inmuebles.InmueblesContract.InmueblesEvent
import com.example.athometfgandroidcarloshernandez.ui.framework.screens.inmuebles.InmueblesContract.InmueblesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InmueblesViewModel @Inject constructor(
    private val getDatosHabitacionesUseCase: GetDatosHabitacionesUseCase,
    private val agregarHabitacionUseCase: AgregarHabitacionUseCase,
    private val agregarMuebleUseCase: AgregarMuebleUseCase,
    private val agregarCajonUseCase: AgregarCajonUseCase,
    private val getUsuariosUseCase: GetUsuariosUseCase,
    private val borrarCajonUseCase: BorrarCajonUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(InmueblesState())
    val uiState: StateFlow<InmueblesState> = _uiState.asStateFlow()

    fun handleEvent(event: InmueblesEvent) {
        when (event) {
            is InmueblesEvent.CargarDatos -> cargarDatos(event.idCasa, primeraCarga = true)
            is InmueblesEvent.MensajeMostrado -> _uiState.update { it.copy(mensaje = null) }
            is InmueblesEvent.AgregarCajon -> agregarCajon(event.cajon, event.idUsuario)
            is InmueblesEvent.AgregarMueble -> agregarMueble(event.mueble)
            is InmueblesEvent.CajonSeleccionado -> TODO()
            is InmueblesEvent.CambioHabitacion -> cambiarHabitacion(event.habitacionId)
            is InmueblesEvent.CambioMueble -> cambiarMueble(event.muebleId)
            is InmueblesEvent.AgregarHabitacion -> agregarHabitacion(event.habitacion)
            is InmueblesEvent.CargarUsuarios -> cargarUsuarios(event.idCasa)
            is InmueblesEvent.BorrarCajon -> borrarCajon(event.idCajon, event.idUsuario, event.idPropietario)
        }
    }

    private fun borrarCajon(idCajon: String,idUsuario:String, idPropietario: String) {
        if (idUsuario != idPropietario){
            _uiState.update { it.copy(mensaje = ConstantesError.NO_SE_PUEDE_BORRAR_CAJON) }
        }
        if(idCajon.isEmpty()){
            _uiState.update { it.copy(mensaje = ConstantesError.NO_HAY_CAJON_QUE_BORRAR) }
        }
        viewModelScope.launch {
            borrarCajonUseCase.invoke(idCajon).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                mensaje = Constantes.CAJON_BORRADO
                            )
                        }
                        _uiState.value.idCasa.let {
                            cargarDatos(
                                idCasa = it,
                                habitacion = _uiState.value.idHabitacionActual,
                                mueble = _uiState.value.muebleActual
                            )
                        }
                    }
                    is NetworkResult.Error -> {
                        _uiState.update {
                            it.copy(
                                mensaje = result.message ?: ConstantesError.ERROR_BORRAR_CAJON,
                                isLoading = false
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    private fun cargarUsuarios(idCasa: String) {
        viewModelScope.launch {
            getUsuariosUseCase.invoke(idCasa).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiState.update {
                            it.copy(
                                idCasa = idCasa,
                                usuarios = result.data?.usuarios ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }

                    is NetworkResult.Error -> {
                        _uiState.update {
                            it.copy(
                                mensaje = result.message ?: ConstantesError.GET_USUARIOS_ERROR,
                                isLoading = false
                            )
                        }
                    }

                    is NetworkResult.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    private fun agregarHabitacion(habitacion: String) {
        viewModelScope.launch {
            _uiState.value.idCasa.let { idCasa ->
                agregarHabitacionUseCase.invoke(idCasa, habitacion).collect { result ->
                    when (result) {
                        is NetworkResult.Success -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    mensaje = Constantes.HABITACION_AGREGADO
                                )
                            }
                            cargarDatos(idCasa)
                        }

                        is NetworkResult.Error -> {
                            _uiState.update {
                                it.copy(
                                    mensaje = result.message ?: ConstantesError.ERROR_AGREGAR_HABITACION,
                                    isLoading = false
                                )
                            }
                        }

                        is NetworkResult.Loading -> {
                            _uiState.update { it.copy(isLoading = true) }
                        }
                    }
                }
            }
        }
    }

    private fun agregarMueble(mueble: String) {
        viewModelScope.launch {
            _uiState.value.idCasa.let { idCasa ->
                agregarMuebleUseCase.invoke(idCasa, _uiState.value.idHabitacionActual, mueble).collect { result ->
                    when (result) {
                        is NetworkResult.Success -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    mensaje = Constantes.MUEBLE_AGREGADO
                                )
                            }
                            cargarDatos(idCasa, _uiState.value.idHabitacionActual)
                        }

                        is NetworkResult.Error -> {
                            _uiState.update {
                                it.copy(
                                    mensaje = result.message ?: ConstantesError.ERROR_AGREGAR_MUEBLE,
                                    isLoading = false
                                )
                            }
                        }

                        is NetworkResult.Loading -> {
                            _uiState.update { it.copy(isLoading = true) }
                        }
                    }
                }
            }
        }
    }

    private fun cargarDatos(
        idCasa: String,
        habitacion: String? = null,
        mueble: String? = null,
        primeraCarga: Boolean = false,
        cambioHabitacion: Boolean = false,
    ) {
        viewModelScope.launch {
            getDatosHabitacionesUseCase.invoke(idCasa, habitacion, mueble).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiState.update {
                            it.copy(
                                idCasa = if (primeraCarga) idCasa else it.idCasa,
                                habitaciones = result.data?.habitaciones ?: emptyList(),
                                muebles = result.data?.muebles ?: emptyList(),
                                cajones = if (result.data?.cajones.isNullOrEmpty()) {
                                    listOf(CajonDTO(nombre = Constantes.NO_HAY_CAJONES, propietario = ""))
                                } else {
                                    result.data?.cajones ?: emptyList()
                                },
                                idHabitacionActual = if (result.data?.habitaciones.isNullOrEmpty()) {
                                    Constantes.ANADE_HABITACION
                                } else if (primeraCarga) {
                                    result.data?.habitaciones?.get(0)?.id ?: ""
                                } else {
                                    habitacion ?: it.idHabitacionActual
                                },
                                muebleActual = if (primeraCarga || cambioHabitacion) {
                                    if (result.data?.muebles.isNullOrEmpty()) {
                                        Constantes.NO_HAY_MUEBLE
                                    } else {
                                        result.data?.muebles?.get(0)?.id ?: ""
                                    }
                                } else {
                                    mueble ?: it.muebleActual
                                },
                                isLoading = false
                            )
                        }
                    }

                    is NetworkResult.Error -> {
                        _uiState.update {
                            it.copy(
                                mensaje = result.message ?: ConstantesError.GET_HABITACIONES_ERROR,
                                isLoading = false
                            )
                        }
                    }

                    is NetworkResult.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    private fun agregarCajon(cajon: String, idPropietario: String) {
        viewModelScope.launch {
            agregarCajonUseCase.invoke(
                _uiState.value.idCasa,
                _uiState.value.idHabitacionActual,
                _uiState.value.muebleActual,
                cajon,
                idPropietario
            ).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                mensaje = Constantes.CAJON_AGREGADO
                            )
                        }
                        _uiState.value.idCasa.let {
                            cargarDatos(
                                idCasa = it,
                                habitacion = _uiState.value.idHabitacionActual,
                                mueble = _uiState.value.muebleActual
                            )
                        }
                    }

                    is NetworkResult.Error -> {
                        _uiState.update {
                            it.copy(
                                mensaje = result.message ?: ConstantesError.ERROR_AGREGAR_CAJON,
                                isLoading = false
                            )
                        }
                    }

                    is NetworkResult.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }


    private fun cambiarMueble(mueble: String) {
        _uiState.update { it.copy(muebleActual = mueble) }
        _uiState.value.idCasa.let { cargarDatos(it, _uiState.value.idHabitacionActual, mueble) }
    }

    private fun cambiarHabitacion(habitacion: String) {
        _uiState.update { it.copy(idHabitacionActual = habitacion) }
        _uiState.value.idCasa.let { cargarDatos(it, habitacion, cambioHabitacion = true) }
    }

}