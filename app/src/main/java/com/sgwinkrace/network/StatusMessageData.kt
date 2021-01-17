package com.sgwinkrace.network


import com.google.gson.annotations.SerializedName

data class StatusMessageData(
    @SerializedName("MESSAGE")
    val mESSAGE: String,
    @SerializedName("STATUS")
    val sTATUS: String
)