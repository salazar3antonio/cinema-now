package com.example.moviefinder.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviefinder.R
import com.example.moviefinder.adapters.TheaterListAdapter
import com.example.moviefinder.databinding.FragmentTheatersNearMeBinding
import com.example.moviefinder.utils.Constants
import com.example.moviefinder.view_models.MovieViewModel
import com.google.android.gms.location.*
import timber.log.Timber

class TheatersNearMeFragment : Fragment() {

    private val mMovieViewModel: MovieViewModel by lazy {
        ViewModelProvider(this).get(MovieViewModel::class.java)
    }

    private lateinit var mLocationCallback: LocationCallback
    private lateinit var mLocationProviderClient: FusedLocationProviderClient
    private lateinit var mTheaterListAdapter: TheaterListAdapter
    private var mUserOrigin = String()
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Timber.i("GPS Permission is Granted")
                searchForTheatersWithUserLocation()
            } else {
                Timber.i("GPS Permission is Denied")
                Toast.makeText(context, "You can also search by ZIP code.", Toast.LENGTH_LONG).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        mTheaterListAdapter = TheaterListAdapter(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentTheatersNearMeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_theaters_near_me, container, false)

        val theaterListRecyclerView = binding.rvTheaterList
        theaterListRecyclerView.layoutManager = LinearLayoutManager(context)
        theaterListRecyclerView.adapter = mTheaterListAdapter

        val zipCode = binding.etZipCode

        binding.lifecycleOwner = this

        binding.btnSearchForTheaters.setOnClickListener {

            mUserOrigin = zipCode.text.toString()

            if (mUserOrigin.isEmpty()) {
                Toast.makeText(
                    context,
                    getString(R.string.toast_text_validate_zip_code),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                mMovieViewModel.callTheaterSearchAPI(mUserOrigin)
            }
        }

        binding.btnMyLocation.setOnClickListener {
                onClickRequestPermission()
        }

        mMovieViewModel.mTheaterSearchResults.observe(viewLifecycleOwner, Observer {
            mTheaterListAdapter.mTheaterList = it
        })

        return binding.root

    }

    fun onClickRequestPermission() {

        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                searchForTheatersWithUserLocation()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }

            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }


        }

    }

    @SuppressLint("MissingPermission")
    fun searchForTheatersWithUserLocation() {
        mLocationProviderClient.lastLocation.addOnSuccessListener { deviceLocation ->

            if (deviceLocation == null) {
                Toast.makeText(
                    context,
                    "Make sure GPS is enabled on your device and try again.",
                    Toast.LENGTH_LONG
                ).show()

                requestUserLocationUpdate()

            } else {
                Timber.i("LOCATION: " + deviceLocation.latitude + ", " + deviceLocation.longitude)
                mUserOrigin =
                    deviceLocation.latitude.toString() + "," + deviceLocation.longitude.toString()
                mMovieViewModel.callTheaterSearchAPI(mUserOrigin)
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun requestUserLocationUpdate() {

        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    mUserOrigin = location.latitude.toString() + "," + location.longitude.toString()
                    mMovieViewModel.callTheaterSearchAPI(mUserOrigin)
                    mLocationProviderClient.removeLocationUpdates(this)
                }
            }
        }

        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        mLocationProviderClient.requestLocationUpdates(
            locationRequest,
            mLocationCallback,
            Looper.getMainLooper()
        )
    }

}

