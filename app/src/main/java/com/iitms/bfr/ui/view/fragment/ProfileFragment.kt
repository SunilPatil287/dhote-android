package com.iitms.bfr.ui.view.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.iitms.bfr.R
import com.iitms.bfr.data.model.User
import com.iitms.bfr.data.model.UserData
import com.iitms.bfr.data.model.UserInfo
import com.iitms.bfr.databinding.ProfileFragmentBinding
import com.iitms.bfr.ui.base.BaseFragment
import com.iitms.bfr.ui.util.Constant
import com.iitms.bfr.ui.view.activity.LoginActivity
import com.iitms.bfr.ui.viewModel.ProfileFragmentViewModel
import java.util.regex.Pattern

class ProfileFragment : BaseFragment<ProfileFragmentViewModel,ProfileFragmentBinding>() {

    var userData : UserData? = null
    var user : User? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()

    }


    @SuppressLint("SetTextI18n")
    private fun observe() {
        viewModel.getUserInfo().observe(viewLifecycleOwner) {
            if (it != null) {
                user = Gson().fromJson(it.listToJson, User::class.java)
                if(user != null) {
                    binding.edtFirstName.setText(user!!.givenName.toString())
                    binding.edtLastName.setText(user!!.familyName.toString())
                    if(user!!.contactMedium != null) {
                        binding.edtMobileNumber.text = user!!.contactMedium!!.phoneNumber.toString()
                        binding.edtEmail.setText(user!!.contactMedium!!.email.toString())
                    }
                    if(user!!.gender.equals("male")){
                        binding.rbMale.isChecked = true
                    }else{
                        binding.rbFemale.isChecked = true
                    }
                }
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner){
            isLoading(it)
        }

        viewModel.userDataInit.observe(viewLifecycleOwner) {
            showSnackBar("Profile Update Successfully.")
        }

        binding.btnUpdate.setOnClickListener {
            if(isValidFields() && user != null) {
                viewModel.updateUserData(getUpdatedUserDate()!!)
            }
        }
    }

    private fun getUpdatedUserDate(): User? {
        user!!.contactMedium!!.email = binding.edtEmail.text.toString()
        user!!.givenName = binding.edtFirstName.text.toString()
        user!!.familyName = binding.edtLastName.text.toString()
        user!!.gender = if (binding.rbMale.isChecked) "male" else "female"
        return user
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
        }
        return true
    }


    override fun createViewModel(): ProfileFragmentViewModel {
       return ViewModelProvider(this,viewModelFactory).get(ProfileFragmentViewModel::class.java)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_profile
    }

}