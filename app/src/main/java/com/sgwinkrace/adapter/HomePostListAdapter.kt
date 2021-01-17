package com.sgwinkrace.adapter


import android.app.Activity
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sgwinkrace.R
import com.sgwinkrace.model.HomeListData
import kotlinx.android.synthetic.main.item_home_post.view.*
import kotlinx.android.synthetic.main.item_title.view.*


class HomePostListAdapter(
    private val context: Activity,
    private val chaptersList: List<HomeListData.POSTLIST>
) :
    RecyclerView.Adapter<HomePostListAdapter.ViewHolder>() {
    var click: onClick? = null
    var selectedItem = 0
    var lastSelected = 0


    public interface onClick {

        fun clickDeleteItem(cartId: String, position: Int)
        fun updateQty(productId: String, priceId: String, position: Int, apiType: String)
    }

    fun setClickListner(onClick: onClick) {
        click = onClick

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_home_post,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {

        if (chaptersList?.size!! == 0) {
            return 0

        }

        if (chaptersList == null) {
            return 0
        }
        return chaptersList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.apply {


            tvProductName.text =
                "Product Category: ${chaptersList!![position].productCategory} - ${chaptersList!![position].state}"
            tvColor.text = "Color: ${chaptersList!![position].color}"
            tvProductType.text = "Product Type: ${chaptersList!![position].productType}"
            tvprice.text = "Price: ${chaptersList!![position].price}"
            tvUserId.text = "RACEP${chaptersList!![position].id}"
            tvDate.text = "Updated on 03/01/2021"

            Glide.with(context)
                .load("https://www.jdogjunkremoval.com/wp-content/uploads/2017/05/scrap-metal-540x540.jpg")
                .placeholder(R.drawable.bg_placeholder)
                .into(imgProduct)
        }


    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvProductName = view.tvProductName
        val tvProductType = view.tvProductType
        val tvColor = view.tvColor
        val tvprice = view.tvprice
        val tvUserId = view.tvUserId
        val tvDate = view.tvDate
        val imgProduct = view.imgProduct


    }
}