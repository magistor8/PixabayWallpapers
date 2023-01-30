package com.magistor8.pixabaywallpapers.data.retrofit

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.magistor8.pixabaywallpapers.App
import com.magistor8.pixabaywallpapers.data.retrofit.entires.ImagesData
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RemoteDataSource {

    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(App.instance.baseUri)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
        .create(Api::class.java)

    suspend fun getImagesByCategory(category: String): Response<ImagesData> = api.getImagesByCategory(category = category).await()
    suspend fun getImagesByCategoryAndPage(category: String, page: Int): Response<ImagesData> = api.getImagesByCategory(category = category, page = page).await()

}