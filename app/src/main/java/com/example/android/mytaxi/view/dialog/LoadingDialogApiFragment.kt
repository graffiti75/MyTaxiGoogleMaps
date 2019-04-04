package com.example.android.mytaxi.view.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.util.Log
import br.cericatto.mytaxitest.domain.retrofit.MyTaxiServiceRetrofit
import com.example.android.mytaxi.AppConfiguration
import com.example.android.mytaxi.R
import com.example.android.mytaxi.view.activity.test.TestTask1Activity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@SuppressLint("ValidFragment")
class LoadingDialogApiFragment @Inject constructor(var mActivity: TestTask1Activity): DialogFragment() {

    //--------------------------------------------------
    // Constants
    //--------------------------------------------------

    companion object {
        const val TAG = "LoadingApi"
    }

    //--------------------------------------------------
    // Dialog Fragment
    //--------------------------------------------------

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        getData(this)

        return AlertDialog.Builder(mActivity)
            .setTitle(R.string.dialog_loading)
            .setMessage(R.string.please_wait)
            .create()
    }

    private fun getData(fragment: LoadingDialogApiFragment) {
        MyTaxiServiceRetrofit.getData()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    fragment.dismiss()
                    mActivity.onLoadingFinished()
                },
                {
                    fragment.dismiss()
                    Log.e(AppConfiguration.TAG, it.message)
                },
                { fragment.dismiss() }
            )
    }
}