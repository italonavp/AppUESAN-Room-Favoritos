package com.example.appuesan.data.local
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Query
import com.example.appuesan.data.local.EquipoFavorito
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritoDao {

    @Insert
    suspend fun guardar(equipo: EquipoFavorito)

    @Delete
    suspend fun eliminar(equipo: EquipoFavorito)

    @Query("SELECT * FROM favoritos")
    fun obtenerFavoritos(): Flow<List<EquipoFavorito>>
}