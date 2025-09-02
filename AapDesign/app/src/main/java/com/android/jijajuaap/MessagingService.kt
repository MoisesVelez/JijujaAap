package com.android.jijajuaap


import com.google.firebase.messaging.FirebaseMessagingService
import javax.inject.Inject

class MessagingService @Inject constructor(): FirebaseMessagingService(){

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }



    fun sendRegistrationToken(token: String){

    }

}