package org.example.project.ui.home.tabs.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.example.project.ui.core.navigation.Routes
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = koinViewModel<LoginViewModel>()
) {
    val loginViewModel = koinViewModel<LoginViewModel>()
    val state by loginViewModel.state.collectAsState()
    val isButtonEnabled = !state.isLoading

    LaunchedEffect(state.isAuthenticated) {
        if (state.isAuthenticated) {
            // Navegar a la pantalla Home (que contiene el BottomNavigation)
            navController.navigate(Routes.Home.route) {
                // Limpia la pila de navegación para que no pueda volver al login
                popUpTo(Routes.Login.route) { inclusive = true }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.LocalShipping,
            contentDescription = "Logo",
            tint = Color(0xFF1173D4),
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))
        Text("Bodega MAB", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        Text("Bienvenido", fontSize = 36.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))

        // Con OutlinedTextField
        OutlinedTextField(
            value = state.usuarioText,
            onValueChange = { loginViewModel.onUsernameChange(it) },
            label = { Text("Usuario", fontSize = 16.sp) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray,
                focusedTextColor = Color.Gray,
                focusedLabelColor = Color.Gray,
                cursorColor = Color.Gray,
                errorBorderColor = Color.Red,
                errorLabelColor = Color.Red,
                errorCursorColor = Color.Red,
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.passwordText,
            onValueChange = { loginViewModel.onPasswordChange(it) },
            label = { Text("Contraseña", fontSize = 16.sp) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray,
                focusedTextColor = Color.Gray,
                focusedLabelColor = Color.Gray,
                cursorColor = Color.Gray,
                errorBorderColor = Color.Red,
                errorLabelColor = Color.Red,
                errorCursorColor = Color.Red,
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { loginViewModel.onLoginClicked() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            // Deshabilita el botón si estamos cargando
            enabled = isButtonEnabled,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1173D4))
        ) {
            // Muestra un ProgressBar o texto según el estado de carga
            if (state.isLoading) {
                // androidx.compose.material3.CircularProgressIndicator
                CircularProgressIndicator(color = Color.White)
            } else {
                Text("Iniciar Sesión", color = Color.White, fontSize = 18.sp)
            }
        }

        // --- Manejo de Errores ---
        state.error?.let { errorMessage ->
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = errorMessage,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    // --- Manejo de Navegación tras Éxito ---
    // Esta parte depende de cómo manejes la navegación (ej. NavController, Cicerone)
    if (state.isAuthenticated) {
        // Ejecuta la navegación UNA SOLA VEZ cuando isAuthenticated cambia a true
        LaunchedEffect(Unit) {
            // TODO: Navegar a la siguiente pantalla (Ejemplo conceptual con NavController):
            // navController.navigate("home_screen_route") {
            //     popUpTo("login_screen_route") { inclusive = true } // Limpia la pila de navegación
            // }
        }
    }
}
