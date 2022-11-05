package com.iitms.bfr.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iitms.bfr.di.annotation.ViewModelKey
import com.iitms.bfr.di.factory.ViewModelFactory
import com.iitms.bfr.ui.base.BaseViewModel
import com.iitms.bfr.ui.viewModel.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(BaseViewModel::class)
    abstract fun bindBaseViewModel(baseViewModel: BaseViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeActivityViewModel::class)
    abstract fun bindHomeActivityViewModel(homeActivityViewModel: HomeActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginActivityViewModel::class)
    abstract fun bindLoginActivityViewModel(loginActivityViewModel: LoginActivityViewModel): ViewModel
    @Binds
    @IntoMap
    @ViewModelKey(HomeFragmentViewModel::class)
    abstract fun bindHomeFragmentViewModel(homeFragmentViewModel: HomeFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileFragmentViewModel::class)
    abstract fun bindProfileFragmentViewModel(profileFragmentViewModel: ProfileFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OtpVerificationViewModel::class)
    abstract fun bindOtpVerificationViewModel(otpVerificationViewModel: OtpVerificationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CartFragmentViewModel::class)
    abstract fun bindCartFragmentViewModel(cartFragmentViewModel: CartFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SubCategoryViewModel::class)
    abstract fun bindSubCategoryViewModel(subCategoryViewModel: SubCategoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrderStatusFragmentViewModel::class)
    abstract fun bindOrderStatusFragmentViewModel(orderStatusFragmentViewModel: OrderStatusFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PaymentActivityViewModel::class)
    abstract fun bindPaymentActivityViewModel(paymentActivityViewModel: PaymentActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AccountFragmentViewModel::class)
    abstract fun bindAccountFragmentViewModel(accountFragmentViewModel: AccountFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ContactUsFragmentViewModel::class)
    abstract fun bindContactUsFragmentViewModel(contactUsFragmentViewModel: ContactUsFragmentViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(AddressViewModel::class)
    abstract fun bindAddressViewModel(addressViewModel: AddressViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    abstract fun bindRegistrationViewModel(registrationViewModel: RegistrationViewModel): ViewModel


}