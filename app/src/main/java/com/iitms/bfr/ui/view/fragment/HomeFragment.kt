package com.iitms.bfr.ui.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.daimajia.slider.library.Animations.DescriptionAnimation
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.TextSliderView
import com.google.gson.Gson
import com.iitms.bfr.R
import com.iitms.bfr.data.model.CategoryList
import com.iitms.bfr.data.model.User
import com.iitms.bfr.data.model.UserInfo
import com.iitms.bfr.databinding.HomeFragmentBinding
import com.iitms.bfr.ui.adapter.HomeCategoryAdapter
import com.iitms.bfr.ui.adapter.MenuAdapter
import com.iitms.bfr.ui.base.BaseFragment
import com.iitms.bfr.ui.listener.CategoryListener
import com.iitms.bfr.ui.util.Constant
import com.iitms.bfr.ui.view.activity.HomeActivity
import com.iitms.bfr.ui.view.activity.LoginActivity
import com.iitms.bfr.ui.viewModel.HomeFragmentViewModel
import javax.inject.Inject


class HomeFragment @Inject constructor() :
    BaseFragment<HomeFragmentViewModel, HomeFragmentBinding>(), View.OnClickListener {

    @Inject
    lateinit var homeCategoryAdapter: HomeCategoryAdapter

    @Inject
    lateinit var bonelessAdapter: MenuAdapter

    @Inject
    lateinit var bestSellerAdapter: MenuAdapter

    var user : User? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
    }


    private fun observe() {
        showBanner(null)
        viewModel.getCategory()
        if(sharedPrefData.getString(Constant.USERID) != ""){
            viewModel.getUserData(sharedPrefData.getString(Constant.USERID))
        }else{
            /*requireActivity().startActivity(Intent(requireActivity(), LoginActivity::class.java))
           requireActivity().finish()*/
        }

        viewModel.userData.observe(viewLifecycleOwner, Observer {
            if(it.user != null){
                sharedPrefData.saveData(Constant.USERID, it.user!!.id.toString())
            }
        })


        viewModel.getUserInfo().observe(viewLifecycleOwner) {
            if (it != null) {
                user = Gson().fromJson(it.listToJson, User::class.java)
                if(user != null) {
                }
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner){
            isLoading(it)
        }

        viewModel.categoryList.observe(viewLifecycleOwner, Observer {
            if(it?.categoryList != null && it.categoryList.isNotEmpty()){
                binding.categoryAdapter = homeCategoryAdapter
                homeCategoryAdapter.addCategory(it.categoryList, object :CategoryListener {
                    override fun onCategoryClick(categoryList: CategoryList) {
                        navigate(R.id.navigation_sub_category, null)
                    }
                })


                binding.bonelessAdapter = bonelessAdapter
                bonelessAdapter.addMenu(it.categoryList, object :CategoryListener {
                    override fun onCategoryClick(categoryList: CategoryList) {
                    }
                })


                binding.bestSellerAdapter = bestSellerAdapter
                bestSellerAdapter.addMenu(it.categoryList, object :CategoryListener {
                    override fun onCategoryClick(categoryList: CategoryList) {

                    }
                })
            }
        })
    }

    private fun showBanner(banner: List<CategoryList>?) {
        if (banner != null && banner.isNotEmpty()) {
          /*  binding.slider.removeAllSliders()
            for (bannerDetail in banner) {
                val textSliderView = TextSliderView(requireContext())
                textSliderView
                    .image(bannerDetail.path)
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .description(bannerDetail.title)
                textSliderView.bundle(Bundle())
                textSliderView.setOnSliderClickListener {
                    if (bannerDetail.link != null && !bannerDetail.link.toString().isEmpty()) {
                        val urlString: String = bannerDetail.link.toString()
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlString))
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }

                }
                binding.slider.addSlider<TextSliderView>(textSliderView)
            }*/
        } else {
            binding.slider.removeAllSliders()
            val textSliderView = TextSliderView(requireContext())
            textSliderView.image(R.drawable.ic_banner).scaleType = BaseSliderView.ScaleType.Fit
            textSliderView.bundle(Bundle())
            binding.slider.addSlider<TextSliderView>(textSliderView)

        }
        binding.slider.setPresetTransformer(SliderLayout.Transformer.Accordion)
        binding.slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom)
        binding.slider.setCustomAnimation(DescriptionAnimation())
        binding.slider.setDuration(4000)
        binding.slider.startAutoCycle()
    }


    override fun createViewModel(): HomeFragmentViewModel {
        return ViewModelProvider(this, viewModelFactory).get(HomeFragmentViewModel::class.java)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_home
    }

    override fun onResume() {
        common.checkPermissionValid(requireContext())
        super.onResume()
    }
    override fun onClick(p0: View?) {
        when (p0!!.id) {

        }
    }




}