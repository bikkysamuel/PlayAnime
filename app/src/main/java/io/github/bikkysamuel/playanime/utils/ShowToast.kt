package io.github.bikkysamuel.playanime.utils

import android.widget.Toast
import io.github.bikkysamuel.playanime.BaseApplication

class ShowToast {

    companion object {
        fun short(message: String) {
            Toast.makeText(
                BaseApplication.instance!!.applicationContext,
                message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}