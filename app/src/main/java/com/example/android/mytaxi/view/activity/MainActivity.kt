package com.example.android.mytaxi.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import br.cericatto.mytaxitest.domain.utils.ActivityUtils
import br.cericatto.mytaxitest.domain.utils.Utils
import com.example.android.mytaxi.R
import com.example.android.mytaxi.view.dialog.DialogUtils

class MainActivity: AppCompatActivity() {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    // Context.
    private val mActivity = this@MainActivity

    //--------------------------------------------------
    // Activity Life Cycle
    //--------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val task1Button: Button = findViewById(R.id.id_activity_main__task_1_button)
        task1Button.setOnClickListener {
            checkConnection(Task1Activity::class.java)
        }

        val task2Button: Button = findViewById(R.id.id_activity_main__task_2_button)
        task2Button.setOnClickListener {
            checkConnection(Task2Activity::class.java)
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    //--------------------------------------------------
    // Methods
    //--------------------------------------------------

    private fun checkConnection(clazz: Class<*>) {
        if (Utils.checkConnection(mActivity)) {
            ActivityUtils.startActivity(mActivity, clazz)
        } else {
            DialogUtils.showNoConnectionDialog(mActivity)
        }
    }
}