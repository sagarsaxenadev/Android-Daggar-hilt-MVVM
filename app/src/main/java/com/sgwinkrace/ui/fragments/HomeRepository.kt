package com.sgwinkrace.ui.fragments

import com.sgwinkrace.network.ApiServiceImple
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeRepository @Inject constructor(val apiServiceImple: ApiServiceImple) {
    fun homePost(id:String,type:String)= flow {
        emit( apiServiceImple.homePost(id,type))

    }.flowOn(Dispatchers.IO)


}