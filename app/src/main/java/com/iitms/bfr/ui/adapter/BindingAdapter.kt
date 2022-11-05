package com.iitms.bfr.ui.adapter


import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Base64
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TimePicker
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.iitms.bfr.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object BindingAdapter {


    @JvmStatic
    @BindingAdapter("colorTint")
    fun setImageTint(imageView: ImageView, color: String?) {
        if (color.isNullOrEmpty()) return
        imageView.setColorFilter(Color.parseColor(color), android.graphics.PorterDuff.Mode.MULTIPLY)
    }

    @BindingAdapter("setImageResource")
    @JvmStatic
    fun loadImage(imageView: ImageView, icon: Int?) {
        if (icon != null) {
            imageView.setImageResource(icon)
        }
    }

    @BindingAdapter("setImageGlide")
    @JvmStatic
    fun loadImageGlide(imageView: ImageView, path: String?) {
        if (path != null && path.isNotEmpty()) {
            Glide.with(imageView.context!!).load(path)
                .error(R.drawable.ic_no_image)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView)
        }else{
            imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.ic_no_image))
        }
    }

    @BindingAdapter("setProfilePicture")
    @JvmStatic
    fun loadProfileImage(imageView: ImageView, profilePic: String?) {
        if (profilePic != null && profilePic.isNotEmpty()) {
            val decodedByte: ByteArray = Base64.decode(profilePic, 0)
            val newBm = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
            imageView.setImageBitmap(newBm)
        } else {
/*
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    imageView.context,
                    R.drawable.ic_profile
                )
            )
*/
        }
    }


    @SuppressLint("NewApi")
    @BindingAdapter("setMenuBackground")
    @JvmStatic
    fun setMenuBackground(linearLayout: LinearLayout, isSelect: Boolean?) {
        if (isSelect != null && isSelect) {
/*
            linearLayout.background =
                ContextCompat.getDrawable(linearLayout.context, R.drawable.rounded_menu_background)
*/
        } else {
            linearLayout.setBackgroundColor(linearLayout.context.getColor(R.color.colorAccent))
        }
    }

/*
    @BindingAdapter(value = ["selectDate", "minDate", "maxDate", "listener"], requireAll = false)
    @JvmStatic
    fun bindDateClicks(
        textView: TextView,
        date: ObservableField<String>?,
        minDate: ObservableField<String>?,
        maxDate: ObservableField<String>?,
        listener: DateChangeListener?
    ) {
        textView.setOnClickListener {
            if (date?.get() == null)
                date?.set("")
            else
                selectDate(textView.context, date!!, minDate, maxDate, listener)
        }
    }
*/


    @BindingAdapter("selectTime24")
    @JvmStatic
    fun bindTime24Clicks(
        textView: LinearLayout,
        time: ObservableField<String>?
    ) {
        textView.setOnClickListener {
            if (time == null && time?.get() == null)
                time?.set("")
            selectTime(textView.context, time!!, true)
        }
    }

    @BindingAdapter("selectTime12")
    @JvmStatic
    fun bindTime12Clicks(
        textView: LinearLayout,
        time: ObservableField<String>?
    ) {
        textView.setOnClickListener {
            if (time == null && time?.get() == null)
                time?.set("")
            selectTime(textView.context, time!!, false)
        }
    }


    @SuppressLint("SimpleDateFormat")
    private fun selectTime(context: Context, time: ObservableField<String>, format: Boolean) {
        val mHour: Int
        val mMinute: Int
        val c = Calendar.getInstance()
        mHour = c[Calendar.HOUR_OF_DAY]
        mMinute = c[Calendar.MINUTE]

        val timePickerDialog = TimePickerDialog(
            context,
            { view: TimePicker?, hourOfDay: Int, minute: Int ->
                if (format) {
                    val _24HourTime = "$hourOfDay:$minute"
                    time.set(_24HourTime)
                } else {
                    val _24HourTime = "$hourOfDay:$minute"
                    val _24HourSDF =
                        SimpleDateFormat("HH:mm")
                    val _12HourSDF =
                        SimpleDateFormat("hh:mm a")
                    var _24HourDt: Date? = null
                    try {
                        _24HourDt = _24HourSDF.parse(_24HourTime)
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }
                    time.set(_12HourSDF.format(_24HourDt))
                }
            }, mHour, mMinute, format
        )
        timePickerDialog.show()
    }


/*
    @SuppressLint("SimpleDateFormat")
    private fun selectDate(
        context: Context,
        date: ObservableField<String>,
        minDateString: ObservableField<String>?,
        maxDateString: ObservableField<String>?,
        listener: DateChangeListener?
    ) {
        val calender = Calendar.getInstance()
        var selectedDate: Date? = null
        if (!date.get().equals("")) {
            selectedDate = SimpleDateFormat("dd/MM/yyyy").parse(date.get()!!)
            calender.time = selectedDate!!
        }
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { view, mYear, monthOfYear, dayOfMonth ->

                val mDay = if (dayOfMonth < 10) "0${dayOfMonth}" else dayOfMonth
                val mMonth = if (monthOfYear + 1 < 10) "0${monthOfYear + 1}" else monthOfYear + 1

                date.set(
                    "$mDay/$mMonth/$mYear"
                )
                listener?.onDateChange(dayOfMonth, monthOfYear, year)
            }, year, month, day
        )
        if (minDateString != null && minDateString.get()!!.isNotEmpty()) {
            val minDate = SimpleDateFormat("dd/MM/yyyy").parse(minDateString.get()!!)
            datePickerDialog.datePicker.minDate = minDate.time
        }
        if (maxDateString != null && maxDateString.get()!!.isNotEmpty()) {
            val maxDate = SimpleDateFormat("dd/MM/yyyy").parse(maxDateString.get()!!)
            datePickerDialog.datePicker.maxDate = maxDate.time
        }
        datePickerDialog.show()
    }
*/

}