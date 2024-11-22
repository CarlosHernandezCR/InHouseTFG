package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginActivity(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateLogin: (Int) -> Unit = {},
    showSnackbar: (String) -> Unit = {},
    innerPadding: PaddingValues,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            showSnackbar(it)
            viewModel.handleEvent(LoginContract.PortadaEvent.ErrorMostrado)
        }
    }

    LaunchedEffect(uiState.id) {
        if(uiState.id != 0) {
            onNavigateLogin(uiState.id)
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .padding(paddingValues = innerPadding)
    ) {
        Portada(
            onCorreoChange = { correo ->
                viewModel.handleEvent(LoginContract.PortadaEvent.Login(correo))
            },
            isLoading = uiState.isLoading
        )
    }
}

@Composable
fun Portada(
    onCorreoChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean
) {
    var correo by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onCorreoChange(correo) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar")
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (isLoading) {
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PortadaPreview() {
    Portada(
        onCorreoChange = {},
        isLoading = false
    )
}