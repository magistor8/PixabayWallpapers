package com.magistor8.pixabaywallpapers.data

import com.magistor8.pixabaywallpapers.data.retrofit.RemoteDataSource
import com.magistor8.pixabaywallpapers.data.retrofit.entires.ImagesData
import com.magistor8.pixabaywallpapers.domain.repos.CategoryRepo
import retrofit2.Response

class CategoryRepoImpl(
    private val dataSource: RemoteDataSource
) : CategoryRepo {
    override suspend fun getImagesByCategory(category: String): Response<ImagesData> = dataSource.getImagesByCategory(category)
    override suspend fun getImagesByCategoryAndPage(category: String, page: Int): Response<ImagesData> = dataSource.getImagesByCategoryAndPage(category, page)
}

