package com.iitms.bfr.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.daimajia.slider.library.Animations.DescriptionAnimation
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.TextSliderView
import com.iitms.bfr.R
import com.iitms.bfr.data.model.CategoryList
import com.iitms.bfr.data.model.UserInfo
import com.iitms.bfr.databinding.SubCategoryDetailFragmentBinding
import com.iitms.bfr.ui.adapter.CategoryAdapter
import com.iitms.bfr.ui.adapter.CategoryStaggeredAdapter
import com.iitms.bfr.ui.adapter.CouponAdapter
import com.iitms.bfr.ui.adapter.MenuAdapter
import com.iitms.bfr.ui.base.BaseFragment
import com.iitms.bfr.ui.listener.CategoryListener
import com.iitms.bfr.ui.viewModel.SubCategoryViewModel
import javax.inject.Inject

class SubCategoryDetailFragment : BaseFragment<SubCategoryViewModel,SubCategoryDetailFragmentBinding>(), View.OnClickListener {


    @Inject
    lateinit var couponAdapter: CouponAdapter

    @Inject
    lateinit var menuAdapter: MenuAdapter


    private var userInfo: UserInfo? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.btnSmallSize.setOnClickListener(this)
        binding.btnLargeSize.setOnClickListener(this)
        binding.btnAddToCart.setOnClickListener(this)
        observe()
    }



    private fun observe() {
        showBanner(null)
        viewModel.getCategory()
        viewModel.categoryList.observe(viewLifecycleOwner, Observer {
            if(it?.categoryList != null && it.categoryList.isNotEmpty()){
                binding.couponAdapter = couponAdapter
                couponAdapter.addCategory(it.categoryList, object : CategoryListener {
                    override fun onCategoryClick(categoryList: CategoryList) {

                    }
                })

                binding.menuAdapter = menuAdapter
                menuAdapter.addMenu(it.categoryList, object : CategoryListener {
                    override fun onCategoryClick(categoryList: CategoryList) {

                    }
                })
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner){
            isLoading(it)
        }
    }


    override fun createViewModel(): SubCategoryViewModel {
       return ViewModelProvider(this,viewModelFactory).get(SubCategoryViewModel::class.java)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_sub_category_detail
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

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btn_small_size -> {
                viewModel.smallSizeMenu.set(true)
            }
            R.id.btn_large_size -> {
                viewModel.smallSizeMenu.set(false)
            }
            R.id.btn_add_to_cart -> {
                navigate(R.id.navigation_cart, null)
            }
        }
    }

}