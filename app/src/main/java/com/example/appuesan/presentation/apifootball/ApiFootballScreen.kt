package com.example.appuesan.presentation.apifootball

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.appuesan.data.local.EquipoFavorito
import kotlin.math.exp


@Composable
fun ApiFootBallScreen(viewModel: ApiFootballViewModel = viewModel()){
    val countries by viewModel.countries.collectAsState()
    val selectedCountry by viewModel.selectedCountry.collectAsState()
    val teams by viewModel.teams.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.error.collectAsState()
    val favoritos by viewModel.favoritos.collectAsState()

    var expanded by remember { mutableStateOf(false) }
    var mostrarFavoritos by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ){
        Text("Equipos de Futbol por país", style = MaterialTheme.typography.titleLarge)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { mostrarFavoritos = false }) {
                Text("Equipos")
            }

            Button(onClick = { mostrarFavoritos = true }) {
                Text("Mis favoritos")
            }
        }
        Box{
            OutlinedButton(
                onClick = {expanded = true},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(selectedCountry?.name ?: "Selecciona un País")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {expanded = false},
                modifier = Modifier.fillMaxWidth()
            ){
                countries.forEach{country ->
                    DropdownMenuItem(
                        text = {Text(country.name)},
                        onClick = {
                            viewModel.onCountrySelected(country)
                            expanded = false
                        }
                    )
                }
            }
        }//close box

        Spacer(modifier = Modifier.height(16.dp))
            if (mostrarFavoritos) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(favoritos) { equipo ->
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(equipo.logo),
                                    contentDescription = "Escudo de ${equipo.nombre}",
                                    modifier = Modifier.size(50.dp),
                                    contentScale = ContentScale.Fit
                                )

                                Column {
                                    Text(
                                        equipo.nombre,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(equipo.pais)
                                }
                                IconButton(
                                    onClick = {
                                        viewModel.eliminarFavorito(equipo)
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Favorite,
                                        contentDescription = "Eliminar de favoritos",
                                        tint = Color.Red
                                    )
                                }
                            }
                        }
                    }
                }
            } else if (isLoading) {
                CircularProgressIndicator()
            } else if (errorMessage != null) {
                Text(
                    "Error: $errorMessage",
                    color = MaterialTheme.colorScheme.error
                )
            } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(teams){wrapper ->
                    val esFavorito = favoritos.any {
                        it.id == wrapper.team.id
                    }
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(4.dp)
                    ){
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(wrapper.team.logo),
                                contentDescription = "Logo del equipo",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.height(50.dp)
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                            Column {
                                Text(wrapper.team.name, style = MaterialTheme.typography.titleMedium)
                                Text(wrapper.team.country)
                                Text(wrapper.team.founded?.toString() ?: "")
                            }

                            IconButton(
                                onClick = {
                                    val equipo = EquipoFavorito(
                                        id = wrapper.team.id,
                                        nombre = wrapper.team.name,
                                        pais = wrapper.team.country,
                                        logo = wrapper.team.logo
                                    )

                                    if (esFavorito) {
                                        viewModel.eliminarFavorito(equipo)
                                    } else {
                                        viewModel.guardarFavorito(equipo)
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = if (esFavorito) Icons.Filled.Favorite
                                    else Icons.Outlined.FavoriteBorder,
                                    contentDescription = "Favorito",
                                    tint = if (esFavorito) Color.Red
                                    else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}