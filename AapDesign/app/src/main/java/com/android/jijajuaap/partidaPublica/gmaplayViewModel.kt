package com.android.jijajuaap.partidaPublica



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.android.jijajuaap.data.AuthService
import com.android.jijajuaap.objects.User
import com.android.jijajuaap.objects.test
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class gmaplayViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {



        var preguntasFinal by mutableStateOf<test?>(null)
            private set



        fun preguntas(user: User?) {
            viewModelScope.launch {
                if (user?.tema.toString() == "Historia") {
                    preguntasFinal = authService.getTestData("01", "historia")
                } else if (user?.tema == "Naturaleza") {
                    preguntasFinal = authService.getTestData("1", "naturaleza")
                }
            }
        }
    }





