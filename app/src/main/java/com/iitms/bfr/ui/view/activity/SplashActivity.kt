package com.iitms.bfr.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import com.iitms.bfr.R
import com.iitms.bfr.ui.base.BaseActivity

import com.iitms.bfr.databinding.SplashActivityBinding
import com.iitms.bfr.ui.viewModel.LoginActivityViewModel


class SplashActivity : BaseActivity<LoginActivityViewModel, SplashActivityBinding>(), Runnable {

    lateinit var handler: Handler

    private val timeOut: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handler = Handler()
        handler.postDelayed(this, timeOut)
    }


    override fun createViewModel(): LoginActivityViewModel {
        return ViewModelProvider(this, factory).get(LoginActivityViewModel::class.java)
    }

    override fun getLayout(): Int {
        return R.layout.activity_splash
    }

    override fun run() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

}