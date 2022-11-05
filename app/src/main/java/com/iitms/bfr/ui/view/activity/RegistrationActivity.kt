package com.iitms.bfr.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.iitms.bfr.R
import com.iitms.bfr.data.model.*
import com.iitms.bfr.databinding.RegistrationBinding
import com.iitms.bfr.ui.base.BaseActivity
import com.iitms.bfr.ui.util.Constant
import com.iitms.bfr.ui.viewModel.RegistrationViewModel
import java.util.regex.Pattern


class RegistrationActivity : BaseActivity<RegistrationViewModel, RegistrationBinding>(),
    View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnRegister.setOnClickListener(this)
        observe()
    }

    private fun observe() {

        viewModel.isLoading.observe(this, Observer {
            isLoading(it)
        })



        viewModel.failed.observe(this, Observer {
            showSnackBar(it.message.toString())
        })

        viewModel.initialUser.observe(this, Observer {
            if(it != null){
                showSnackBar("Registration Successfully Done.")
                sharedPrefData.saveData(Constant.USERID, it.id.toString())
                startActivity(Intent(this@RegistrationActivity, HomeActivity::class.java).putExtra("IntentNavigation","Registration").setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        })


    }

    override fun createViewModel(): RegistrationViewModel {
        return ViewModelProvider(this, factory).get(RegistrationViewModel::class.java)
    }

    override fun getLayout(): Int {
        return R.layout.activity_registration
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btn_register -> {
                if (isValidFields()) {
                    sendUserData()
                }
            }
        }
    }

    private fun sendUserData() {
        viewModel.saveUserDate(getUserData())
    }

    private fun getUserData(): User {
        val user = User()
        user.contactMedium = ContactMedium(binding.edtEmail.text.toString(), binding.edtMobileNumber.text.toString())
        user.givenName = binding.edtFirstName.text.toString()
        user.familyName = binding.edtLastName.text.toString()
        user.gender = if (binding.rbMale.isChecked) "male" else "female"
        user.maritalStatus = ""
        user.middleName = ""
        user.passCode = ""
        user.platform = "android"
        user.system = "customer"
        user.place = getPlaces()
        return user
    }

    private fun getPlaces(): List<Place>? {
        val placeList = ArrayList<Place>()
        val place = Place()
        place.city = binding.edtCity.text.toString()
        place.addressType = "home"
        place.country = "india"
        place.defaultDeliveryAddress = true
        place.locality = binding.edtArea.text.toString()
        place.pinCode = binding.edtPincode.text.toString()
        place.state = binding.edtState.text.toString()
        place.streetName = ""
        place.fullAddress =
            binding.edtFlatNo.text.toString() + " " + binding.edtArea.text.toString()
        place.geoLocationRefOrValue = getGeoLocation()
        placeList.add(place)
        return placeList
    }

    private fun getGeoLocation(): GeoLocationRefOrValue? {
        val geometry = Geometry()
        geometry.geographicDatum = ""
        geometry.latitude = 21.1255545
        geometry.longitude = 72.25564646
        return GeoLocationRefOrValue(geometry)
    }

    private fun isValidFields(): Boolean {
        val EMAIL_PATTERN =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        val pattern = Pattern.compile(EMAIL_PATTERN)
        if(binding.edtFirstName.text.isEmpty()){
            showSnackBar("Please enter first name.")
            return false
        } else if(binding.edtLastName.text.isEmpty()){
            showSnackBar("Please enter last name.")
            return false
        } else if(binding.edtEmail.text.isEmpty()){
            showSnackBar("Please enter email.")
            return false
        } else if(!binding.edtEmail.text.toString().matches(EMAIL_PATTERN.toRegex())){
            showSnackBar("Please enter valid email.")
            return false
        } else if(binding.edtMobileNumber.text.isEmpty()){
            showSnackBar("Please enter phone number.")
            return false
        } else if(binding.edtMobileNumber.text.toString().length != 10){
            showSnackBar("Please enter valid phone number.")
            return false
        } else if(binding.edtPincode.text.isEmpty()){
            showSnackBar("Please enter pincode.")
            return false
        } else if(binding.edtFlatNo.text.isEmpty()){
            showSnackBar("Please enter Flat, House no, Building, Company, Apartment.")
            return false
        } else if(binding.edtArea.text.isEmpty()){
            showSnackBar("Please enter Area, Street, Sector, Village")
            return false
        }else if(binding.edtLandmark.text.isEmpty()){
            showSnackBar("Please enter landmark.")
            return false
        }else if(binding.edtCity.text.isEmpty()){
            showSnackBar("Please enter city.")
            return false
        }else if(binding.edtState.text.isEmpty()){
            showSnackBar("Please enter state.")
            return false
        }
        return true
    }


}