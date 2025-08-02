package com.android.jijajuaap.ranking

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.jijajuaap.data.AuthService
import com.android.jijajuaap.objects.User
import com.android.jijajuaap.objects.preguntaComunidad
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class rankingView @Inject constructor(
    private val authService: AuthService
) : ViewModel() {


    var listausuarios by mutableStateOf<List<User>>(emptyList())
        private set



    fun obtener15() {
        viewModelScope.launch {
            try {
                val resultado = authService.obtenerTop15Usuarios()
                if (resultado != null) {
                    listausuarios = resultado
                }
            } catch (e: Exception) {

            }
        }
    }


}