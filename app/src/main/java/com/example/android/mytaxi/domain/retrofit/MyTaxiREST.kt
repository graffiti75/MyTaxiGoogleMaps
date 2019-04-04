package com.example.android.mytaxi.domain.retrofit

import br.cericatto.mytaxitest.model.PoiList
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MyTaxiREST {
    @GET(".")
    fun getData(
        @Query("p1Lat") p1Lat: String = "53.694865",
        @Query("p1Lon") p1Lon: String = "9.757589",
        @Query("p2Lat") p2Lat: String = "53.394655",
        @Query("p2Lon") p2Lon: String = "10.099891"): Observable<PoiList>
}