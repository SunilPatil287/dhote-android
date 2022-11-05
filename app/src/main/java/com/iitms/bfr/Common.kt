package com.iitms.bfr

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.os.Build
import android.os.Environment
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.exifinterface.media.ExifInterface
import androidx.lifecycle.Lifecycle
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.iitms.bfr.databinding.DialogCommonBinding
import com.iitms.bfr.ui.adapter.CommonSpinnerAdapter
import com.iitms.bfr.ui.listener.SpinnerDialogListener
import com.iitms.sdcloud.ui.util.ImageSource
import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback
import org.imaginativeworld.oopsnointernet.dialogs.signal.NoInternetDialogSignal
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


open class Common @Inject constructor() {


    private var PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )



    @Inject
    lateinit var commonSpinnerAdapter: CommonSpinnerAdapter

    public external fun getAppFeePayer(): String

    fun isInternetAvailable(): Boolean {
        val connectivityManager =
            MyApplication.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.activeNetworkInfo.also {
            return it != null && it.isConnected
        }
    }

    fun getDateStringForInput(dateInLong: Long): String {
        val date = Date(dateInLong)
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        return sdf.format(date)
    }

    fun getDateStringForDisplay(dateInLong: Long): String {
        return try {
            val date = Date(dateInLong)
            val myFormat = "dd-MM-yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            sdf.format(date)
        }catch (e : Exception){
            ""
        }

    }


    fun getHtmlString(html: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            Html.fromHtml(html).toString()
        }
    }

    fun openSpinnerDialog(
        mContext: Context,
        spinnerData: ArrayList<String>,
        dialogTitle: String,
        listener: SpinnerDialogListener
    ) {
        val dialog = BottomSheetDialog(mContext, R.style.AppBottomSheetDialogTheme)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding: DialogCommonBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.dialog_common,
            null,
            false
        )
        dialogBinding.adapter = commonSpinnerAdapter
        dialogBinding.tvLbl.text = dialogTitle
        commonSpinnerAdapter.addData(spinnerData,
            object : CommonSpinnerAdapter.SpinnerItemClickListener {
                override fun onItemClick(value: String) {
                    dialog.dismiss()
                    listener.onDialogClick(value)
                }
            })

        dialogBinding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                val filterData = filter(s.toString(), spinnerData)
                commonSpinnerAdapter.update(filterData)
            }
        })

        dialog.setContentView(dialogBinding.root)
        dialog.show()
    }

    fun filter(searchText: String?, spinnerData: ArrayList<String>): ArrayList<String> {
        var filterNames = ArrayList<String>()
        if (searchText != null && searchText.isNotEmpty())
            for (s in spinnerData) {
                if (s.lowercase().contains(searchText.lowercase())) {
                    filterNames.add(s)
                }
            }
        else {
            filterNames = spinnerData
        }
        return filterNames
    }

    fun getDateDDMMYYTOYYMMDD(dateInLong: String): String {
        return try {
            val format: DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
            val formatYYMMDD: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            formatYYMMDD.format(format.parse(dateInLong)!!)
        }catch (e : Exception){
            ""
        }

    }

    fun getDateDDMMYYYYToYYYYDDMMSlash(dateString: String): String {
        val from = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val date = from.parse(dateString)
        val myFormat = "yyyy/MM/dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        return sdf.format(date!!)
    }

    fun getBitmap(photo: String?): Bitmap? {
        val decodedByte = Base64.decode(photo, 0)
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
    }


    fun timeConvert(date: String): String? {
        val _24HourSDF = SimpleDateFormat("HH:mm",Locale.US)
        val _12HourSDF = SimpleDateFormat("hh:mm a",Locale.US)
        var _12HourDt: Date? = null
        try {
            _12HourDt = _12HourSDF.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return _24HourSDF.format(_12HourDt!!)
    }

    fun getFormattedDate(workingDate: String?): String? {
        try {
            val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val dateFormat = dateFormatter.parse(workingDate!!)
            val stringDateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return stringDateFormatter.format(dateFormat!!)
        } catch (pe: ParseException) {
            pe.printStackTrace()
        }
        return ""
    }

    fun getFormattedDateForShow(workingDate: String?): String? {
        try {
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val dateFormat = dateFormatter.parse(workingDate)
            val stringDateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            return stringDateFormatter.format(dateFormat)
        } catch (pe: ParseException) {
            pe.printStackTrace()
        }
        return ""
    }

    fun getCurrentDate(format: String?): String? {
        val c = Calendar.getInstance().time
        println("Current time => $c")
        val df = SimpleDateFormat(format, Locale.getDefault())
        return df.format(c)
    }

    fun onDownloadComplete(url: File, mContext: Context) {
        try {

            // Create URI
            val uri = FileProvider.getUriForFile(
                mContext, mContext.packageName.toString() + ".fileprovider",
                url
            )
            val intent = Intent(Intent.ACTION_VIEW)
            if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword")
            } else if (url.toString().contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf")
            } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint")
            } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel")
            } else if (url.toString().contains(".zip") || url.toString().contains(".rar")) {
                // WAV audio file
                intent.setDataAndType(uri, "application/x-wav")
            } else if (url.toString().contains(".rtf")) {
                // RTF file
                intent.setDataAndType(uri, "application/rtf")
            } else if (url.toString().contains(".wav") || url.toString().contains(".mp3")) {
                // WAV audio file
                intent.setDataAndType(uri, "audio/x-wav")
            } else if (url.toString().contains(".gif")) {
                // GIF file
                intent.setDataAndType(uri, "image/gif")
            } else if (url.toString().contains(".jpg") || url.toString()
                    .contains(".jpeg") || url.toString().contains(".png")
            ) {
                // JPG file
                intent.setDataAndType(uri, "i mage/jpeg")
            } else if (url.toString().contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain")
            } else if (url.toString().contains(".3gp") || url.toString()
                    .contains(".mpg") || url.toString().contains(".mpeg") || url.toString()
                    .contains(".mpe") || url.toString().contains(".mp4") || url.toString()
                    .contains(".avi")
            ) {
                intent.setDataAndType(uri, "video/*")
            } else {
                intent.setDataAndType(uri, "*/*")
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION)
            mContext.startActivity(intent)

        } catch (e: Exception) {
            Toast.makeText(MyApplication.instance, "You don't have application to open file", Toast.LENGTH_LONG)
                .show()
        }
    }


    fun getCountOfDays(createdDateString: String, expireDateString: String): Int {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        var createdConvertedDate: Date? = null
        var expireCovertedDate: Date? = null
        var todayWithZeroTime: Date? = null
        try {
            createdConvertedDate = dateFormat.parse(createdDateString)
            expireCovertedDate = dateFormat.parse(expireDateString)
            val today = Date()
            todayWithZeroTime = dateFormat.parse(dateFormat.format(today))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        var cYear = 0
        var cMonth = 0
        var cDay = 0
        if (createdConvertedDate!!.after(todayWithZeroTime)) {
            val cCal = Calendar.getInstance()
            cCal.time = createdConvertedDate
            cYear = cCal[Calendar.YEAR]
            cMonth = cCal[Calendar.MONTH]
            cDay = cCal[Calendar.DAY_OF_MONTH]
        } else {
            val cCal = Calendar.getInstance()
            cCal.time = todayWithZeroTime
            cYear = cCal[Calendar.YEAR]
            cMonth = cCal[Calendar.MONTH]
            cDay = cCal[Calendar.DAY_OF_MONTH]
        }


        /*Calendar todayCal = Calendar.getInstance();
    int todayYear = todayCal.get(Calendar.YEAR);
    int today = todayCal.get(Calendar.MONTH);
    int todayDay = todayCal.get(Calendar.DAY_OF_MONTH);
    */
        val eCal = Calendar.getInstance()
        eCal.time = expireCovertedDate
        val eYear = eCal[Calendar.YEAR]
        val eMonth = eCal[Calendar.MONTH]
        val eDay = eCal[Calendar.DAY_OF_MONTH]
        val date1 = Calendar.getInstance()
        val date2 = Calendar.getInstance()
        date1.clear()
        date1[cYear, cMonth] = cDay
        date2.clear()
        date2[eYear, eMonth] = eDay
        val diff = date2.timeInMillis - date1.timeInMillis
        val dayCount = diff.toFloat() / (24 * 60 * 60 * 1000)
        return dayCount.toInt()
    }

    fun getDateForFeesDDMMYYYY(workingDate: String): String? {
        try {
            return if (workingDate.isNotEmpty()) {
                val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                val dateFormat = dateFormatter.parse(workingDate)
                val stringDateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                stringDateFormatter.format(dateFormat)
            } else {
                ""
            }
        } catch (pe: ParseException) {
            pe.printStackTrace()
        }
        return ""
    }

    fun getDateForNonAcademicDDMMYYYY(workingDate: String): String? {
        try {
            return if (workingDate.isNotEmpty()) {
                val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                val dateFormat = dateFormatter.parse(workingDate)
                val stringDateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                stringDateFormatter.format(dateFormat)
            } else {
                ""
            }
        } catch (pe: ParseException) {
            pe.printStackTrace()
        }
        return ""
    }

    fun getFormattedDateForFees(workingDate: String): String? {
        try {
            return if (!workingDate.isEmpty()) {
                val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val dateFormat = dateFormatter.parse(workingDate)
                val stringDateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                stringDateFormatter.format(dateFormat)
            } else {
                ""
            }
        } catch (pe: ParseException) {
            pe.printStackTrace()
        }
        return ""
    }
    fun compressImage(
        imageSource: ImageSource,
        imgPath: String,
        fileCompressCallback: FileCompressCallback
    ) {
        Log.e("File Path", "$imgPath ")
        val options = BitmapFactory.Options()
        var bitmap = BitmapFactory.decodeFile(imgPath, options)
        var width = bitmap.width
        var height = bitmap.height
/*
        if (width > 800 && height > 800) {
            width = width / 4
            height = height / 4
            if (width <= 800 || height <= 800) {
                height = 1200
                width = 800
            }
        }
*/
        try {
            val dry: File
            val file: File
            var fileName = ""
            if (imageSource === ImageSource.GALLERY) {
                fileName = "IMG_" + SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                    .toString() + ".jpg"
                dry = File(
                    Environment.getExternalStorageDirectory().absolutePath
                        .toString() + "/" + Environment.DIRECTORY_DOWNLOADS
                )
                if (!dry.exists()) {
                    dry.mkdirs()
                }
                file = File(dry, fileName)
                if (file.exists()) file.delete()
            } else {
                file = File(imgPath)
                fileName = file.getName()
            }
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true)


            val ei: ExifInterface = ExifInterface(imgPath)
            val orientation = ei.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED
            )

            var rotatedBitmap: Bitmap? = null
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> rotatedBitmap = rotateImage(bitmap, 90)
                ExifInterface.ORIENTATION_ROTATE_180 -> rotatedBitmap = rotateImage(bitmap, 180)
                ExifInterface.ORIENTATION_ROTATE_270 -> rotatedBitmap = rotateImage(bitmap, 270)
                ExifInterface.ORIENTATION_NORMAL -> rotatedBitmap = bitmap
                else -> rotatedBitmap = bitmap
            }

            val fileOut = FileOutputStream(file)
            rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, fileOut)
            fileCompressCallback.getCompressedFilePath(file.absolutePath, fileName)
        } catch (fne: FileNotFoundException) {
            Log.e("ExceptionNNNN", fne.toString().toString() + " ")
        }
    }


    interface FileCompressCallback {
        fun getCompressedFilePath(filePath: String?, fileName: String?)
    }

    fun getNoInternetDialog(
        activity: Activity,
        lifecycles: Lifecycle
    ) {

        var d = NoInternetDialogSignal.Builder(
            activity,
            lifecycles
        ).apply {
            dialogProperties.apply {
                connectionCallback = object : ConnectionCallback { // Optional
                    override fun hasActiveConnection(hasActiveConnection: Boolean) {

                    }
                }

                cancelable = false // Optional
                noInternetConnectionTitle = "No Internet" // Optional
                noInternetConnectionMessage =
                    "Check your Internet connection and try again." // Optional
                showInternetOnButtons = true // Optional
                pleaseTurnOnText = "Please turn on" // Optional
                wifiOnButtonText = "Wifi" // Optional
                mobileDataOnButtonText = "Mobile data" // Optional

                onAirplaneModeTitle = "No Internet" // Optional
                onAirplaneModeMessage = "You have turned on the airplane mode." // Optional
                pleaseTurnOffText = "Please turn off" // Optional
                airplaneModeOffButtonText = "Airplane mode" // Optional
                showAirplaneModeOffButtons = true // Optional
            }
        }
        d.build()

    }


    fun checkPermissionValid(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkPermission(context)
        } else
            return true
    }


    private fun checkPermission(context: Context): Boolean {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED /*|| ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED*/ || ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                PERMISSIONS,
                0
            )
        } else {
            return true
        }
        return false
    }


}

