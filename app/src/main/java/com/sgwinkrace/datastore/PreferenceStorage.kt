package com.sgwinkrace.datastore

import kotlinx.coroutines.flow.Flow

interface PreferenceStorage {

    val isLogin: Flow<Boolean>
    suspend fun setIsLogin(isDarkTheme: Boolean)


    suspend fun clearPreferenceStorage()
}
