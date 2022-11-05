package com.iitms.bfr.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.iitms.bfr.R
import com.iitms.bfr.data.model.*
import com.iitms.bfr.databinding.AddAddressFragmentBinding
import com.iitms.bfr.ui.adapter.MoreCouponAdapter
import com.iitms.bfr.ui.base.BaseFragment
import com.iitms.bfr.ui.viewModel.AddressViewModel
import java.util.regex.Pattern
import javax.inject.Inject


class AddAddressFragment @Inject constructor() :
    BaseFragment<AddressViewModel, AddAddressFragmentBinding>(), View.OnClickListener {


    var user: User? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSaveAddress.setOnClickListener(this)
        observe()
    }


    private fun observe() {
        viewModel.getUserInfo().observe(viewLifecycleOwner) {
            if (it != null) {
                user = Gson().fromJson(it.listToJson, User::class.java)
                if(user != null) {

                }
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner){
            isLoading(it)
        }
        
        viewModel.userDataInit.observe(viewLifecycleOwner) {
            showSnackBar("Address Added Successfully.")
            requireActivity().onBackPressed()
        }


    }


    override fun createViewModel(): AddressViewModel {
        return ViewModelProvider(this, viewModelFactory).get(AddressViewModel::class.java)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_add_address
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btn_save_address -> {
                if(isValidFields() && user != null){
                    viewModel.updateUserData(getUpdatedData()!!)
                }
            }
        }
    }

    private fun getUpdatedData(): User? {
        var places = ArrayList<Place>()
        places = user!!.place as ArrayList<Place>
        places.add(getPlace())
        user!!.place = places
        return user
    }


    private fun getPlace(): Place {
        val place = Place()
        place.city = binding.edtCity.text.toString()
        place.addressType = binding.edtAddressType.text.toString()
        place.country = "india"
        place.defaultDeliveryAddress = false
        place.locality = binding.edtArea.text.toString()
        place.pinCode = binding.edtPincode.text.toString()
        place.state = binding.edtState.text.toString()
        place.streetName = ""
        place.fullAddress =
            binding.edtFlatNo.text.toString() + " " + binding.edtArea.text.toString() + " " + binding.edtCity.text.toString()
        place.geoLocationRefOrValue = getGeoLocation()
        return place
    }

    private fun getGeoLocation(): GeoLocationRefOrValue? {
        val geometry = Geometry()
        geometry.geographicDatum = ""
        geometry.latitude = 21.1255545
        geometry.longitude = 72.25564646
        return GeoLocationRefOrValue(geometry)
    }

    private fun isValidFields(): Boolean {
        if (binding.edtAddressType.text.isEmpty()) {
            showSnackBar("Please enter address type.")
            return false
        } else if (binding.edtPincode.text.isEmpty()) {
            showSnackBar("Please enter pin code.")
            return false
        } else if (binding.edtFlatNo.text.isEmpty()) {
            showSnackBar("Please enter Flat, House no, Building, Company, Apartment.")
            return false
        } else if (binding.edtArea.text.isEmpty()) {
            showSnackBar("Please enter Area, Street, Sector, Village")
            return false
        } else if (binding.edtLandmark.text.isEmpty()) {
            showSnackBar("Please enter landmark.")
            return false
        } else if (binding.edtCity.text.isEmpty()) {
            showSnackBar("Please enter city.")
            return false
        } else if (binding.edtState.text.isEmpty()) {
            showSnackBar("Please enter state.")
            return false
        }
        return true
    }


}