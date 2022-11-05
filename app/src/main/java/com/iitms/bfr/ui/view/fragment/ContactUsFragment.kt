package com.iitms.bfr.ui.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.iitms.bfr.R
import com.iitms.bfr.databinding.ContactUsFragmentBinding
import com.iitms.bfr.ui.base.BaseFragment
import com.iitms.bfr.ui.view.activity.LoginActivity
import com.iitms.bfr.ui.viewModel.ContactUsFragmentViewModel

class ContactUsFragment : BaseFragment<ContactUsFragmentViewModel,ContactUsFragmentBinding>(), View.OnClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      
    }


    override fun createViewModel(): ContactUsFragmentViewModel {
       return ViewModelProvider(this,viewModelFactory).get(ContactUsFragmentViewModel::class.java)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_contact_us
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
          
        }

    }

}