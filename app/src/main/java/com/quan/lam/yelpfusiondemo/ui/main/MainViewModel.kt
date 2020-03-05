package com.quan.lam.yelpfusiondemo.ui.main

import androidx.lifecycle.ViewModel
import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.quan.lam.yelpfusiondemo.BuildConfig
import com.quan.lam.yelpfusiondemo.data.YelpBusinessResponse
import com.quan.lam.yelpfusiondemo.data.YelpFusionAPI
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * ViewModel of MainFragment
 * Will hold the list of returned businesses related logic
 */
class MainViewModel : ViewModel() {
    var lastKnowLocation: Location? = null
    private var disposable: Disposable? = null
    var businessList = ArrayList<BusinessListItem>()
    var businessListLiveData: MutableLiveData<List<BusinessListItem>> = MutableLiveData(businessList)
    //A work around to be able to unit testing around AndroidSchedulers.mainThread()
    private var scheduler = AndroidSchedulers.mainThread()
    fun getBusinesses(searchTerm: String, location: String, loadMore: Boolean = false)  {
        val offset = if (loadMore) {
            businessList.size
        } else {
            0
        }
        val searchJob: Observable<YelpBusinessResponse> = if (location == "Current Location" || location.isEmpty()) {
            YelpFusionAPI.getAPI().searchBusinesses(
                searchTerm, lastKnowLocation?.latitude ?: 0.0, lastKnowLocation?.longitude ?: 0.0, offset)
        } else {
            YelpFusionAPI.getAPI().searchBusinesses(searchTerm, location, offset)
        }



        disposable = searchJob.subscribeOn(Schedulers.io())
                .observeOn(scheduler).subscribe(
                    { businessResponse ->
                        if (!loadMore) businessList.clear()
                        businessResponse.businesses.forEach {
                            businessList.add(BusinessListItem(it.name, it.image_url, it.rating, it.id))
                        }
                        businessListLiveData.value = businessList
                    }, {
                        Log.e("MainViewModel", it.toString())
                    })
    }

    fun getItemCount(): Int {
        return businessList.size
    }
    fun onDestroy() {
        disposable?.dispose()
    }

    fun setScheduler(s: Scheduler) {
        if (BuildConfig.DEBUG)
            scheduler = s
    }
}
