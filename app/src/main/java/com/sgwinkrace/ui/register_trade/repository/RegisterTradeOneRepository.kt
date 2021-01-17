package com.sgwinkrace.ui.register_trade.repository

import com.sgwinkrace.model.OtpData
import com.sgwinkrace.network.ApiServiceImple
import com.sgwinkrace.network.RegisterTadeOneData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RegisterTradeOneRepository
@Inject
constructor(val apiServiceImple: ApiServiceImple){


    fun step1(
        id: String,
        email: String,
        industry_category: String,
        person_name: String,
        person_email: String,
        person_mobile: String,
        gst_number: String
    ) = flow {
        emit( apiServiceImple.tradeStep1(
            id,email,industry_category,person_name,person_email,person_mobile,gst_number
        ))

    }.flowOn(Dispatchers.IO)



    fun step2(
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
    ) = flow {
        emit( apiServiceImple.tradeStep2(
            id,register_trade_id,pincode,state,district, op_address, reg_address, address2, com_pan, aadhaar, litigation
        ))

    }.flowOn(Dispatchers.IO)


    fun step3(
        id: String,
        register_trade_id: String,
        docs: String
    ) = flow {
        emit( apiServiceImple.tradeStep3(
            id,register_trade_id,docs))

    }.flowOn(Dispatchers.IO)


    fun step4(
        id: String,
        register_trade_id: String,
        bankname:String,
        branch:String,
        acc_holder_name:String,
        acc_no:String,
        acc_type:String,
        ifsc:String
    ) = flow {
        emit( apiServiceImple.tradeStep4(
           id, register_trade_id, bankname, branch, acc_holder_name, acc_no, acc_type, ifsc
        ))

    }.flowOn(Dispatchers.IO)


}