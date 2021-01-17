package com.sgwinkrace.ui.register_trade.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sgwinkrace.model.OtpData
import com.sgwinkrace.network.RegisterTadeOneData
import com.sgwinkrace.ui.register_trade.repository.RegisterTradeOneRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterTradeOneViewModel @ViewModelInject constructor(val registerTradeOneRepository: RegisterTradeOneRepository):ViewModel(){

    private var _tradeStep1LiveData = MutableLiveData<RegisterTadeOneData>()

    val tradeStep1LiveData: LiveData<RegisterTadeOneData>
        get() = _tradeStep1LiveData

    private var _tradeStep2LiveData = MutableLiveData<RegisterTadeOneData>()

    val tradeStep2LiveData: LiveData<RegisterTadeOneData>
        get() = _tradeStep2LiveData

    private var _tradeStep3LiveData = MutableLiveData<RegisterTadeOneData>()

    val tradeStep3LiveData: LiveData<RegisterTadeOneData>
        get() = _tradeStep3LiveData


    private var _tradeStep4LiveData = MutableLiveData<RegisterTadeOneData>()

    val tradeStep4LiveData: LiveData<RegisterTadeOneData>
        get() = _tradeStep4LiveData


    fun tradeStep1(
        id: String,
        email: String,
        industry_category: String,
        person_name: String,
        person_email: String,
        person_mobile: String,
        gst_number: String
    ) {
        viewModelScope.launch {
            registerTradeOneRepository.step1(
                id,email,industry_category,person_name,person_email,person_mobile,gst_number
            ).collect {
                _tradeStep1LiveData.value=it
            }

        }
    }


    fun tradeStep2(
        id: String,
        register_trade_id: String,
        pincode: String,
        state: String,
        district: String,
        op_address: String,
        reg_address: String,
        address2: String,
        com_pan: String,
        aadhaar: String,
        litigation: String
    ) {
        viewModelScope.launch {
            registerTradeOneRepository.step2(
                id,register_trade_id, pincode, state, district, op_address, reg_address, address2, com_pan, aadhaar, litigation
            ).collect {
               _tradeStep2LiveData.value=it
            }

        }
    }

  fun tradeStep3(
        id: String,
        register_trade_id: String,
        docs: String

    ) {
        viewModelScope.launch {
            registerTradeOneRepository.step3(
                id,register_trade_id,docs).collect {
               _tradeStep3LiveData.value=it
            }

        }
    }


  fun tradeStep4(
      id: String,
      register_trade_id: String,
      bankname:String,
      branch:String,
      acc_holder_name:String,
      acc_no:String,
      acc_type:String,
      ifsc:String

    ) {
        viewModelScope.launch {
            registerTradeOneRepository.step4(
                id, register_trade_id, bankname, branch, acc_holder_name, acc_no, acc_type,ifsc
            ).collect {
               _tradeStep4LiveData.value=it
            }

        }
    }


}