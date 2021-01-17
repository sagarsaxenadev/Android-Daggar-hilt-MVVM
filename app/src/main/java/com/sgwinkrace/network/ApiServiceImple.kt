package com.sgwinkrace.network

import com.sgwinkrace.model.HomeListData
import com.sgwinkrace.model.LoginData
import com.sgwinkrace.model.OtpData
import com.sgwinkrace.model.SignupData
import javax.inject.Inject

class ApiServiceImple @Inject constructor(val apiService: ApiService) {

    suspend fun signupUser(emai: String, mobile: String, pin: String): SignupData {
        return apiService.getData(
            email = emai,
            pin = pin,
            mobile = mobile
        )
    }

    suspend fun verifyOtp(id: String, otp: String): OtpData {
        return apiService.otpVerifyOTP(
            id = id,
            otp = otp
        )
    }

    suspend fun login(key: String, pin: String): LoginData {
        return apiService.login(
            eMAIL_OR_PHONE = key,
            pIN = pin
        )
    }

    suspend fun homePost(id: String, type: String): HomeListData {
        return apiService.homePost(
            id = id,
            type = type
        )
    }

    suspend fun tradeStep1(
        id: String,
        email: String,
        industry_category: String,
        person_name: String,
        person_email: String,
        person_mobile: String,
        gst_number: String
    ): RegisterTadeOneData {
        return apiService.step1(
            eMAIL = email,
            iNDUSTRY_CATEGORY = industry_category,
            kEY_PERSON_EMAIL = person_email,
            kEY_PERSON_MOBILE = person_mobile,
            kEY_PERSON_NAME = person_name,
            gST_NUMBER = gst_number,
            id = id

        )
    }
    suspend fun tradeStep2(
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
    ): RegisterTadeOneData {
        return apiService.step2(
            id = id,
            rEGISTER_ADDRESS = reg_address,
            rEGISTRATION_TRADE_ID = register_trade_id,
            cOMPANY_PAN_NUMBER = com_pan,
            dISTRICT = district,
            lITIGATION = litigation,
            aDDRESS2 = address2,
            aDHAR_CARD_NUMBER = aadhaar,
            oPERATING_ADDRESS = op_address,
            sTATE = state,
            pINCODE = pincode

        )
    }


    suspend fun tradeStep3(
        id: String,
        register_trade_id: String,
        docs:String
    ): RegisterTadeOneData {
        return apiService.step3(
            id = id,
            rEGISTRATION_TRADE_ID= register_trade_id,
            docs = docs


        )
    }

    suspend fun tradeStep4(
        id: String,
        register_trade_id: String,
        bankname:String,
        branch:String,
        acc_holder_name:String,
        acc_no:String,
        acc_type:String,
        ifsc:String
    ): RegisterTadeOneData {
        return apiService.step4(
             id,
             register_trade_id,
            bankname,
            branch,
            acc_holder_name,
            acc_no,
            acc_type,
            ifsc



        )
    }
}