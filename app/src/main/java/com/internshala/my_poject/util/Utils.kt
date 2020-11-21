package com.internshala.my_poject.util

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import java.util.regex.Pattern

/**
 * Created by Rahul Abrol on 31/8/20.
 */
object Utils {

    fun noInternetDialog(
        context: Context,
        onPositiveButtonClick: (DialogInterface, Int) -> Unit,
        onNegativeButtonClick: (DialogInterface, Int) -> Unit
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error")
        builder.setMessage("No Internet Connection found. Please connect to the internet and re-open the app.")
        builder.setCancelable(false)
        builder.setPositiveButton(/*Retry*/"Ok", onPositiveButtonClick)
//        builder.setNegativeButton("Cancel", onNegativeButtonClick)
        builder.create()
        builder.show()
    }
}

object Extentions {
    fun String.isEmailValid() = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    ).matcher(this).matches()
}