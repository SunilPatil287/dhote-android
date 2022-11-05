package com.iitms.bfr.ui.viewModel
import androidx.lifecycle.MutableLiveData
import com.iitms.bfr.ui.base.BaseViewModel
import javax.inject.Inject

class SharedViewModel @Inject constructor(): BaseViewModel(){
   var sharedData = MutableLiveData<Int>()
}