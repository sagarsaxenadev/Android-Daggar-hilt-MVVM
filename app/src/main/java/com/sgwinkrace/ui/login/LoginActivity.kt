package com.sgwinkrace.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sgwinkrace.databinding.ActivityLoginBinding
import com.sgwinkrace.datastore.AppDataStore
import com.sgwinkrace.ui.dashboard.DashboardActivity
import com.sgwinkrace.ui.signup.SignupActivity
import com.sgwinkrace.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()


    @Inject
    lateinit var appDataStore: AppDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClickHandler()

    }

    private fun onClickHandler() {
        binding.btnGotoSignUp.setOnClickListener {
            Intent(this, SignupActivity::class.java).also { startActivity(it) }
        }

        binding.btnLogin.setOnClickListener {
            val mKey = binding.edtMobileOrEmail.text.toString().trim()
            val mPin = binding.edtPin.text.toString().trim()

            if (mKey.isNullOrEmpty())
                snackBar(it, "Enter mobile number / Email")
            else if (mPin.isNullOrEmpty())
                snackBar(it, "Enter Password")
            else if (mPin.length != UserConstants.PIN_COUNT)
                snackBar(it, "Invalid password")
            else {
showProgressDialog(this,"Please wait...")
                viewModel.login(mKey, mPin)
                viewModel.loginLiveData.observe(this, {

                    hideProgressDialog()
                    if (it.sTATUS == "SUCCESS") {

                        lifecycleScope.launch {
                            appDataStore.saveIsLogin(true)
                            appDataStore.saveUserMobile(it.dATA.mOBILE)
                            appDataStore.saveUserEmail(it.dATA.eMAIL)
                            appDataStore.saveUserId(it.dATA.iD)
                        }

                        startNewActivity(DashboardActivity::class.java)

                    } else {
                        snackBar(binding.rootLayout, it.mESSAGE)
                    }
                })

            }
        }
    }
}