package com.iitms.bfr.ui.view.activity

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.iitms.bfr.R
import com.iitms.bfr.databinding.ImageViewActivityBinding
import com.iitms.bfr.ui.base.BaseActivity
import com.iitms.bfr.ui.viewModel.ImageViewViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ImageViewActivity : BaseActivity<ImageViewViewModel, ImageViewActivityBinding>(),
    View.OnTouchListener {
    private var previousFingerPosition = 0
    private var baseLayoutPosition = 0
    private var defaultViewHeight = 0

    private var isClosing = false
    private var isScrollingUp = false
    private var isScrollingDown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.extras
        val imageUrl = bundle!!.getString("BUNDLE_IMAGE_URL")

        if (imageUrl != null && imageUrl.isNotEmpty()) {
            Picasso.with(applicationContext).load(imageUrl).skipMemoryCache()
                .into(binding.ivHomeworkImage, object : Callback {
                    override fun onSuccess() {
                        binding.pbHomework.visibility = View.GONE
                    }

                    override fun onError() {
                        binding.pbHomework.visibility = View.GONE
                    }
                })

        }else{
            Glide.with(this).load(imageUrl)
                .error(R.drawable.ic_no_image)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.ivHomeworkImage)
        }

        binding.imgClose.setOnClickListener {
            onBackPressed()
        }

    }

    override fun createViewModel(): ImageViewViewModel {
        return ViewModelProvider(this, factory).get(ImageViewViewModel::class.java)
    }

    override fun getLayout(): Int {
        return R.layout.activity_image_view
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        val Y = event.rawY.toInt()
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                // save default base layout height
                defaultViewHeight = binding.baseLayout.height

                // Init finger and view position
                previousFingerPosition = Y
                baseLayoutPosition = binding.baseLayout.y as Int
            }
            MotionEvent.ACTION_UP -> {
                // If user was doing a scroll up
                if (isScrollingUp) {
                    // Reset baselayout position
                    binding.baseLayout.y = 0.0f
                    // We are not in scrolling up mode anymore
                    isScrollingUp = false
                }

                // If user was doing a scroll down
                if (isScrollingDown) {
                    // Reset baselayout position
                    binding.baseLayout.y = 0.0f
                    // Reset base layout size
                    binding.baseLayout.layoutParams.height = defaultViewHeight
                    binding.baseLayout.requestLayout()
                    // We are not in scrolling down mode anymore
                    isScrollingDown = false
                }
            }
            MotionEvent.ACTION_MOVE -> if (!isClosing) {
                val currentYPosition = binding.baseLayout.y as Int

                // If we scroll up
                if (previousFingerPosition > Y) {
                    // First time android rise an event for "up" move
                    if (!isScrollingUp) {
                        isScrollingUp = true
                    }

                    // Has user scroll down before -> view is smaller than it's default size -> resize it instead of change it position
                    if (binding.baseLayout.getHeight() < defaultViewHeight) {
                        binding.baseLayout.getLayoutParams().height =
                            binding.baseLayout.getHeight() - (Y - previousFingerPosition)
                        binding.baseLayout.requestLayout()
                    } else {
                        // Has user scroll enough to "auto close" popup ?
                        if (baseLayoutPosition - currentYPosition > defaultViewHeight / 4) {
                            closeUpAndDismissDialog(currentYPosition)
                            return true
                        }

                        //
                    }
                    binding.baseLayout.setY(binding.baseLayout.getY() + (Y - previousFingerPosition))
                } else {

                    // First time android rise an event for "down" move
                    if (!isScrollingDown) {
                        isScrollingDown = true
                    }

                    // Has user scroll enough to "auto close" popup ?
                    if (Math.abs(baseLayoutPosition - currentYPosition) > defaultViewHeight / 2) {
                        closeDownAndDismissDialog(currentYPosition)
                        return true
                    }

                    // Change base layout size and position (must change position because view anchor is top left corner)
                    binding.baseLayout.setY(binding.baseLayout.getY() + (Y - previousFingerPosition))
                    binding.baseLayout.getLayoutParams().height =
                        binding.baseLayout.getHeight() - (Y - previousFingerPosition)
                    binding.baseLayout.requestLayout()
                }

                // Update position
                previousFingerPosition = Y
            }
        }
        return true
    }

    private fun closeUpAndDismissDialog(currentPosition: Int) {
    }

    private fun closeDownAndDismissDialog(currentPosition: Int) {
    }
}