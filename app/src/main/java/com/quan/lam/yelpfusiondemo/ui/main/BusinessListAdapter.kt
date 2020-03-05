package com.quan.lam.yelpfusiondemo.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.quan.lam.yelpfusiondemo.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.business_list_item.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BusinessListAdapter(
    private val mValues: List<BusinessListItem>,
    private val mListener: OnBusinessItemClickListener?
) : RecyclerView.Adapter<BusinessListAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as BusinessListItem
            mListener?.onInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.business_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mNameText.text = item.name
        holder.mRatingText.text = item.rating.toString()
        if (item.image_url.isNotEmpty()) {
            holder.mImage.visibility = View.VISIBLE
            Picasso.get().load(item.image_url).into(holder.mImage)
        } else {
            holder.mImage.visibility = View.INVISIBLE
        }

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mNameText: TextView = mView.businessName
        val mRatingText: TextView = mView.businessRating
        val mImage: ImageView = mView.businessImage
    }
}
