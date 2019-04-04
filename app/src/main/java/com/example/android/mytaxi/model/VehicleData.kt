package br.cericatto.mytaxitest.model

import com.example.android.mytaxi.model.Coordinate

data class VehicleData(
    var id: Long,
    var coordinate: Coordinate,
    var fleetType: String,
    var heading: String)