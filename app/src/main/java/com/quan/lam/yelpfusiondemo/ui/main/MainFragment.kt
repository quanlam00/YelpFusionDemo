package com.quan.lam.yelpfusiondemo.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quan.lam.yelpfusiondemo.MainActivity
import com.quan.lam.yelpfusiondemo.R
import com.quan.lam.yelpfusiondemo.ui.businessdetail.BusinessDetailFragment
import io.reactivex.disposables.Disposable

/**
 * Main Fragment of the app.
 */
class MainFragment : Fragment(), OnBusinessItemClickListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var searchButton: Button
    private lateinit var searchText: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var locationText: TextView
    private lateinit var recyclerViewAdapter: BusinessListAdapter
    private var locationDisposable: Disposable? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        searchButton = view.findViewById(R.id.searchButton)
        searchText = view.findViewById(R.id.editText)
        recyclerView = view.findViewById(R.id.recyclerView)
        locationText = view.findViewById(R.id.locationText)
        searchButton.setOnClickListener {
            viewModel.getBusinesses(searchText.text.toString(), locationText.text.toString())

            //Hide Keyboard
            val inputManager: InputMethodManager =
                context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                activity?.currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )

            Toast.makeText(context, "Loading...", Toast.LENGTH_LONG).show()
        }


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val businessesListObserver = Observer<List<BusinessListItem>> {
            recyclerViewAdapter.notifyItemRangeChanged(0, viewModel.getItemCount())
        }

        viewModel.businessListLiveData.observe(viewLifecycleOwner, businessesListObserver)

        // Set the adapter
        recyclerViewAdapter = BusinessListAdapter(viewModel.businessList, this)
        with(recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerViewAdapter
        }

        //Add Infinite Scrolling
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.getBusinesses(searchText.text.toString(), locationText.text.toString(), true)
                }
            }
        })
        getLastKnownLocation()
    }

    //Get Last Known location from system
    private fun getLastKnownLocation() {
        locationDisposable = (activity as MainActivity).getLastKnownLocation().subscribe(
            {location ->
                viewModel.lastKnowLocation = location
            },
            {error-> onError(error)}
        )
    }

    private fun onError(error: Throwable) {
        Log.e("MainFragment", error.toString())
        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
        locationDisposable?.dispose()
    }

    //Callback when an item on the list is selected
    override fun onInteraction(item: BusinessListItem?) {
        val detailsFragment =
            BusinessDetailFragment.newInstance(item?.id ?:"")

        if (activity!=null)
            requireActivity().supportFragmentManager
            .beginTransaction()
            // 2
            .replace(R.id.container, detailsFragment, "businessDetail")
            // 3
            .addToBackStack(null)
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        //No call for super(). Bug on API Level > 11.
        //Work around for a OS bug
    }
}
