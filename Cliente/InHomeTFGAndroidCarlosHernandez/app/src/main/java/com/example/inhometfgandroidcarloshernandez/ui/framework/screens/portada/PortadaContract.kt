package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.portada

interface PortadaContract {
    sealed class PortadaEvent {
        data class Login(val correo: String) : PortadaEvent()
        data object ErrorMostrado: PortadaEvent()
    }

    data class PortadaState(
        val loading: Boolean = false,
        val error: String? = null,
        val estado: String? = null
    )
}