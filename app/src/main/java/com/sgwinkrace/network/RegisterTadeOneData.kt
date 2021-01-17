package com.sgwinkrace.network


import com.google.gson.annotations.SerializedName

data class RegisterTadeOneData(
    @SerializedName("ID")
    val iD: String,
    @SerializedName("MESSAGE")
    val mESSAGE: String,
    @SerializedName("STATUS")
    val sTATUS: String
)