package com.sgwinkrace.ui.otp

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sgwinkrace.model.OtpData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class OtpViewModel @ViewModelInject constructor(private val otpRepository: OtpRepository) : ViewModel(){

    private var _otpLiveData = MutableLiveData<OtpData>()

    val liveData: LiveData<OtpData>
        get() = _otpLiveData


    fun verifyOtp(id:String,otp:String) {

        viewModelScope.launch {

            otpRepository.verifyOtp(id,otp).collect {
                _otpLiveData.value=it
            }

        }
    }



}