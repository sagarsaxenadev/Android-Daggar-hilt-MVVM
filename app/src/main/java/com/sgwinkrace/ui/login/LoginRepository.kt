package com.sgwinkrace.ui.login

import com.sgwinkrace.model.LoginData
import com.sgwinkrace.network.ApiServiceImple
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiServiceImple: ApiServiceImple) {

    fun login(key:String,pin:String): Flow<LoginData> = flow {
        emit(apiServiceImple.login(key,pin))
    }.flowOn(Dispatchers.IO)
}