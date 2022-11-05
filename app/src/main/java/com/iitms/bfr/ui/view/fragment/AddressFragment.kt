package com.iitms.bfr.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.iitms.bfr.R
import com.iitms.bfr.data.model.*
import com.iitms.bfr.databinding.AddressFragmentBinding
import com.iitms.bfr.ui.adapter.LocationAdapter
import com.iitms.bfr.ui.base.BaseFragment
import com.iitms.bfr.ui.listener.LocationListener
import com.iitms.bfr.ui.viewModel.AddressViewModel
import javax.inject.Inject


class AddressFragment @Inject constructor() :
    BaseFragment<AddressViewModel, AddressFragmentBinding>(), View.OnClickListener {

    @Inject
    lateinit var locationAdapter: LocationAdapter

    var user : User? = null



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddAddress.setOnClickListener(this)
        observe()
    }


    private fun observe() {

        viewModel.getUserInfo().observe(viewLifecycleOwner) {
            if (it != null) {
                user = Gson().fromJson(it.listToJson, User::class.java)
                if(user != null) {
                    showLocations(user!!.place!!)
                }
            }
        }



        viewModel.isLoading.observe(viewLifecycleOwner){
            isLoading(it)
        }


    }

    private fun showLocations(it: List<Place>) {
        binding.adapter = locationAdapter

        locationAdapter.addLocation(it as ArrayList<Place>, object : LocationListener {
            override fun onLocationClick(location: Place) {
            }

            override fun onDeleteLocation(location: Place) {
            }

            override fun onEditLocation(location: Place) {
            }
        })

    }


    override fun createViewModel(): AddressViewModel {
        return ViewModelProvider(this, viewModelFactory).get(AddressViewModel::class.java)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_address
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btn_add_address -> {
                navigate(R.id.navigation_add_address, null)
            }
        }
    }







}