package com.android.jijajuaap.data


import android.annotation.SuppressLint
import android.app.Activity
import com.android.jijajuaap.R
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resumeWithException


class AuthService @SuppressLint("RestrictedApi")
@Inject constructor(private val firebaseAuth: FirebaseAuth, @ApplicationContext private val context: Context){

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

    fun getGoogleClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(context, gso)
    }

   suspend fun loginWithGoogle(string: String): FirebaseUser? {
        val credential = GoogleAuthProvider.getCredential(string,null)
      return completedRegisterWithCredential(credential)

    }



    private suspend fun completedRegisterWithCredential(credential: AuthCredential): FirebaseUser? {
        return firebaseAuth.signInWithCredential(credential).await().user
    }

    suspend fun loginWithTwitter(activity: Activity): FirebaseUser? {
        val provider = OAuthProvider.newBuilder("twitter.com").build()
        return registerWithProvider(activity, provider)
    }

    private suspend fun registerWithProvider(
        activity: Activity,
        provider: OAuthProvider
    ): FirebaseUser? = suspendCancellableCoroutine { continuation ->
        val auth = FirebaseAuth.getInstance()
        val pendingResult = auth.pendingAuthResult

        if (pendingResult != null) {
            pendingResult
                .addOnSuccessListener { continuation.resume(it.user) }
                .addOnFailureListener { continuation.resumeWithException(it) }
        } else {
            auth.startActivityForSignInWithProvider(activity, provider)
                .addOnSuccessListener { continuation.resume(it.user) }
                .addOnFailureListener { continuation.resumeWithException(it) }
        }
    }




}




