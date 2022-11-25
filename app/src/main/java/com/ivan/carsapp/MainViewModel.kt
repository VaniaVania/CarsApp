package com.ivan.carsapp
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivan.carsapp.data.Car
import com.ivan.carsapp.data.CarsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val api: CarsApi
) : ViewModel() {

    private val _state = mutableStateOf(RabbitState())
    val state: State<RabbitState> = _state

    init {
        getRandomCar()
    }

    fun getRandomCar() {
        viewModelScope.launch {
            try {
                _state.value = state.value.copy(isLoading = true)
                _state.value = state.value.copy(
                    car = api.getRandomCar(),
                    isLoading = false
                )
            } catch(e: Exception) {
                Log.e("MainViewModel", "getRandomCar: ", e)
                _state.value = state.value.copy(isLoading = false)
            }
        }
    }

    data class RabbitState(
        val car: Car? = null,
        val isLoading: Boolean = false
    )
}