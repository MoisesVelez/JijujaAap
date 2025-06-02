package com.android.jijajuaap.data


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthService @Inject constructor(private val firebaseAuth: FirebaseAuth){

    suspend fun login(email:String,password:String): FirebaseUser? {
       return firebaseAuth.signInWithEmailAndPassword(email,password).await().user
    }

    suspend fun register(email:String, password:String): FirebaseUser? {
        return firebaseAuth.createUserWithEmailAndPassword(email,password).await().user
    }

    fun isUserLogged(): Boolean{
        return getCurrentUser() != null
    }

    private fun getCurrentUser()= firebaseAuth.currentUser


    fun logOut() {
        firebaseAuth.signOut()
    }
}