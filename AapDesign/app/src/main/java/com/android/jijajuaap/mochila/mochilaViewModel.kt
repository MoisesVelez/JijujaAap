package com.android.jijajuaap.mochila


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.android.jijajuaap.data.AuthService
import com.android.jijajuaap.objects.Objetos
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class mochilaViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {


    val listaObjetos = mutableStateListOf<Objetos>()
    var objeto by mutableStateOf<Objetos?>(null)

    fun seleccionarObjeto(objetos: Objetos){
        objeto = objetos
    }

    fun añadirLista(objetos: Objetos){
        if (objetos !in listaObjetos && listaObjetos.size <= 2){
            listaObjetos.add(objetos)
        }
    }

    fun sacarLista(objetos: Objetos){
        listaObjetos.remove(objetos)
    }

}