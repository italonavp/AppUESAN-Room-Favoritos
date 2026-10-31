package com.example.appuesan.presentation.apifootball

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appuesan.data.remote.apifootball.Country
import com.example.appuesan.data.remote.apifootball.RetrofitInstance
import com.example.appuesan.data.remote.apifootball.TeamWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.appuesan.data.local.AppDatabase
import com.example.appuesan.data.local.EquipoFavorito
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class ApiFootballViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val favoritoDao = AppDatabase.getDatabase(application).favoritoDao()
    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    val countries: StateFlow<List<Country>> = _countries
    private val _selectedCountry = MutableStateFlow<Country?>(null)
    val selectedCountry: StateFlow<Country?> = _selectedCountry

    private val _teams = MutableStateFlow<List<TeamWrapper>>(emptyList())
    val teams: StateFlow<List<TeamWrapper>> = _teams

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessages = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _errorMessages

    val favoritos = favoritoDao.obtenerFavoritos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


    init {
        loadCountries()
    }

    fun loadCountries(){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.api.getCountries()
                _countries.value = response.response
            }catch (e: Exception){
                _errorMessages.value = e.message
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun loadTeamsByCountry(country: Country){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.api.getTeamsBycountry(country.name)
                _teams.value = response.response
                _selectedCountry.value = country
            }catch (e: Exception){
                _errorMessages.value = e.message
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun onCountrySelected(country: Country){
        _selectedCountry.value = country
        loadTeamsByCountry(country)
    }

    fun guardarFavorito(equipo: EquipoFavorito) {
        viewModelScope.launch {
            favoritoDao.guardar(equipo)
        }
    }

    fun eliminarFavorito(equipo: EquipoFavorito) {
        viewModelScope.launch {
            favoritoDao.eliminar(equipo)
        }
    }
}

