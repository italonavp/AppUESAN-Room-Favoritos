package com.example.appuesan.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoritos")
data class EquipoFavorito(
    @PrimaryKey val id: Int,
    val nombre: String,
    val pais: String,
    val logo: String
)