package com.sgwinkrace.network

import android.service.autofill.UserData
import com.sgwinkrace.model.HomeListData
import com.sgwinkrace.model.LoginData
import com.sgwinkrace.model.OtpData
import com.sgwinkrace.model.SignupData
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @POST("app_signup")
    @FormUrlEncoded
    suspend fun getData(
        @Field("EMAIL") email :String,
        @Field("PIN") pin :String,
        @Field("MOBILE") mobile :String
    ) : SignupData

    @POST("appotpVerification")
    @FormUrlEncoded
    suspend fun otpVerifyOTP(
        @Field("ID") id :String,
        @Field("OTP") otp :String
    ) : OtpData

    @POST("app_login")
    @FormUrlEncoded
    suspend fun login(
        @Field("EMAIL_OR_PHONE") eMAIL_OR_PHONE :String,
        @Field("PIN") pIN :String
    ) : LoginData


    @POST("post_list")
    @FormUrlEncoded
    suspend fun homePost(
        @Field("ID") id :String,
        @Field("TYPE") type :String //Buy or Sell
    ) : HomeListData




    @POST("register_trade_step1")
    @FormUrlEncoded
    suspend fun step1(
        @Field("ID") id :String,
        @Field("EMAIL") eMAIL :String,
        @Field("INDUSTRY_CATEGORY") iNDUSTRY_CATEGORY :String,
        @Field("KEY_PERSON_NAME") kEY_PERSON_NAME :String,
        @Field("KEY_PERSON_EMAIL") kEY_PERSON_EMAIL :String,
        @Field("KEY_PERSON_MOBILE") kEY_PERSON_MOBILE :String,
        @Field("GST_NUMBER") gST_NUMBER :String
    ) : RegisterTadeOneData



    @POST("register_trade_step2")
    @FormUrlEncoded
    suspend fun step2(
        @Field("ID") id :String,
        @Field("REGISTRATION_TRADE_ID") rEGISTRATION_TRADE_ID :String,
        @Field("STATE") sTATE :String,
        @Field("DISTRICT") dISTRICT :String,
        @Field("OPERATING_ADDRESS") oPERATING_ADDRESS :String,
        @Field("REGISTER_ADDRESS") rEGISTER_ADDRESS :String,
        @Field("ADDRESS2") aDDRESS2 :String,
        @Field("COMPANY_PAN_NUMBER") cOMPANY_PAN_NUMBER :String,
        @Field("ADHAR_CARD_NUMBER") aDHAR_CARD_NUMBER :String,
        @Field("PINCODE") pINCODE :String,
        @Field("LITIGATION") lITIGATION :String
    ) : RegisterTadeOneData

    @POST("register_trade_step3")
    @FormUrlEncoded
    suspend fun step3(
        @Field("ID") id :String,
        @Field("REGISTRATION_TRADE_ID") rEGISTRATION_TRADE_ID :String,
        @Field("DOCS") docs :String
    ) : RegisterTadeOneData




    @POST("register_trade_step4")
    @FormUrlEncoded
    suspend fun step4(
        @Field("ID") id :String,
        @Field("REGISTRATION_TRADE_ID") rEGISTRATION_TRADE_ID :String,
        @Field("BANK_NAME") BANK_NAME :String,
        @Field("BRANCH") BRANCH :String,
        @Field("ACCOUNT_NAME") ACCOUNT_NAME :String,
        @Field("ACCOUNT_NUMBER") ACCOUNT_NUMBER :String,
        @Field("ACCOUNT_TYPE") ACCOUNT_TYPE :String,
        @Field("IFSC") IFSC :String

    ) : RegisterTadeOneData


    
}