package com.quan.lam.yelpfusiondemo

import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.quan.lam.yelpfusiondemo.ui.main.MainFragment
import io.reactivex.Single

const val PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION: Int = 1

/**
 * MainActivity will be a gateway to the system's resource.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    fun getLastKnownLocation(): Single<Location> {
        return if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED) {
            Single.create { emitter ->
                fusedLocationClient.lastLocation.addOnSuccessListener {location ->
                    emitter.onSuccess(location)
                }
            }
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
            ) {
                Single.create { emitter -> emitter.onError(Throwable("Permission Denied.")) }
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
                    PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION
                )
                Single.create { emitter -> emitter.onError(Throwable("Permission Denied. Try again after permission granted.")) }
            }
        }
    }
}
