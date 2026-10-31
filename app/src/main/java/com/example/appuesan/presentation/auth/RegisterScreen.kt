package com.example.appuesan.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appuesan.data.remote.FireBaseAuthManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable

fun RegisterScreen(navController: NavController){
    var email by remember { mutableStateOf("") }
    var name by remember {mutableStateOf("") }
    var password by remember {mutableStateOf("") }
    var confirmPassword by remember {mutableStateOf("") }
    val context = LocalContext.current

    Column(modifier = Modifier
        .padding(all = 16.dp)
        .fillMaxSize()
        .statusBarsPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Registrar una cuenta", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            placeholder = {Text("Ingrese un correo electrónico")},
            label = {Text("Correo electrónico")},
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = name,
            onValueChange = {name = it},
            placeholder = {Text("Ingrese su nombre")},
            label = {Text("Nombre")},
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            placeholder = {Text("Ingrese una contraseña")},
            label = {Text("Contraseña")},
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {confirmPassword = it},
            placeholder = {Text("Ingrese nuevamente su contraseña")},
            label = {Text("Confirmar contraseña")},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                    val result = FireBaseAuthManager.registerUser(email = email, password = password, name = name)
                    if (result.isSuccess)
                        navController.navigate("login")
                    else {
                        val error = result.exceptionOrNull()?.message ?: "Error desconocido"
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()

                    }
                }
                },
            enabled =  password.isNotBlank() && confirmPassword.isNotBlank() && password == confirmPassword && email.contains("@"),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear Cuenta")
        }
    }
}