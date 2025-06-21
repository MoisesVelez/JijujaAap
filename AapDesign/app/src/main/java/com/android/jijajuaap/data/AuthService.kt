package com.android.jijajuaap.data

import kotlinx.coroutines.tasks.await
import android.annotation.SuppressLint
import android.app.Activity
import com.android.jijajuaap.R
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.content.Context
import android.util.Log
import com.android.jijajuaap.objects.User
import com.android.jijajuaap.objects.test
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class AuthService @SuppressLint("RestrictedApi")
@Inject constructor(private val firebaseAuth: FirebaseAuth, private val firestore: FirebaseFirestore,@ApplicationContext private val context: Context){



    suspend fun saveUserInFirestore(user: FirebaseUser, name: String) {
        val userData = User(
            uid = user.uid,
            email = user.email ?: "",
            name = name
        )
        firestore.collection("users").document(user.uid).set(userData).await()
    }


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



    suspend fun completedRegisterWithCredential(credential: AuthCredential): FirebaseUser? =
        suspendCoroutine { cont ->
            firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = firebaseAuth.currentUser
                        if (user != null) {

                            val updateData = mutableMapOf<String, Any>(
                                "uid" to user.uid,
                                "name" to (user.displayName ?: ""),
                                "email" to (user.email ?: ""),
                                "avatarId" to "error_de_usuario",
                                "totalPoints" to 0,
                                "totalQuiz" to 0,
                                "team" to "Sin equipo",
                                "rango" to "Novato"
                            )



                            firestore.collection("users")
                                .document(user.uid)
                                .set(updateData, SetOptions.merge())
                                .addOnSuccessListener {
                                    cont.resume(user)
                                }
                                .addOnFailureListener { e ->
                                    cont.resumeWithException(e)
                                }
                        } else {
                            cont.resume(null)
                        }
                    } else {
                        cont.resume(null)
                    }
                }
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



    suspend fun getUserData(uid: String): User? = suspendCoroutine { cont ->
        firestore.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val user = document.toObject(User::class.java)
                    cont.resume(user)
                } else {
                    cont.resume(null)
                }
            }
            .addOnFailureListener { e ->
                cont.resumeWithException(e)
            }
    }

    suspend fun updateUserTeam(uid: String, team: String) {
        val userRef = firestore.collection("users").document(uid)
        val updateData = mapOf("team" to team)
        userRef.set(updateData, SetOptions.merge()).await()
    }

    suspend fun updateImagen(uid: String,avatarId:String){
        val userRef = firestore.collection("users").document(uid)
        val  updateImg = mapOf("avatarId" to avatarId)
        userRef.set(updateImg, SetOptions.merge()).await()
    }



    suspend fun updateTema(uid: String,tema:String){
        val userRef = firestore.collection("users").document(uid)
        val  updateImg = mapOf("tema" to tema)
        userRef.set(updateImg, SetOptions.merge()).await()
    }

    suspend fun updatePuntos(uid: String,tema:String,int: Int){
        val userRef = firestore.collection("users").document(uid)
        val  updatePuntos = mapOf(tema to int)
        userRef.set(updatePuntos, SetOptions.merge()).await()
    }


    suspend fun getTestData(uid: String, collection: String): test? {
        try {
            val doc = firestore.collection(collection).document(uid).get().await()
            if (doc.exists()) {
                Log.d("Firestore", "Documento encontrado en colección '$collection' con id '$uid': ${doc.data}")
                val testObj = doc.toObject(test::class.java)
                Log.d("Firestore", "Objeto mapeado: $testObj")
                return testObj
            } else {
                Log.w("Firestore", "Documento NO existe en colección '$collection' con id '$uid'")
                return null
            }
        } catch (e: Exception) {
            Log.e("Firestore", "Error al obtener documento", e)
            return null
        }
    }

}
















