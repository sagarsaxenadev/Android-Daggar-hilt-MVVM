package com.sgwinkrace.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.sgwinkrace.databinding.ActivitySignupBinding
import com.sgwinkrace.datastore.AppDataStore
import com.sgwinkrace.datastore.PreferenceStorage
import com.sgwinkrace.network.Status
import com.sgwinkrace.ui.otp.OtpActivity
import com.sgwinkrace.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding
    private val viewModel: SignupViewModel by viewModels()

    @Inject
    lateinit var appDataStore: AppDataStore


    var mPin = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClickHandler()

    }

    private fun onClickHandler() {


        binding.btnRegister.setOnClickListener {
            mPin = ""
            mPin = edtDigit1.text.toString().trim() + edtDigit2.text.toString()
                .trim() + edtDigit3.text.toString().trim() + edtDigit4.text.toString().trim()


            val emailId = binding.edtEmailId.text.toString().trim()
            val mobile = binding.edtMobileNumber.text.toString().trim()


            if (mPin.toString().length != UserConstants.PIN_COUNT) {
                this.snackBar(binding.rootLayout, "Enter PIN")
            } else if (emailId.isNullOrEmpty()) {
                this.snackBar(binding.rootLayout, "Enter Email ID")

            } else if (mobile.isNullOrEmpty()) {
                this.snackBar(binding.rootLayout, "Enter mobile number")

            } else if (mobile.length != 10) {
                snackBar(binding.rootLayout, "Invalid mobile number")

            } else {
                showProgressDialog(this,"Please wait...")

                viewModel.userSignup(emailId, mobile, mPin)

                viewModel.signupLiveData.observe(this, Observer {

                    hideProgressDialog()
                    if (it.sTATUS == "SUCCESS") {



                        lifecycleScope.launch {

                            appDataStore.saveUserEmail(it.dATA.eMAIL)
                            appDataStore.saveUserMobile(it.dATA.mOBILE)
                            appDataStore.saveUserId(it.dATA.iD)

                            appDataStore.isLoginFlow.collect {
                                Log.d("isLogin ", it.toString())
                            }


                        }
                        startNextActivity(OtpActivity::class.java)

                    } else {
                        snackBar(binding.rootLayout, it.mESSAGE)
                    }

                })
            }
        }
        binding.btnGotoLogin.setOnClickListener {
            finish()
        }

        inputPin()
    }

    private fun inputPin() {
        binding.apply {

            edtDigit1.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(text: Editable?) {
                    if (text?.length == 1) {
                        edtDigit2.requestFocus()
                    }

                }

            })

            edtDigit2.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(text: Editable?) {
                    if (text?.length == 1) {
                        edtDigit3.requestFocus()
                    }
                    if (text?.length == 0) {
                        edtDigit1.requestFocus()
                    }
                }
            })

            edtDigit3.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(text: Editable?) {
                    if (text?.length == 1) {
                        edtDigit4.requestFocus()
                    }
                    if (text?.length == 0) {
                        edtDigit2.requestFocus()
                    }
                }
            })

            edtDigit4.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(text: Editable?) {
                    if (text?.length == 1) {

                    }
                    if (text?.length == 0) {
                        edtDigit3.requestFocus()
                    }
                }
            })

        }
    }


}