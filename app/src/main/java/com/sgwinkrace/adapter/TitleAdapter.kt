package com.sgwinkrace.adapter


import android.app.Activity
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sgwinkrace.R
import kotlinx.android.synthetic.main.item_title.view.*


class TitleAdapter(private val context: Activity, private val chaptersList: List<String?>?) :
    RecyclerView.Adapter<TitleAdapter.ViewHolder>() {
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
                R.layout.item_title,
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

            if (selectedItem == position) {
                tvTitle.setTypeface(tvTitle.getTypeface(), Typeface.BOLD);
                tvTitle.textSize = 15f
            }else{
                tvTitle.textSize = 14f
                tvTitle.setTypeface(tvTitle.getTypeface(), Typeface.NORMAL);


            }


            itemView.setOnClickListener {

                lastSelected = selectedItem
                selectedItem = position
                notifyItemChanged(lastSelected)
                notifyItemChanged(selectedItem)

                tvTitle.textSize = 16f


notifyDataSetChanged()
            }


            tvTitle.text = chaptersList!![position]
        }


    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvTitle = view.tvTitle


    }
}