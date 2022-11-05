package com.iitms.bfr.di.module

import com.iitms.bfr.ui.view.activity.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributesInjectHomeActivity(): HomeActivity

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributesInjectLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributesInjectSplashActivity(): SplashActivity


    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributesInjectImageViewActivity(): ImageViewActivity

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributesInjectOtpVerificationActivity(): OtpVerificationActivity

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributesInjectPaymentActivity(): PaymentActivity

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributesInjectRegistrationActivity(): RegistrationActivity

}