package com.sgwinkrace.ui.otp

import com.sgwinkrace.model.OtpData
import com.sgwinkrace.model.SignupData
import com.sgwinkrace.network.ApiServiceImple
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class OtpRepository @Inject constructor(private val apiServiceImple: ApiServiceImple) {

    fun verifyOtp(id:String,otp:String): Flow<OtpData> = flow {
        emit( apiServiceImple.verifyOtp(id,otp))

    }.flowOn(Dispatchers.IO)

}