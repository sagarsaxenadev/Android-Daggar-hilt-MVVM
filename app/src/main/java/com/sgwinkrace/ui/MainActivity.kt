package com.sgwinkrace.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.lifecycleScope
import com.sgwinkrace.R
import com.sgwinkrace.datastore.AppDataStore
import com.sgwinkrace.ui.dashboard.DashboardActivity
import com.sgwinkrace.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var appDataStore: AppDataStore

    var isLogin = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            appDataStore.isLoginFlow.collect {
                isLogin = it
            }
        }

        Handler(Looper.getMainLooper()).postDelayed(
            {

                if (isLogin) {
                    Intent(this, DashboardActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }

                } else {
                    Intent(this, LoginActivity::class.java).also {
                        startActivity(it)
                        finish()
                }

                }
            }, 2000
        )
    }


}