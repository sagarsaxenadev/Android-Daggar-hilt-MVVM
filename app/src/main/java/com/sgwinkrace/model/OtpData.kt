package com.sgwinkrace.model


import com.google.gson.annotations.SerializedName

data class OtpData(
    @SerializedName("DATA")
    val dATA: DATA,
    @SerializedName("MESSAGE")
    val mESSAGE: String,
    @SerializedName("STATUS")
    val sTATUS: String
) {
    data class DATA(
        @SerializedName("ID")
        val iD: String,
        @SerializedName("MOBILE")
        val mOBILE: String
    )
}