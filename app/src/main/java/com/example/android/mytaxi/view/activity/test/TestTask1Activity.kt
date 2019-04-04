package com.example.android.mytaxi.view.activity.test

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.example.android.mytaxi.R
import com.example.android.mytaxi.view.dialog.LoadingDialogApiFragment

class TestTask1Activity : AppCompatActivity() {

    //--------------------------------------------------
    // Activity Life Cycle
    //--------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_task1)

        val fragment = LoadingDialogApiFragment(this)
        fragment.isCancelable = false
        fragment.show(supportFragmentManager, LoadingDialogApiFragment.TAG)
    }

    //--------------------------------------------------
    // Callbacks
    //--------------------------------------------------

    fun onLoadingFinished() {
        val textView = findViewById<TextView>(R.id.id_activity_test_task1__text_view)
        textView.setText(R.string.dialog_ok)
    }
}