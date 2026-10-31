package com.example.appuesan.presentation.realtime

import android.R
import android.R.attr.text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions


private val firestoreInstance =
    FirebaseFirestore.getInstance()
        .collection("eventos")
        .document("task")




@Composable
fun FirestoreRealtimeScreen(){
    var contador by remember { mutableStateOf(0) }
    var mensaje by remember { mutableStateOf (value = "") }
    var mensajeInput by remember { mutableStateOf (value = "") }
    var conectado by remember { mutableStateOf (value = false) }

    DisposableEffect(Unit){
    val listener = firestoreInstance.addSnapshotListener { snapshot, error ->
        if (error != null) {
            conectado = false
            return@addSnapshotListener
        }
        if(snapshot !=null && snapshot.exists()){
            contador = snapshot.getLong("contador")?.toInt() ?: 0
            mensaje = snapshot.getString("mensaje")?: ""
        }
    }
        onDispose {
        listener.remove()
    }

    }
    Column(
        modifier = Modifier.fillMaxWidth().padding(24.dp).statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),

    ) {
        Text("FireStore en tiempo real", style = MaterialTheme.typography.titleLarge)
        Text(
            if (conectado) "Conectado" else "Conectando...",
            style = MaterialTheme.typography.titleMedium
        )
        Button(onClick ={
            firestoreInstance.set(mapOf("contador" to FieldValue.increment(1)), SetOptions.merge())
        }){
            Text("+1")
        }
        Text("Mensaje actual: $mensaje")

        OutlinedTextField(
            value = mensajeInput,
            onValueChange = {mensajeInput = it},
            placeholder = {Text("mensaje")},
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = {
            firestoreInstance.set(mapOf("mensaje" to mensajeInput),SetOptions.merge())
        },modifier = Modifier.fillMaxWidth()){
            Text("Actualizar mensaje")
        }
    }

}