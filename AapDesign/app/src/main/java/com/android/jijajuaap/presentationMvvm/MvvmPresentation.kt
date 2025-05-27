package com.android.jijajuaap.presentationMvvm


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MvvmPresentation : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password



    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password

    }

}