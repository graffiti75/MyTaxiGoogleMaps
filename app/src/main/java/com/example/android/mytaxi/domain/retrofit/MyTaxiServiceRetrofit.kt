package br.cericatto.mytaxitest.domain.retrofit

import br.cericatto.mytaxitest.model.PoiList
import com.example.android.mytaxi.domain.retrofit.MyTaxiREST
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object MyTaxiServiceRetrofit {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    private const val BASE_URL = "https://fake-poi-api.mytaxi.com/"
    private var mService: MyTaxiREST

    //--------------------------------------------------
    // Methods
    //--------------------------------------------------

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        mService = retrofit.create(MyTaxiREST::class.java)
    }

    fun getData(): Observable<PoiList> {
        return mService.getData()
    }
}