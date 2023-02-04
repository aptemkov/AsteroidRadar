package com.github.aptemkov.asteroidradar.api

import com.github.aptemkov.asteroidradar.Constants
import com.github.aptemkov.asteroidradar.Constants.API_KEY
import com.github.aptemkov.asteroidradar.Constants.BASE_URL
import com.github.aptemkov.asteroidradar.PictureOfDay
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*

interface AsteroidsApi {

    @GET("neo/rest/v1/feed")
    fun getAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String = API_KEY,
    ): Deferred<ResponseBody>

    @GET("planetary/apod")
    fun getDayPicture(
        @Query("api_key") apiKey: String = API_KEY,
    ): Deferred<PictureOfDay>

}


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private fun formatDate(date: Date): String {
    val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
    return dateFormat.format(date)
}

object AsteroidApi {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()


    val api: AsteroidsApi = retrofit.create(AsteroidsApi::class.java)
}