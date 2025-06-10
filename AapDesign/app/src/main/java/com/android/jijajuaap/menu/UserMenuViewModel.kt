package com.android.jijajuaap.menu

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.jijajuaap.R
import com.android.jijajuaap.data.AuthService
import com.android.jijajuaap.objects.User
import com.android.jijajuaap.ui.theme.White
import com.android.jijajuaap.ui.theme.azulJi
import com.android.jijajuaap.ui.theme.azulUser
import com.android.jijajuaap.ui.theme.rojoJa
import com.android.jijajuaap.ui.theme.rojoUser
import com.android.jijajuaap.ui.theme.verdeJu
import com.android.jijajuaap.ui.theme.verdeUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserMenuViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {
    var colorEscogido: Color by mutableStateOf<Color>(White)
    var colorFinal: Color by mutableStateOf<Color>(colorEscogido)



    @Composable
    fun colorUsuario(color: Color): Color{
        if(color == azulJi){
            return azulUser
        }else if(color==rojoJa){
            return rojoUser
        }else if(color==verdeJu){
            return verdeUser
        }
        return White
    }

    var user by mutableStateOf<User?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun loadUserData(uid: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val userData = authService.getUserData(uid)
                user = userData

            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "Error al cargar usuario"
            } finally {
                isLoading = false
            }
        }
    }


    @SuppressLint("DiscouragedApi")
    @Composable
    fun imagenUsuario(user: User?): Int {
        val drawableName = user?.avatarId
        val context = LocalContext.current
        val avatarResId = if (!drawableName.isNullOrEmpty()) {
            context.resources.getIdentifier(drawableName, "drawable", context.packageName)
        } else {
            R.drawable.error_de_usuario
        }
        return avatarResId

    }


    fun hasUserChosenTeam(context: Context, userId: String): Boolean {
        val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        return prefs.getBoolean("hasChosenTeam_$userId", false)
    }

    fun setUserHasChosenTeam(context: Context, userId: String) {
        val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("hasChosenTeam_$userId", true).apply()
    }


        @Composable
        fun MyCustomDialog(onDismiss: () -> Unit) {
            Dialog(
                onDismissRequest = { },
                properties = DialogProperties(
                    dismissOnClickOutside = false,
                    dismissOnBackPress = false
                )
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = White,
                    tonalElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Bienvenido al mundo de JIJAJU",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            "Debes elegir un pueblo al que consagrar tu lealtad. Elige tu color:",
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        TeamOption("Rojines", "Rojin", rojoJa) {
                            updateTeam("Rojin")
                            onDismiss()
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        TeamOption("Azulenses", "Azulense", azulJi) {
                            updateTeam("Azulense")
                            onDismiss()
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        TeamOption("Verdianos", "Verdiano", verdeJu) {
                            updateTeam("Verdiano")
                            onDismiss()
                        }
                    }
                }
            }
        }



    @Composable
    fun TeamOption(label: String, teamName: String, color: Color, onClick: () -> Unit) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(
                text = label,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(containerColor = color)
            ) {
                Text("Elegir", color = Color.Black)
            }
        }
    }

    fun cambioColor(team:String?): Color{
        val colorElegido = when(team){
            "Rojin" -> rojoJa
            "Azulense" -> azulJi
            "Verdiano" -> verdeJu
            else -> White
        }
        return colorElegido
    }


    fun updateTeam(team: String) {
        viewModelScope.launch {
            try {
                user?.uid?.let { uid ->
                    authService.updateUserTeam(uid, team)
                    user = user?.copy(team = team)
                    colorFinal = cambioColor(team)
                }
            } catch (e: Exception) {
                Log.e("UserMenuViewModel", "Error al actualizar el equipo", e)
            }
        }
    }

    fun updateImg(avatarId:String){

        viewModelScope.launch {
            try {
                user?.uid?.let { uid ->
                    authService.updateImagen(uid,avatarId)
                    user = user?.copy(avatarId = avatarId)

                }
            } catch (e: Exception) {
                Log.e("UserMenuViewModel", "Error al actualizar la imagen", e)
            }
        }

    }





}



