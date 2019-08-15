package com.tlz.tuiautomator.demo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.orhanobut.logger.Logger
import com.tlz.tuiautomator.TUiautomator
import com.tlz.tuiautomator.onFailure
import com.tlz.tuiautomator.onSuccess
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class MainActivity : AppCompatActivity() {

    private val automator = TUiautomator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_start.setOnClickListener {
            start()
        }

        initTask()
    }

    private fun initTask() {

    }

    private fun start() {

    }
}
