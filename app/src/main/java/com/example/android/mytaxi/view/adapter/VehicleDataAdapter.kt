package com.example.android.mytaxi.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.cericatto.mytaxitest.model.VehicleData
import com.example.android.mytaxi.R
import com.example.android.mytaxi.view.activity.Task1Activity

class VehicleDataAdapter(private val mActivity: Task1Activity, private val mItems: List<VehicleData>?)
    : RecyclerView.Adapter<VehicleDataAdapter.VehicleDataViewHolder>() {

    //--------------------------------------------------
    // Adapter Methods
    //--------------------------------------------------

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleDataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_vehicle_data, parent, false)
        return VehicleDataViewHolder(view)
    }

    override fun onBindViewHolder(holder: VehicleDataViewHolder, position: Int) {
        val item = mItems!![position]

        val idTextView = holder.itemView.findViewById<TextView>(R.id.id_adapter_vehicle_data__id_text_view)
        idTextView.text = mActivity.getString(R.string.adapter_id) + " " + item.id.toString()

        val latTextView = holder.itemView.findViewById<TextView>(R.id.id_adapter_vehicle_data__lat_text_view)
        val lat = item.coordinate.latitude
        latTextView.text = mActivity.getString(R.string.adapter_lat) + " " + lat.substring(0, 10)

        val lonTextView = holder.itemView.findViewById<TextView>(R.id.id_adapter_vehicle_data__lon_text_view)
        val lon = item.coordinate.longitude
        lonTextView.text = mActivity.getString(R.string.adapter_lon) + " " + lon.substring(0, 10)

        val cityTextView = holder.itemView.findViewById<TextView>(R.id.id_adapter_vehicle_data__fleet_type_text_view)
        cityTextView.text = mActivity.getString(R.string.adapter_fleet_type) + " " + item.fleetType
    }

    override fun getItemCount(): Int {
        return if (mItems != null && mItems.isNotEmpty()) {
            mItems.size
        } else 0
    }

    //--------------------------------------------------
    // View Holder
    //--------------------------------------------------

    inner class VehicleDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}