package com.android.jijajuaap.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.firebase.auth.FirebaseAuth


@Composable
fun menu(userMenuViewModel: UserMenuViewModel){
    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
    LaunchedEffect(currentUserUid) {
        if (currentUserUid != null) {
            userMenuViewModel.loadUserData(currentUserUid)
        }
    }

    UserMenuScreen(userMenuViewModel)
}








@Composable
fun UserMenuScreen(viewModel: UserMenuViewModel) {

    val user = viewModel.user
    val isLoading = viewModel.isLoading
    val error = viewModel.errorMessage

    if (isLoading) {
        CircularProgressIndicator()
    } else if (error != null) {
        Text(text = error, color = Color.Red)
    } else if (user != null) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "Hola, ${user.name}")
            Text(text = "Email: ${user.avatarId}")

        }
    }
}
