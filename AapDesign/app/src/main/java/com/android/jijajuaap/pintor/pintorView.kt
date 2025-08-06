package com.android.jijajuaap.pintor

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.jijajuaap.R
import com.android.jijajuaap.data.AuthService
import com.android.jijajuaap.objects.Objetos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class pintorView @Inject constructor(private val authService: AuthService) : ViewModel() {


    var listaObjetos by mutableStateOf<List<Objetos>>(emptyList())
        private set

    var objeto by mutableStateOf<Objetos?>(null)
        private set



    fun obtenerObjetos() {
        viewModelScope.launch {
            try {
                val resultado = authService.obtenerObjetos()
                if (resultado != null) {
                    listaObjetos = resultado
                }
            } catch (e: Exception) {

            }
        }
    }


    @SuppressLint("DiscouragedApi")
    @Composable
    fun imagenObjeto(objeto: Objetos?): Int {
        val drawableName = objeto?.imagen
        val context = LocalContext.current
        val avatarResId = if (!drawableName.isNullOrEmpty()) {
            context.resources.getIdentifier(drawableName, "drawable", context.packageName)
        } else {
            R.drawable.mochila
        }
        return avatarResId

    }

   fun obtenerObjeto(nombre:String){
       viewModelScope.launch {
           try {
               objeto = authService.obtenerObjeto(nombre)
           }catch (e: Exception){

           }
       }


    }

}