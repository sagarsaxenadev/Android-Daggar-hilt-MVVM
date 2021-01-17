package com.sgwinkrace.ui.signup

import com.sgwinkrace.model.SignupData
import com.sgwinkrace.network.ApiServiceImple
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class SignupRepository @Inject constructor( private val apiServiceImple: ApiServiceImple) {

    fun signupUser(email:String,pin:String,mobile:String): Flow<SignupData> = flow {
        emit( apiServiceImple.signupUser(email,mobile,pin))

    }.flowOn(Dispatchers.IO)

}