package com.walmart.assignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.walmart.assignment.data.Country
import com.walmart.assignment.repository.CountriesRepository
import com.walmart.assignment.ui.UIState
import kotlinx.coroutines.launch

class CountriesViewModel(
    private val repository: CountriesRepository = CountriesRepository()
) : ViewModel() {
    
    private val _uiState = MutableLiveData<UIState<List<Country>>>()
    val uiState: LiveData<UIState<List<Country>>> = _uiState

    fun loadCountries() {
        if (_uiState.value == null) {
            _uiState.value = UIState.Loading
        }

        viewModelScope.launch {
            if (_uiState.value == UIState.Loading) {
                repository.getCountries()
                    .onSuccess { countries ->
                        _uiState.value = UIState.Success(countries)
                    }
                    .onFailure { exception ->
                        val errorMessage = when (exception.message) {
                            "Empty response" -> "No countries found."
                            "Server error" -> "Server error. Please try again later."
                            "Timeout" -> "Request timeout. Please try again."
                            "Network error" -> "Network error. Please check your internet connection."
                            else -> "An unknown error occurred."
                        }
                        _uiState.value = UIState.Error(errorMessage)
                    }
            }
        }
    }
} 