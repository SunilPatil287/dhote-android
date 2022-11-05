package com.iitms.bfr.ui.base


import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.iitms.bfr.Common
import com.iitms.bfr.R
import com.iitms.bfr.data.model.ToolbarModel
import com.iitms.bfr.di.factory.ViewModelFactory
import com.iitms.bfr.ui.view.fragment.ProgressDialogFragment
import com.iitms.sdcloud.ui.util.SharedPrefData
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


abstract class BaseActivity <VM : BaseViewModel, DB : ViewDataBinding> : DaggerAppCompatActivity() {

    protected lateinit var binding: DB

    protected lateinit var viewModel: VM

    protected abstract fun createViewModel(): VM

    @Inject
    lateinit var common: Common

   /* @Inject
    lateinit var sessionDialog: SessionDialog*/

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var progressDialogFragment: ProgressDialogFragment

    @Inject
    lateinit var sharedPrefData: SharedPrefData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        viewModel = createViewModel()

       /* viewModel.sessionExpire.observe(this, Observer {
            sessionDialog.show(supportFragmentManager, "")
        })*/


    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun performDataBinding() {
        binding = DataBindingUtil.setContentView(this, getLayout())
        binding.executePendingBindings()
    }

    abstract fun getLayout(): Int

    fun navigate(navId:Int,resId: Int, bundle: Bundle?) {
        findNavController(navId).navigate(
            resId,
            bundle,
            defaultAnimation()
        )
    }

    private fun defaultAnimation(): NavOptions {
        return NavOptions.Builder()
            /* .setEnterAnim(R.anim.animate_card_enter)
             .setExitAnim(R.anim.animate_card_exit)
             .setPopEnterAnim(R.anim.animate_card_enter)
             .setPopExitAnim(R.anim.animate_card_exit)*/
            .build()
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
                progressDialogFragment.show(supportFragmentManager, "")
        } catch (e: Exception) {

        }

    }


    fun setToolbarData(isHomePage: Boolean, title: String, statusBarColorRed: Boolean): ToolbarModel {
        if(statusBarColorRed) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this@BaseActivity, R.color.colorPrimary)
        }else {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this@BaseActivity, R.color.transparent)
        }
        return ToolbarModel(
                title,resources.getString(R.string.welocom_msg),isHomePage
            )
    }
    fun showSnackBar(message: String) {
        val snackBar = Snackbar.make(
            findViewById(android.R.id.content),
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