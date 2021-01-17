package com.sgwinkrace.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.sgwinkrace.model.LoginData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(private val loginRepository: LoginRepository) : ViewModel() {


    private var _loginLiveData = MutableLiveData<LoginData>()

    val loginLiveData: MutableLiveData<LoginData>
        get() = _loginLiveData


    fun login(key:String,pin:String) {

        viewModelScope.launch {

            loginRepository.login(key, pin).collect {
                _loginLiveData.value = it
            }

        }
    }


}