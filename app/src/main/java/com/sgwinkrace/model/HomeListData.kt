package com.sgwinkrace.model


import com.google.gson.annotations.SerializedName

data class HomeListData(
    @SerializedName("MESSAGE")
    val mESSAGE: String,
    @SerializedName("POST_LIST")
    val pOSTLIST: List<POSTLIST>,
    @SerializedName("STATUS")
    val sTATUS: String
) {
    data class POSTLIST(
        @SerializedName("color")
        val color: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("price")
        val price: String,
        @SerializedName("product_category")
        val productCategory: String,
        @SerializedName("product_condition")
        val productCondition: String,
        @SerializedName("product_packaging")
        val productPackaging: String,
        @SerializedName("product_quality")
        val productQuality: String,
        @SerializedName("product_type")
        val productType: String,
        @SerializedName("product_uses")
        val productUses: String,
        @SerializedName("state")
        val state: String,
        @SerializedName("type")
        val type: String,
        @SerializedName("wash")
        val wash: String
    )
}