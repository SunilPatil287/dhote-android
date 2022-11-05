package com.iitms.bfr.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.iitms.bfr.Common
import com.iitms.bfr.di.factory.ViewModelFactory
import com.iitms.bfr.ui.view.fragment.ProgressDialogFragment
import com.iitms.sdcloud.ui.util.SharedPrefData
import dagger.android.support.DaggerFragment
import javax.inject.Inject


abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : DaggerFragment() {

    protected lateinit var binding: DB

    protected lateinit var viewModel: VM

    protected abstract fun createViewModel(): VM

    abstract fun getLayout(): Int

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var common: Common

    @Inject
    lateinit var progressDialogFragment: ProgressDialogFragment

    @Inject
    lateinit var sharedPrefData: SharedPrefData



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        viewModel = createViewModel()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    private fun defaultAnimation(): NavOptions {
        return NavOptions.Builder()
           /* .setEnterAnim(R.anim.animate_card_enter)
            .setExitAnim(R.anim.animate_card_exit)
            .setPopEnterAnim(R.anim.animate_card_enter)
            .setPopExitAnim(R.anim.animate_card_exit)*/
            .build()
    }

    fun navigate(resId: Int, bundle: Bundle?) {
        findNavController().navigate(
            resId,
            bundle,
            defaultAnimation()
        )
    }


    fun isLoading(isLoading: Boolean) {
        if (isLoading)
            showDialog()
        else dismissDialog()
    }


    private fun showDialog() {
        try {
            progressDialogFragment.dismiss()
            if (progressDialogFragment.isAdded && progressDialogFragment.isVisible)
                progressDialogFragment.dismiss()
            else
                progressDialogFragment.show(childFragmentManager, "")
        } catch (e: Exception) {

        }

    }


    fun navigateFinishFragment(resId: Int, bundle: Bundle?, desId :Int) {
        findNavController().navigate(
            resId,
            bundle,
            NavOptions.Builder().setPopUpTo(desId, false).build()
        )
    }

    fun showSnackBar(message: String) {
        val snackBar = Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_LONG
        )
        snackBar.show()
    }

    private fun dismissDialog() {
        try {
            progressDialogFragment.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}