package com.iitms.bfr.ui.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iitms.bfr.data.model.Status
import javax.inject.Inject

open class BaseViewModel @Inject constructor() : ViewModel() {

    var isNetworkAvailable: MutableLiveData<Boolean> = MutableLiveData()
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var failed: MutableLiveData<Status> = MutableLiveData()
    var success: MutableLiveData<Status> = MutableLiveData()
    var sessionExpire: MutableLiveData<Status> = MutableLiveData()

   /* @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var editor: SharedPreferences.Editor
*/
   /* @Inject
    lateinit var status: Status*/

   /* @Inject
    lateinit var common: Common*/

    fun isNetworkAvailable(isAvailable: Boolean): MutableLiveData<Boolean> {
        isNetworkAvailable.value = isAvailable
        return isNetworkAvailable
    }

    fun isLoadingDialog(isLoadingDialog: Boolean): MutableLiveData<Boolean> {
        isLoading.value = isLoadingDialog
        return isLoading
    }

    @Suppress("DEPRECATION")
    fun checkInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }


   /* fun downloadFile2(file: ResponseBody) {
        try {

            var uri: Uri? = null
            var count: Int
            val data = ByteArray(1024 * 4)
            val fileSize = file.contentLength()
            val bis: InputStream = BufferedInputStream(file.byteStream(), 1024 * 8)

            val fileName = "/Result_" + System.currentTimeMillis() + ".pdf"
            val resolver = MyApplication.instance.contentResolver

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                }
                uri =
                    resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)
                Log.d("Uri", "$uri")



                Log.v("EXCEPTION", FileUtil.from(MyApplication.instance, uri).toString())
            } else {

                var directory =
                    File(Environment.getExternalStorageDirectory().absolutePath + "/" + Environment.DIRECTORY_DOWNLOADS)
                if (!directory.exists()) {
                    directory.mkdir()
                }
                directory =
                    File(Environment.getExternalStorageDirectory().absolutePath + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + fileName)

                uri = Uri.fromFile(directory)

            }

            if (uri != null) {
                resolver.openOutputStream(uri).use {
                    while (bis.read(data).also { count = it } != -1) {
                        it?.write(data, 0, count)
                    }
                    it?.close()
                }
            }
            Toast.makeText(MyApplication.instance, "Download Completed.", Toast.LENGTH_LONG)
                .show()
            common.onDownloadComplete(
                FileUtil.from(MyApplication.instance, uri),
                MyApplication.instance
            )


        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Log.v("EXCEPTION", e.message!!)
            Toast.makeText(MyApplication.instance, "Report not generated.", Toast.LENGTH_LONG)
                .show()

        }
    }*/
}

