package com.neotreks.accuterra.mapboxdemo

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.google.android.material.button.MaterialButton

class FirstSdkInitDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_first_sdk_init)

        findViewById<MaterialButton>(R.id.ok_button).setOnClickListener {
            dismiss()
        }
    }
}