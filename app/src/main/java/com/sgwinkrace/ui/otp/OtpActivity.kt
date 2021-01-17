package com.sgwinkrace.ui.otp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sgwinkrace.databinding.ActivityOtpBinding
import com.sgwinkrace.datastore.AppDataStore
import com.sgwinkrace.ui.dashboard.DashboardActivity
import com.sgwinkrace.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class OtpActivity : AppCompatActivity() {
    lateinit var binding: ActivityOtpBinding
    var mPin = ""
var userId=""
    private val viewModel: OtpViewModel by viewModels()

    @Inject
    lateinit var appDataStore: AppDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClickHandler()
    }

    private fun onClickHandler() {

        binding.btnResendOTP.setOnClickListener {
            myToast("OTP sent")
        }
        inputPin()
        binding.imgQuestion.setOnClickListener {
            this.dialogMsgOk(message = "We have sent an OTP to your mobile number", title = "Help")
        }

        binding.btnSubmit.setOnClickListener {
            mPin = ""
            mPin = binding.edtDigit1.text.toString().trim() + binding.edtDigit2.text.toString()
                .trim() + binding.edtDigit3.text.toString().trim() + binding.edtDigit4.text.toString().trim()

            if (mPin.length != 4){
                snackBar(binding.rootLayout,message = "Enter OTP")
            }else {
                lifecycleScope.launch {
                    appDataStore.userIdFlow.collect {
                        userId=it
                    }
                }
                showProgressDialog(this,"Please wait...")

                viewModel.verifyOtp(userId,mPin)

                viewModel.liveData.observe(this,{
hideProgressDialog()
                    if (it.sTATUS == "SUCCESS") {



                        lifecycleScope.launch {
                            appDataStore.saveIsLogin(true)
                            appDataStore.saveUserMobile(it.dATA.mOBILE)
                            appDataStore.saveUserId(it.dATA.iD)
                        }
                        startNewActivity(DashboardActivity::class.java)
                    }else {
                        snackBar(binding.rootLayout,it.mESSAGE)
                    }
                })
            }

        }
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