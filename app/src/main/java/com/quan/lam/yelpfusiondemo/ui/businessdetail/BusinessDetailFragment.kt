package com.quan.lam.yelpfusiondemo.ui.businessdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.quan.lam.yelpfusiondemo.R
import com.quan.lam.yelpfusiondemo.data.BusinessDetail
import com.squareup.picasso.Picasso

class BusinessDetailFragment(val businessId: String) : Fragment() {
    companion object {
        fun newInstance(businessId: String) =
            BusinessDetailFragment(businessId)
    }

    private lateinit var viewModel: BusinessDetailViewModel
    private lateinit var layout: ConstraintLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.business_detail_fragment, container, false)
        layout = view.findViewById(R.id.businessDetailLayout)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BusinessDetailViewModel::class.java)
        viewModel.onFragmentStart(layout, businessId)

        val businessDetailObserver = Observer<BusinessDetail> {
            layout.findViewById<TextView>(R.id.nameText).text = it.name
            layout.findViewById<TextView>(R.id.locationText).text = "${it.location.address1}, ${it.location.city}, ${it.location.state}, ${it.location.country} ${it.location.zip_code}"
            layout.findViewById<TextView>(R.id.reviewCount).text = it.review_count.toString()
            layout.findViewById<TextView>(R.id.ratingText).text = it.rating.toString()
            layout.findViewById<TextView>(R.id.priceText).text = it.price
            val imageView = layout.findViewById<ImageView>(R.id.detailBusinessImage)
            if (it.image_url.isNotEmpty()) {
                Picasso.get().load(it.image_url).into(imageView)
                imageView.visibility = View.VISIBLE
            } else {
                imageView.visibility = View.INVISIBLE
            }
        }

        viewModel.businessDetail.observe(viewLifecycleOwner, businessDetailObserver)
    }

}
