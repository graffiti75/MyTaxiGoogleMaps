@file:Suppress("DEPRECATION")

package com.example.android.mytaxi.view.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import br.cericatto.mytaxitest.domain.retrofit.MyTaxiServiceRetrofit
import br.cericatto.mytaxitest.domain.utils.NavigationUtils
import br.cericatto.mytaxitest.domain.utils.Utils
import br.cericatto.mytaxitest.model.VehicleData
import com.example.android.mytaxi.R
import com.example.android.mytaxi.view.adapter.VehicleDataAdapter
import com.example.android.mytaxi.view.dialog.DialogUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class Task1Activity : AppCompatActivity() {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    // Context.
    private val mActivity = this@Task1Activity

    // Adapter.
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: VehicleDataAdapter

    private lateinit var mProgress: ProgressDialog

    //--------------------------------------------------
    // Activity Life Cycle
    //--------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task1)

        mProgress = DialogUtils.showProgressDialog(mActivity, getString(R.string.dialog_loading))
        setAdapter()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        NavigationUtils.animate(mActivity, NavigationUtils.Animation.BACK)
    }

    //--------------------------------------------------
    // Menu
    //--------------------------------------------------

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //--------------------------------------------------
    // Methods
    //--------------------------------------------------

    private fun setAdapter() {
        if (Utils.checkConnection(mActivity)) {
            getData()
        } else {
            DialogUtils.showNoConnectionDialog(mActivity)
        }
    }

    private fun getData() {
        MyTaxiServiceRetrofit.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                var list = it.poiList
                updateAdapter(list)
            }
    }

    private fun updateAdapter(list: List<VehicleData>) {
        if (list == null || list.isEmpty()) {
            Toast.makeText(mActivity, R.string.empty_list, Toast.LENGTH_LONG)
        } else {
            mAdapter = VehicleDataAdapter(mActivity, list)
            val layoutManager = LinearLayoutManager(mActivity)
            mRecyclerView = findViewById(R.id.id_activity_task1__recycler_view)
//            R.id.id_activity_task1__recycler_view
            mRecyclerView.layoutManager = layoutManager
//            mRecyclerView.itemAnimator = DefaultItemAnimator()
            mRecyclerView.adapter = mAdapter
            mRecyclerView.addItemDecoration(DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL))
            mProgress.dismiss()
        }
    }
}
