package com.iitms.bfr.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.iitms.bfr.R
import com.iitms.bfr.ui.base.BaseActivity
import javax.inject.Inject

import com.iitms.bfr.data.model.LoginModel
import com.iitms.bfr.databinding.OtpVerificationActivityBinding
import com.iitms.bfr.ui.viewModel.OtpVerificationViewModel


class OtpVerificationActivity : BaseActivity<OtpVerificationViewModel, OtpVerificationActivityBinding>(), View.OnClickListener {

    @Inject
    lateinit var loginModel: LoginModel

    var otp : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.edtOtpOne.addTextChangedListener(mTextWatcher)
        binding.edtOtpTwo.addTextChangedListener(mTextWatcher)
        binding.edtOtpThree.addTextChangedListener(mTextWatcher)
        binding.edtOtpFour.addTextChangedListener(mTextWatcher)
        binding.edtOtpFive.addTextChangedListener(mTextWatcher)
        binding.btnVerify.setOnClickListener(this)
        binding.phoneNo = intent.getStringExtra("Phone")
        otp = intent.getStringExtra("OTP")
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
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
        })

    }

    override fun createViewModel(): OtpVerificationViewModel {
        return ViewModelProvider(this, factory).get(OtpVerificationViewModel::class.java)
    }

    override fun getLayout(): Int {
        return R.layout.activity_otp_verification
    }

    val mTextWatcher = object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            when {
                editable === binding.edtOtpOne.editableText -> {
                    if(binding.edtOtpOne.text.isNotEmpty()) {
                        binding.edtOtpTwo.isFocusableInTouchMode = true;
                        binding.edtOtpTwo.isFocusable = true;
                        binding.edtOtpTwo.requestFocus();
                    }
                }
                editable === binding.edtOtpTwo.editableText -> {
                    if(binding.edtOtpTwo.text.isNotEmpty()) {
                        binding.edtOtpThree.isFocusableInTouchMode = true;
                        binding.edtOtpThree.isFocusable = true;
                        binding.edtOtpThree.requestFocus();
                    }
                }
                editable === binding.edtOtpThree.editableText -> {
                    if(binding.edtOtpThree.text.isNotEmpty()) {
                        binding.edtOtpFour.isFocusableInTouchMode = true;
                        binding.edtOtpFour.isFocusable = true;
                        binding.edtOtpFour.requestFocus();
                    }
                }
                editable === binding.edtOtpFour.editableText -> {
                    if(binding.edtOtpFour.text.isNotEmpty()) {
                        binding.edtOtpFive.isFocusableInTouchMode = true;
                        binding.edtOtpFive.isFocusable = true;
                        binding.edtOtpFive.requestFocus();
                    }
                }
                editable === binding.edtOtpFive.editableText -> {
                    if(binding.edtOtpFive.text.isNotEmpty()) {
                        binding.edtOtpSix.isFocusableInTouchMode = true;
                        binding.edtOtpSix.isFocusable = true;
                        binding.edtOtpSix.requestFocus();
                    }
                }

            }
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btn_verify -> {
                if(validOtp()) {
                    finish()
                    startActivity(Intent(this@OtpVerificationActivity, RegistrationActivity::class.java))
//                    startActivity(Intent(this@OtpVerificationActivity, HomeActivity::class.java))
                }
            }
        }
    }

    private fun validOtp(): Boolean {
        otp = "987654"
        val etOne = binding.edtOtpOne.text.toString()
        val etTwo = binding.edtOtpTwo.text.toString()
        val etThree = binding.edtOtpThree.text.toString()
        val etFour = binding.edtOtpFour.text.toString()
        val etFive = binding.edtOtpFive.text.toString()
        val etSix = binding.edtOtpSix.text.toString()

        val userOtp = etOne + etTwo + etThree + etFour + etFive + etSix

        if(userOtp.trim().length != 6){
            showSnackBar(resources.getString(R.string.error_enter_correct_otp))
            return false
        } else if(userOtp.trim() != otp){
            showSnackBar(resources.getString(R.string.error_enter_correct_otp))
            return false
        }
        return true
    }


}