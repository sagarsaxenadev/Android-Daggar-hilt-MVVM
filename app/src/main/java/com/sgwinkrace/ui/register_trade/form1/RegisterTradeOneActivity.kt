package com.sgwinkrace.ui.register_trade.form1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.sgwinkrace.databinding.ActivityRegisterTradeOneBinding
import com.sgwinkrace.datastore.AppDataStore
import com.sgwinkrace.network.ApiServiceImple
import com.sgwinkrace.ui.dashboard.DashboardActivity
import com.sgwinkrace.ui.register_trade.form2.RegisterTradeTwoActivity
import com.sgwinkrace.ui.register_trade.viewmodel.RegisterTradeOneViewModel
import com.sgwinkrace.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.header_register_trade_layout.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
@AndroidEntryPoint
class RegisterTradeOneActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterTradeOneBinding

    val viewModel: RegisterTradeOneViewModel by viewModels()

    @Inject
    lateinit var appDataStore: AppDataStore
    var userId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterTradeOneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tvHeaderTitle.text = "Register Trade"
        imgHeaderBack.setOnClickListener { finish() }
        tvStepNumber.text = "Step 1/4"

        onClickHandler()
    }

    private fun onClickHandler() {

        binding.btnNext.setOnClickListener {
            var mCompanyName = binding.edtCompanyName.text.toString().trim()
            var mName = binding.edtKetPersonName.text.toString().trim()
            var mGst = binding.edtGst.text.toString().trim()
            var mEmail = binding.edtKeyPEmail.text.toString().trim()
            var mMobileNumber = binding.edtKeyPMobile.text.toString().trim()


            when {

                mCompanyName.isNullOrEmpty() -> this.snackBar(it, "Enter company name")
                mName.isNullOrEmpty() -> this.snackBar(it, "Enter person name")
                mGst.isNullOrEmpty() -> this.snackBar(it, "Enter GST")
                mEmail.isNullOrEmpty() -> this.snackBar(it, "Enter key email")
                mMobileNumber.isNullOrEmpty() -> this.snackBar(it, "Enter key mobile number")
                mMobileNumber.length !=10 -> this.snackBar(it, "Enter valid key mobile number")
                Utils.isValidEmail(mEmail) == false -> this.snackBar(it, "Enter valid email")

                else -> {
                    lifecycleScope.launchWhenStarted {

                        appDataStore.userIdFlow.collect {
                            userId = it
                        }
                    }
                    showProgressDialog(this,"Please wait...")

                    viewModel.tradeStep1(
                        userId,
                        mEmail,
                        "Dealer",
                        mName,
                        mEmail,
                        mMobileNumber,
                        mGst
                    )
                    viewModel.tradeStep1LiveData.observe(this,{response->
hideProgressDialog()
                        if (response.sTATUS == "SUCCESS") {

                            Intent(this,RegisterTradeTwoActivity::class.java).apply {
                                putExtra("trade_id",response.iD)
                                startActivity(this)
                            }

                        }else {
                            snackBar(it,response.mESSAGE)
                        }

                    })

                }

            }
        }

    }

}