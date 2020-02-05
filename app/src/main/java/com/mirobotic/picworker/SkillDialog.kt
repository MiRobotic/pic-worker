package com.mirobotic.picworker

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import kotlinx.android.synthetic.main.dialog_skill.*
import java.util.*

class SkillDialog(context: Context, private val title: String, private val description: String): Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_skill)
        Objects.requireNonNull<Window>(window).setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )


        imgClose.setOnClickListener { dismiss() }

        tvTitle.text = title

        tvMsg.text = description


    }

}