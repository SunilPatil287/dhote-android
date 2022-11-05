package com.iitms.bfr.ui.view.activity

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.gms.location.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.iitms.bfr.data.model.UserInfo
import com.iitms.bfr.databinding.HomeActivityBinding
import com.iitms.bfr.ui.base.BaseActivity
import com.iitms.bfr.ui.util.GpsTracker
import com.iitms.bfr.R
import com.iitms.bfr.ui.viewModel.HomeActivityViewModel
import kotlinx.android.synthetic.main.layout_bottom_nav.view.*
import java.io.IOException
import java.util.*


class HomeActivity : BaseActivity<HomeActivityViewModel, HomeActivityBinding>() {

    private lateinit var controller: NavController // don't forget to initialize
    var userInfo :UserInfo? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var locationCallback: LocationCallback? = null
    private var locationRequest: LocationRequest? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        controller = findNavController(R.id.nav_host_fragment)

        if(intent.getStringExtra("IntentNavigation") != null){
            if(intent.getStringExtra("IntentNavigation") == "Order") {
                val bundle = Bundle()
                bundle.putString("Amount", intent.getStringExtra("Amount"))
                navigate(R.id.nav_host_fragment, R.id.navigation_order_status, bundle)
            } else if(intent.getStringExtra("IntentNavigation") == "Registration") {

            }
        }

        observe()
        val bottomBarBackground = binding.navLayout.bottomBar.background as MaterialShapeDrawable
        bottomBarBackground.shapeAppearanceModel = bottomBarBackground.shapeAppearanceModel
            .toBuilder()
            .setTopRightCorner(CornerFamily.ROUNDED, 70.0f)
            .setTopLeftCorner(CornerFamily.ROUNDED, 70.0f)
            .build()
        binding.navLayout.bottomNavigation.setOnItemSelectedListener { menuItem ->
            Log.v("BottomManu", menuItem.itemId.toString())
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    binding.toolbarModel = setToolbarData(true, "Home", false)
                    navigate(R.id.nav_host_fragment, R.id.navigation_home, null)
                    true
                }
                R.id.menu_category -> {
                    navigate(R.id.nav_host_fragment, R.id.navigation_category, null)
                    true
                }
                R.id.menu_account -> {
                    navigate(R.id.nav_host_fragment, R.id.navigation_account, null)
/*                    val bundle = Bundle()
                    bundle.putString("Amount", "100")
                    navigate(R.id.nav_host_fragment, R.id.navigation_order_status, bundle)*/
                    true
                }

                else -> false
            }
        }

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.ivKart.setOnClickListener {
            navigate(R.id.nav_host_fragment, R.id.navigation_cart, null)
        }

    }

    private fun observe() {

        binding.tvTitle.text = "Nagpur"
        binding.tvSubTitle.text = ""

/*
        viewModel.getLocations().observe(this, androidx.lifecycle.Observer {
            if(it != null && it.isNotEmpty()){
                binding.tvTitle.text = it[0].locationName
                binding.tvSubTitle.text = it[0].address
            }else{

            }
        })
*/


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        createLocationRequest()
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult == null) {
                    return
                }
                for (location in locationResult.locations) {
                    // Update UI with location data
                    // ...
                }
            }
        }

/*        viewModel.getUserInfo().observe(this , Observer {
            this.userInfo = it
        })*/
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest.create()
        locationRequest!!.interval = 10000
        locationRequest!!.fastestInterval = 5000
        locationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }
    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient!!.requestLocationUpdates(
            locationRequest!!,
            locationCallback!!,
            Looper.getMainLooper()
        )
    }


    private fun setLocation() {
        val gpsTracker = GpsTracker(this)
        if (common.checkPermissionValid(this@HomeActivity)) {
            if (gpsTracker.isGPSTrackingEnabled) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                   return
                }
                fusedLocationClient!!.lastLocation
                    .addOnSuccessListener(
                        this
                    ) { location ->
                        if (location != null) {
                            val addresses: List<Address>
                            val geocoder: Geocoder = Geocoder(this@HomeActivity, Locale.getDefault())
                            try {
                                addresses = geocoder.getFromLocation(
                                    location.latitude,
                                    location.longitude,
                                    1
                                )!!
                                val address = addresses[0].getAddressLine(0)
                                val locality = addresses[0].locality
                                val state = addresses[0].adminArea
                                val country = addresses[0].countryName
                                val postalCode = addresses[0].postalCode
                                val knownName = addresses[0].featureName

                                binding.tvTitle.text = locality
                                binding.tvSubTitle.text = address
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }
                    }
            } else {
                gpsTracker.showSettingsAlert()
            }
        }
    }


    override fun createViewModel(): HomeActivityViewModel {
        return ViewModelProvider(this, factory).get(HomeActivityViewModel::class.java)
    }

    override fun getLayout(): Int {
        return R.layout.activity_home
    }

    /**
     * This listener is used to get fragment destination(Current Fragment)
     */
    private val listener =
        NavController.OnDestinationChangedListener { controller, destination, arguments ->
            Log.v("FragmentChange", destination.displayName)

            if (destination.id == R.id.navigation_home || destination.id == R.id.navigation_account || destination.id == R.id.navigation_category)
                binding.navLayout.visibility = View.VISIBLE
             else  binding.navLayout.visibility = View.GONE

            if (destination.id == R.id.navigation_home) {
                binding.toolbarModel = setToolbarData(true, getDayWish(), false)
                binding.navLayout.bottomNavigation.menu.findItem(R.id.menu_home).isChecked = true
            } else if (destination.id == R.id.navigation_account) {
                binding.toolbarModel = setToolbarData(false, destination.label.toString(), true)
                binding.navLayout.bottomNavigation.menu.findItem(R.id.menu_account).isChecked = true
            } else if (destination.id == R.id.navigation_category) {
                binding.toolbarModel = setToolbarData(false, destination.label.toString(), true)
                binding.navLayout.bottomNavigation.menu.findItem(R.id.menu_category).isChecked = true
            } else{
                binding.toolbarModel = setToolbarData(false, destination.label.toString(), true)
            }
        }

    override fun onResume() {
        super.onResume()
        controller.addOnDestinationChangedListener(listener)
        common.checkPermissionValid(this@HomeActivity)
        createLocationRequest()
        startLocationUpdates()
        setLocation()
    }

    override fun onPause() {
        controller.removeOnDestinationChangedListener(listener)
        super.onPause()
    }

    override fun onBackPressed() {
        val currentFragment = controller.currentDestination!!.id

        if (currentFragment == R.id.navigation_home){
            val builder = MaterialAlertDialogBuilder(this)
            builder.setTitle(getString(R.string.title_log_out))
            builder.setMessage(getString(R.string.msg_logout))
            builder.setPositiveButton(
                getString(R.string.action_yes)
            ) { dialog: DialogInterface?, which: Int ->
                sharedPrefData.removeData()
                startActivity(Intent(this, LoginActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
            builder.setNegativeButton(
                getString(R.string.action_no)
            ) { dialog: DialogInterface?, which: Int -> dialog!!.dismiss() }
            builder.show()
        }else {
            super.onBackPressed()
        }
    }


    private fun getDayWish() : String {
        val calender = Calendar.getInstance()
        val timeOfDay = calender[Calendar.HOUR_OF_DAY]
        val wish =
            if (timeOfDay < 12) "Good Morning" else if (timeOfDay < 16) "Good Afternoon" else if (timeOfDay < 21) "Good Evening" else "Welcome"
        return wish
    }
}