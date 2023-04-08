package com.chaudharynabin6.newapp.other.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast

//https://lxadm.com/cant-toast-on-a-thread-that-has-not-called-looper-prepare/
class ShowToast(val context: Context) {
    fun showToast(toastMessage: String) {

        Handler(Looper.getMainLooper()).post(Runnable {
            Toast.makeText(
                context,
                toastMessage,
                Toast.LENGTH_SHORT
            ).show()
        })
    }
}


