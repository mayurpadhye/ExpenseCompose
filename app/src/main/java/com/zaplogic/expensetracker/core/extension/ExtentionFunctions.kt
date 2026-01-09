package com.zaplogic.expensetracker.core.extension

import android.content.Context
import android.widget.Toast

object ExtensionFunctions{

    fun Context.showToastMs(msg : String){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }

}