package com.magistor8.pixabaywallpapers.data.retrofit

import com.magistor8.pixabaywallpapers.data.retrofit.entires.ImagesData
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("api/")
    fun getImagesByCategory(
        @Query("key") key: String = "33106230-b104905cd7ff74ed17e2229af",
        @Query("category") category: String,
        @Query("page") page: Int = 1,
        @Query("per_page") pp: Int = 100
    ): Deferred<Response<ImagesData>>
}
