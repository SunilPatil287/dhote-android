package com.iitms.bfr.ui.view.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.iitms.bfr.R
import com.iitms.bfr.data.model.User
import com.iitms.bfr.databinding.AccountFragmentBinding
import com.iitms.bfr.ui.base.BaseFragment
import com.iitms.bfr.ui.util.Constant
import com.iitms.bfr.ui.view.activity.LoginActivity
import com.iitms.bfr.ui.view.activity.RegistrationActivity
import com.iitms.bfr.ui.viewModel.AccountFragmentViewModel

class AccountFragment : BaseFragment<AccountFragmentViewModel,AccountFragmentBinding>(), View.OnClickListener {

    var user : User? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvAddress.setOnClickListener(this)
        binding.tvProfile.setOnClickListener(this)
        binding.tvPrivacyPolicy.setOnClickListener(this)
        binding.tvFaq.setOnClickListener(this)
        binding.tvContactUs.setOnClickListener(this)
        binding.tvOrders.setOnClickListener(this)
        binding.tvTerms.setOnClickListener(this)
        binding.tvLogout.setOnClickListener(this)
        observe()
    }

    @SuppressLint("SetTextI18n")
    private fun observe() {
        if(sharedPrefData.getString(Constant.USERID) == ""){
            requireActivity().startActivity(Intent(requireActivity(), LoginActivity::class.java))
            requireActivity().finish()
        }else{
            viewModel.getUserData(sharedPrefData.getString(Constant.USERID))
        }

        viewModel.isLoading.observe(viewLifecycleOwner){
            isLoading(it)
        }

        viewModel.user.observe(viewLifecycleOwner) {
            if(it != null) {
                this.user = it
                binding.tvName.text = it.givenName + " " + it.familyName
            }
        }

    }


    override fun createViewModel(): AccountFragmentViewModel {
       return ViewModelProvider(this,viewModelFactory).get(AccountFragmentViewModel::class.java)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_account
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_address -> {
                navigate(R.id.navigation_address, null)
            }
            R.id.tv_profile -> {
                navigate(R.id.navigation_profile, null)
            }
            R.id.tv_privacy_policy -> {
                navigate(R.id.navigation_privacy, null)
            }
            R.id.tv_faq -> {
                navigate(R.id.navigation_faq, null)
            }
            R.id.tv_contact_us -> {
                navigate(R.id.navigation_contact_us, null)
            }
            R.id.tv_orders -> {
                navigate(R.id.navigation_orders, null)
            }
            R.id.tv_terms -> {
                navigate(R.id.navigation_terms, null)
            }
            R.id.tv_logout -> {
                requireActivity().finish()
                startActivity(Intent(requireActivity(), LoginActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            }
        }

    }

}