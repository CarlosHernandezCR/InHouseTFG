package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.inmuebles

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.inhometfgandroidcarloshernandez.common.Constantes
import com.example.inhometfgandroidcarloshernandez.data.model.CajonDTO
import com.example.inhometfgandroidcarloshernandez.ui.GlobalViewModel
import com.example.inhometfgandroidcarloshernandez.ui.framework.screens.calendario.Cargando
import com.example.inhometfgandroidcarloshernandez.ui.framework.screens.calendario.Selector
import com.example.inhometfgandroidcarloshernandez.ui.framework.screens.estados.ComboBox
import com.example.inhometfgandroidcarloshernandez.ui.framework.screens.inmuebles.InmueblesContract.Usuario

@Composable
fun InmueblesActivity(
    idUsuario: String,
    idCasa: String,
    showSnackbar: (String) -> Unit = {},
    viewModel: InmueblesViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(idCasa) {
        viewModel.handleEvent(InmueblesContract.InmueblesEvent.CargarDatos(idCasa))
        viewModel.handleEvent(InmueblesContract.InmueblesEvent.CargarUsuarios(idCasa))
    }
    LaunchedEffect(uiState.mensaje) {
        uiState.mensaje?.let { showSnackbar.invoke(it) }
        viewModel.handleEvent(InmueblesContract.InmueblesEvent.MensajeMostrado)
    }
    if (uiState.isLoading)
        Cargando()
    else
        InmueblesPantalla(
            habitacionActual = uiState.habitacionActual,
            habitaciones = uiState.habitaciones,
            muebleActual = uiState.muebleActual,
            muebles = uiState.muebles.map { it.nombre },
            cajones = uiState.cajones,
            usuarios = uiState.usuarios,
            cambioHabitacion = {
                viewModel.handleEvent(
                    InmueblesContract.InmueblesEvent.CambioHabitacion(
                        it
                    )
                )
            },
            cambioMueble = { viewModel.handleEvent(InmueblesContract.InmueblesEvent.CambioMueble(it)) },
            cajonClicado = {
                viewModel.handleEvent(
                    InmueblesContract.InmueblesEvent.CajonSeleccionado(
                        it
                    )
                )
            },
            agregarCajon = { nombre, idPropietario ->
                viewModel.handleEvent(
                    InmueblesContract.InmueblesEvent.AgregarCajon(
                        nombre, idPropietario
                    )
                )
            },
            agregarMueble = { nombre ->
                viewModel.handleEvent(
                    InmueblesContract.InmueblesEvent.AgregarMueble(
                        nombre
                    )
                )
            },
            agregarHabitacion = { nombre ->
                viewModel.handleEvent(
                    InmueblesContract.InmueblesEvent.AgregarHabitacion(
                        nombre
                    )
                )
            },
        )
}

@Composable
fun InmueblesPantalla(
    habitacionActual: String,
    habitaciones: List<String>,
    muebleActual: String,
    muebles: List<String>,
    cajones: List<CajonDTO>,
    usuarios: List<Usuario> = emptyList(),
    cambioHabitacion: (String) -> Unit = {},
    cambioMueble: (String) -> Unit = {},
    cajonClicado: (String) -> Unit = {},
    agregarCajon: (String, String) -> Unit,
    agregarMueble: (String) -> Unit = {},
    agregarHabitacion: (String) -> Unit = {},
) {
    var showDialog by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }
    var isCajon by remember { mutableStateOf(false) }
    val areHabitacionesEmpty = habitaciones.isEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Selector(
            valorActual = habitacionActual,
            opciones = habitaciones,
            onCambio = cambioHabitacion,
        )
        Spacer(modifier = Modifier.height(16.dp))

        Selector(
            valorActual = muebleActual,
            opciones = muebles,
            onCambio = cambioMueble,
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(cajones) { item ->
                    CajonItem(
                        cajon = item.nombre,
                        propietario = item.propietario,
                        onClick = { cajonClicado(item.nombre) }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.6f),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Button(
                    onClick = {
                        dialogTitle = Constantes.AGREGAR_HABITACION
                        isCajon = false
                        showDialog = true
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 6.dp)
                ) {
                    Text(
                        text = Constantes.AGREGAR_HABITACION,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Button(
                    onClick = {
                        dialogTitle = Constantes.AGREGAR_MUEBLE
                        isCajon = false
                        showDialog = true
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 6.dp),
                    enabled = !areHabitacionesEmpty
                ) {
                    Text(
                        text = Constantes.AGREGAR_MUEBLE,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Button(
                    onClick = {
                        dialogTitle = Constantes.AGREGAR_CAJON
                        isCajon = true
                        showDialog = true
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 6.dp),
                    enabled = !areHabitacionesEmpty
                ) {
                    Text(
                        text = Constantes.AGREGAR_CAJON,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        if (showDialog) {
            DialogNuevoElemento(
                title = dialogTitle,
                usuarios = usuarios,
                onConfirm = { name, idPropietario ->
                    if (isCajon) {
                        if (idPropietario != null) {
                            agregarCajon(name, idPropietario)
                        }
                    } else {
                        if (dialogTitle == Constantes.AGREGAR_HABITACION) {
                            agregarHabitacion(name)
                        } else {
                            agregarMueble(name)
                        }
                    }
                    showDialog = false
                },
                onDismiss = { showDialog = false }
            )
        }
    }
}

@Composable
fun CajonItem(cajon: String, propietario: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = cajon, style = MaterialTheme.typography.titleMedium)
            Text(
                text = propietario,
                style = MaterialTheme.typography.titleSmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun DialogNuevoElemento(
    title: String,
    usuarios: List<Usuario> = emptyList(),
    onConfirm: (String, String?) -> Unit,
    onDismiss: () -> Unit
) {
    var itemName by remember { mutableStateOf("") }
    var selectedUsuario by remember { mutableStateOf<Usuario?>(null) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = title, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = itemName,
                    onValueChange = { itemName = it },
                    label = { Text(Constantes.NOMBRE) },
                    modifier = Modifier.fillMaxWidth()
                )
                if (usuarios.isNotEmpty() && title != Constantes.AGREGAR_HABITACION && title != Constantes.AGREGAR_MUEBLE) {
                    Spacer(modifier = Modifier.height(8.dp))
                    ComboBox(
                        items = usuarios.map { it.nombre },
                        selectedItem = selectedUsuario?.nombre ?: "",
                        titulo = Constantes.PROPIETARIO,
                        onItemSelected = { name ->
                            selectedUsuario = usuarios.find { it.nombre == name }
                        }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(Constantes.CANCELAR)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = { onConfirm(itemName, selectedUsuario?.id) }) {
                        Text(Constantes.AGREGAR)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInmueblesActivity() {
    val habitaciones = listOf("Sala", "Cocina", "Dormitorio")
    val muebles = listOf("Armario", "Mesa", "Silla")
    val cajones = listOf(
        CajonDTO(nombre = "Cajón 1", propietario = "Carlos"),
        CajonDTO(nombre = "Cajón 2", propietario = "Ana"),
        CajonDTO(nombre = "Cajón 3", propietario = "Luis")
    )

    InmueblesPantalla(
        habitacionActual = "Sala",
        habitaciones = habitaciones,
        muebleActual = "Armario",
        muebles = muebles,
        cajones = cajones,
        cambioHabitacion = {},
        cambioMueble = {},
        cajonClicado = {},
        agregarCajon = { _, _ -> },
        agregarMueble = {},
    )
}