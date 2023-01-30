package com.magistor8.pixabaywallpapers.domain.repos

import com.magistor8.pixabaywallpapers.data.retrofit.entires.ImagesData
import retrofit2.Response

interface CategoryRepo {
    suspend fun getImagesByCategory(category: String) : Response<ImagesData>
    suspend fun getImagesByCategoryAndPage(category: String, page: Int): Response<ImagesData>
}