package com.iitms.bfr.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.iitms.bfr.R
import javax.inject.Inject


class ProgressDialogFragment @Inject constructor() : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.isCancelable = false
        return inflater.inflate(R.layout.fragment_progress_dialog, container, false)
    }

    /**
     * Displays progress dialog
     *
     * @param p_manager [FragmentManager]
     * @param message   String message
     */
    fun showProgressBar(
        p_manager: FragmentManager?,
        message: String
    ) {
        show(p_manager!!, "dialog")
    }

    @Deprecated("")
    fun showProgressBar() {
        try {
            show(requireActivity().supportFragmentManager, "dialog")
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    /**
     * Dismiss progress dialog
     */
    override fun dismiss() {
        try {
            super.dismiss()
        } catch (np: Exception) {
            np.printStackTrace()
        }
    }

}