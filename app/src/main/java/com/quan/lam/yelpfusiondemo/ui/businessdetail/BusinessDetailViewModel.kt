package com.quan.lam.yelpfusiondemo.ui.businessdetail

import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.quan.lam.yelpfusiondemo.data.BusinessDetail
import com.quan.lam.yelpfusiondemo.data.YelpFusionAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class BusinessDetailViewModel : ViewModel() {
    private var disposable: Disposable? = null
    val businessDetail = MutableLiveData<BusinessDetail>()
    fun onFragmentStart(layout: ConstraintLayout, businessId: String) {
        layout.visibility = View.INVISIBLE
        disposable = YelpFusionAPI.getAPI().getBusinessDetail(businessId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                businessDetail.value = it
                layout.visibility = View.VISIBLE
            }, {
                businessDetail.value = BusinessDetail(name = "Error Getting Business Detail")
                Log.e("BusinessDetailModel", it.toString())
            })
    }
}

