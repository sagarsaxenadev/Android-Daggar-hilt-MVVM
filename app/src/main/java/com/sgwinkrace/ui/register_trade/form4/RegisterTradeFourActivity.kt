package com.sgwinkrace.ui.register_trade.form4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sgwinkrace.databinding.ActivityRegisterTradeFourBinding
import com.sgwinkrace.datastore.AppDataStore
import com.sgwinkrace.ui.dashboard.DashboardActivity
import com.sgwinkrace.ui.register_trade.form2.RegisterTradeTwoActivity
import com.sgwinkrace.ui.register_trade.viewmodel.RegisterTradeOneViewModel
import com.sgwinkrace.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_register_trade_two.view.*
import kotlinx.android.synthetic.main.header_register_trade_layout.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegisterTradeFourActivity : AppCompatActivity() {

    var mTradeId = ""
    lateinit var binding: ActivityRegisterTradeFourBinding

    @Inject
    lateinit var appDataStore: AppDataStore
    var userId = ""
    val viewModel: RegisterTradeOneViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterTradeFourBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tvHeaderTitle.text = "Register Trade"
        imgHeaderBack.setOnClickListener { finish() }
        tvStepNumber.text = "Step 4/4"

        mTradeId = intent.getStringExtra("trade_id") ?: ""

        onClickHandler()

    }

    private fun onClickHandler() {

        binding.tvSkip.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Congratulations")
                .setMessage("You have registered successfully")
                .setPositiveButton(
                    "Ok"
                ) { dialogInterface, i ->

                    startNewActivity(DashboardActivity::class.java)
                    dialogInterface.dismiss()
                }.show()
        }


        binding.btnNext.setOnClickListener {
            var accountHolderName = binding.edtAccountHolderName.text.toString().trim()
            var bankName = binding.edtBankName.text.toString().trim()
            var branch = binding.edtBranch.text.toString().trim()
            var ifscCode = binding.edtIfscCode.text.toString().trim()
            var accountNumber = binding.edtAccountNumber.text.toString().trim()

            when {
                accountHolderName.isNullOrEmpty() -> snackBar(it, "Enter account holder name")
                bankName.isNullOrEmpty() -> snackBar(it, "Enter bank name")
                branch.isNullOrEmpty() -> snackBar(it, "Enter branch")
                ifscCode.isNullOrEmpty() -> snackBar(it, "Enter IFSC code")
                accountNumber.isNullOrEmpty() -> snackBar(it, "Enter account number")
                ifscCode.length != 11 -> snackBar(it, "Invalid IFSC code")

                else -> {
                    lifecycleScope.launchWhenStarted {

                        appDataStore.userIdFlow.collect {
                            userId = it
                        }
                    }
                    showProgressDialog(this,"Please wait...")

                    viewModel.tradeStep4(
                        userId,
                        mTradeId,
                        bankName,
                        branch,
                        accountHolderName,
                        accountNumber,
                        "saving",
                        ifscCode
                    )
                    viewModel.tradeStep4LiveData.observe(this, { _response ->
                        hideProgressDialog()
                        if (_response.sTATUS == "SUCCESS") {

                            lifecycleScope.launch {
                                appDataStore.saveProfileComplete(true)

                            }

                            MaterialAlertDialogBuilder(this)
                                .setTitle("Congratulations")
                                .setMessage("${_response.mESSAGE}")
                                .setPositiveButton(
                                    "Ok"
                                ) { dialogInterface, i ->

                                    startNewActivity(DashboardActivity::class.java)
                                    dialogInterface.dismiss()
                                }.show()


                        } else {
                            snackBar(it, _response.mESSAGE)
                        }


                    })
                }
            }

        }
    }
}