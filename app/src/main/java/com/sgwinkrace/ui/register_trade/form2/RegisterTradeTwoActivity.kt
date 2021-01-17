package com.sgwinkrace.ui.register_trade.form2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.sgwinkrace.databinding.ActivityRegisterTradeTwoBinding
import com.sgwinkrace.datastore.AppDataStore
import com.sgwinkrace.ui.register_trade.form3.RegisterTradeThreeActivity
import com.sgwinkrace.ui.register_trade.viewmodel.RegisterTradeOneViewModel
import com.sgwinkrace.utils.hideProgressDialog
import com.sgwinkrace.utils.showProgressDialog
import com.sgwinkrace.utils.snackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_register_trade_two.view.*
import kotlinx.android.synthetic.main.header_register_trade_layout.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class RegisterTradeTwoActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterTradeTwoBinding

    val viewModel: RegisterTradeOneViewModel by viewModels()

    @Inject
    lateinit var appDataStore: AppDataStore
    var userId = ""
    var mTradeId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterTradeTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tvHeaderTitle.text = "Register Trade"
        imgHeaderBack.setOnClickListener { finish() }
        tvStepNumber.text = "Step 2/4"
        onClickHandler()

        mTradeId = intent.getStringExtra("trade_id") ?: ""


    }

    private fun onClickHandler() {


                binding.btnNext.setOnClickListener {

                    var pincode = binding.edtPincode.text.toString().trim()
                    var state = binding.edtState.text.toString().trim()
                    var district = binding.edtDistrict.text.toString().trim()
                    var opAddress = binding.edtOperatingAddress.text.toString().trim()
                    var regAddress = binding.edtRegistedAddress.text.toString().trim()
                    var address2 = binding.edtAddress2.text.toString().trim()
                    var comPanNumber = binding.edtCompPanNumber.text.toString().trim()
                    var aadhaarNo = binding.edtAadhaarNumber.text.toString().trim()




                    when {
                        pincode.isNullOrEmpty() -> snackBar(it, "Enter pincode")
                        pincode.length != 6 -> snackBar(it, "Invalid pincode")
                        state.isNullOrEmpty() -> snackBar(it, "Enter state")
                        district.isNullOrEmpty() -> snackBar(it, "Enter district")
                        opAddress.isNullOrEmpty() -> snackBar(it, "Enter operating address")
                        regAddress.isNullOrEmpty() -> snackBar(it, "Enter registered address")
                        address2.isNullOrEmpty() -> snackBar(it, "Enter address 2")
                        comPanNumber.isNullOrEmpty() -> snackBar(it, "Enter company pan number")
                        comPanNumber.length != 10 -> snackBar(it, "Invalid company pan number")
                        aadhaarNo.isNullOrEmpty() -> snackBar(it, "Enter aadhaar number")
                        aadhaarNo.length != 12 -> snackBar(it, "Invalid aadhaar number")

                        else -> {
                            lifecycleScope.launchWhenStarted {

                                appDataStore.userIdFlow.collect {
                                    userId = it
                                }
                            }
                            showProgressDialog(this,"Please wait...")


                            viewModel.tradeStep2(
                                userId,
                                mTradeId,
                                pincode,
                                state,
                                district,
                                opAddress,
                                regAddress,
                                address2,
                                comPanNumber,
                                aadhaarNo,
                                "no"
                            )


                            viewModel.tradeStep2LiveData.observe(this, { response ->

                                hideProgressDialog()
                                if (response.sTATUS == "SUCCESS") {

                                    Intent(this, RegisterTradeThreeActivity::class.java).apply {
                                        putExtra("trade_id", response.iD)
                                        startActivity(this)
                                    }

                                } else {
                                    snackBar(it, response.mESSAGE)
                                }

                            })
                        }


                    }
                }



    }
}