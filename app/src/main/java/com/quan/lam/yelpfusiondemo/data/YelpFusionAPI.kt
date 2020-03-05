package com.quan.lam.yelpfusiondemo.data

import com.quan.lam.yelpfusiondemo.BuildConfig
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


const val API_KEY = "O7jgb8-J69qAN5j3JH18cieo0fIhJfhBeaqYUiWDMrCEFsjz-liWdjfrUlxCI9IkM6eQLip0Lgvct0q0OLVu91KqDdLnq4HAqiH7MEHRHBXyu7m1QpILifGkB01gXnYx"
const val BASE_URL = "https://api.yelp.com/v3/"
interface YelpFusionAPI {
    @Headers("Authorization: Bearer $API_KEY")
    @GET("businesses/search")
    //@GET("businesses/search?term=delis&latitude=37.786882&longitude=-122.399972\n")
    fun searchBusinesses(
        @Query("term") term: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("offset") offset:Int = 0
    ): Observable<YelpBusinessResponse>

    @Headers("Authorization: Bearer $API_KEY")
    @GET("businesses/search")
    //@GET("businesses/search?term=delis&latitude=37.786882&longitude=-122.399972\n")
    fun searchBusinesses(
        @Query("term") term: String,
        @Query( "location") location: String,
        @Query("offset") offset:Int = 0
    ): Observable<YelpBusinessResponse>

    @Headers("Authorization: Bearer $API_KEY")
    @GET("businesses/{businessId}")
    fun getBusinessDetail(
        @Path("businessId") businessId: String
    ): Observable<BusinessDetail>

    companion object {
        private var api: YelpFusionAPI? = null
        fun getAPI(): YelpFusionAPI {
            if (api == null) {
                val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                api = retrofit.create(YelpFusionAPI::class.java)
            }
            return api!!
        }
        fun setAPI(yelpAPI: YelpFusionAPI) {
            if (BuildConfig.DEBUG)
                api = yelpAPI
        }
    }
}