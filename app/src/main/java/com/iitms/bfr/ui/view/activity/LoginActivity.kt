package com.iitms.bfr.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.iitms.bfr.R
import com.iitms.bfr.ui.base.BaseActivity
import javax.inject.Inject

import com.iitms.bfr.data.model.LoginModel
import com.iitms.bfr.databinding.LoginActivityBinding
import com.iitms.bfr.ui.viewModel.LoginActivityViewModel


class LoginActivity : BaseActivity<LoginActivityViewModel, LoginActivityBinding>() , View.OnClickListener{

    @Inject
    lateinit var loginModel: LoginModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.loginModel = loginModel
        binding.viewModel = viewModel
        binding.btnLogin.setOnClickListener(this)

        observe()
    }

    private fun observe() {

        viewModel.isLoading.observe(this, Observer {
            isLoading(it)
        })


        viewModel.failed.observe(this, Observer {
            showSnackBar(it.message.toString())
        })

        viewModel.initial.observe(this, Observer {
            if(it != null) {
                startActivity(Intent(this@LoginActivity, OtpVerificationActivity::class.java).putExtra("OTP", it.otp).putExtra("Phone", it.mobileNumber))
            }
        })

    }

    override fun createViewModel(): LoginActivityViewModel {
        return ViewModelProvider(this, factory).get(LoginActivityViewModel::class.java)
    }

    override fun getLayout(): Int {
        return R.layout.activity_login
    }

    override fun onClick(p0: View?) {
       when(p0!!.id) {
           R.id.btn_login -> {
               if(validFields()){
                   viewModel.login(binding.edtNumber.text.toString())
               }
           }
       }
    }

    private fun validFields(): Boolean {
        if(binding.edtNumber.text.isEmpty()){
            showSnackBar(resources.getString(R.string.error_enter_number))
            return false
        } else if(binding.edtNumber.text.toString().length != 10){
            showSnackBar(resources.getString(R.string.error_number))
            return false
        }
        return true
    }



}