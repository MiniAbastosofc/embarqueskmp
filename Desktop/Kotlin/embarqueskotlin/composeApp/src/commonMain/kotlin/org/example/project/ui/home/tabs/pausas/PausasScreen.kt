package org.example.project.ui.home.tabs.pausas


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.example.project.ui.home.tabs.pausas.Components.BotonConfirmarPausa
import org.example.project.ui.home.tabs.pausas.Components.CardPausas
import org.example.project.ui.home.tabs.pausas.Components.MotivoPausa
import org.example.project.ui.home.tabs.pausas.Components.TextoInformativoPausas
import org.example.project.ui.home.tabs.pausas.Components.TipoPausas
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PausasScreen(navController: NavHostController, rutaId: Int?, usuarioId: Int?) {
    val pausasViewModel = koinViewModel<PausasViewModel>()
    val state by pausasViewModel.state.collectAsState()


    LaunchedEffect(rutaId, usuarioId) {
        println("DEBUG: PausasScreen cargada")
        println("DEBUG: rutaId recibido -> $rutaId")
        println("DEBUG: usuarioId recibido -> $usuarioId")
    }

    LaunchedEffect(rutaId) {
        if (rutaId != null) {
            pausasViewModel.cargarDatosEmbarque(rutaId)
        }
    }
    LaunchedEffect(state.operacionExitosa) {
        if (state.operacionExitosa) {
            navController.popBackStack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Regresar",
                    tint = Color(0xFF64B5F6)
                )
            }
            Text(
                text = "Asignaciones",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        //Aqui Inicia el Contenido
        CardPausas(embarque = state.datosEmbarque)
        Spacer(Modifier.height(10.dp))
        TextoInformativoPausas()
        Spacer(Modifier.height(10.dp))
        TipoPausas(
            seleccionado = state.tipoPausa,
            onOpcionSelected = { nombre -> pausasViewModel.onTipoPausaSelected(nombre) })
        Spacer(Modifier.height(10.dp))
        MotivoPausa(
            texto = state.motivoPausa,
            onValueChange = { pausasViewModel.onMotivoPausaChanged(it) })
        if (state.errorMessage != null) {
            Text(
                text = state.errorMessage!!,
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        // LÓGICA DE BOTONES CONDICIONALES
        if (state.existePausaActiva) {
            // BOTÓN DE FINALIZAR (Solo aparece si existeRegistro es true)
            Button(
                onClick = {
                    println("DEBUG UI: usuarioId=$usuarioId, pausaId=${state.pausaIdActiva}")
                    pausasViewModel.finalizarPausa(
                        pausaId = state.pausaIdActiva ?: 0,
                        usuarioId = usuarioId ?: 0
                    )
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336)), // Rojo
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Finalizar Pausa", fontWeight = FontWeight.Bold)
            }

            // Texto informativo extra si gustas
            Text(
                text = "Hay una pausa en curso desde Node.js",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        } else {
            // BOTÓN DE INICIAR (Tu botón original)
            BotonConfirmarPausa(
                habilitado = state.botonConfirmar,
                isLoading = state.isLoading,
                onClick = {
                    pausasViewModel.iniciarPausa(
                        embarqueId = rutaId,
                        usuarioId = usuarioId
                    )
                }
            )
        }
    }
}