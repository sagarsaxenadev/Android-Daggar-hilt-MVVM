package com.sgwinkrace.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataStore @Inject constructor(@ApplicationContext  context: Context) {
    private val dataStore = context.createDataStore(name = "race_app_data")

    companion object {

        val USER_NAME = stringPreferencesKey("user_name")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_ID= stringPreferencesKey("user_id")
        val USER_MOBILE = stringPreferencesKey("mobile_number")
        val IS_LOGIN = booleanPreferencesKey("is_login")
        val IS_PROFILE_COMPLETE = booleanPreferencesKey("is_profile_complete")

    }

    suspend fun saveUserName(name: String) {
        dataStore.edit {
            it[USER_NAME] = name
        }
    }

    suspend fun saveProfileComplete(status: Boolean) {
        dataStore.edit {
            it[IS_PROFILE_COMPLETE] = status
        }
    }

    suspend fun saveUserEmail(email: String) {
        dataStore.edit {
            it[USER_EMAIL] = email
        }
    }

    suspend fun saveUserId(id: String) {
        dataStore.edit {
            it[USER_ID] = id
        }
    }

    suspend fun saveUserMobile(mobile: String) {
        dataStore.edit {
            it[USER_EMAIL] = mobile
        }
    }

    suspend fun saveIsLogin(is_login: Boolean) {
        dataStore.edit {
            it[IS_LOGIN] = is_login
        }
    }


    val userNameFlow: Flow<String> = dataStore.data.map {
        it[USER_NAME] ?: ""
    }

    val userEmailFlow: Flow<String> = dataStore.data.map {
        it[USER_EMAIL] ?: ""
    }

    val userIsProfileComplete: Flow<Boolean> = dataStore.data.map {
        it[IS_PROFILE_COMPLETE] ?:false
    }



    val userIdFlow: Flow<String> = dataStore.data.map {
        it[USER_ID] ?: ""
    }

    val userMobileFlow: Flow<String> = dataStore.data.map {
        it[USER_MOBILE] ?: ""
    }

    val isLoginFlow: Flow<Boolean> = dataStore.data.map {
        it[IS_LOGIN] ?: false
    }

     suspend fun clearPreferenceStorage() {
        dataStore.edit {
            it.clear()
        }
    }


}