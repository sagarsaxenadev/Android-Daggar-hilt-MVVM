package com.sgwinkrace.ui.signup


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.sgwinkrace.model.SignupData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SignupViewModel @ViewModelInject constructor(private val signupRepository: SignupRepository) : ViewModel() {

    private var _signupLiveData = MutableLiveData<SignupData>()

    val signupLiveData: LiveData<SignupData>
        get() = _signupLiveData


    fun userSignup(email:String,mobile:String,pin:String) {

        viewModelScope.launch {

           signupRepository.signupUser(email, pin, mobile).collect {
               _signupLiveData.value =it
           }

        }
    }
}