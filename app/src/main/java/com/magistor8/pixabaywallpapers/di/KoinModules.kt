package com.magistor8.pixabaywallpapers.di

import com.magistor8.pixabaywallpapers.data.CategoryRepoImpl
import com.magistor8.pixabaywallpapers.data.retrofit.RemoteDataSource
import com.magistor8.pixabaywallpapers.domain.repos.CategoryRepo
import com.magistor8.pixabaywallpapers.view.category.CategoryFragment
import com.magistor8.pixabaywallpapers.view.category.CategoryFragmentViewModel
import com.magistor8.pixabaywallpapers.view.category_images.CategoryImagesAdapter
import com.magistor8.pixabaywallpapers.view.category_images.CategoryImagesFragment
import com.magistor8.pixabaywallpapers.view.category_images.CategoryImagesFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModule = module {

    single<CategoryRepo> {CategoryRepoImpl(RemoteDataSource())}

    scope<CategoryFragment> {
        viewModel { CategoryFragmentViewModel() }
    }

    scope<CategoryImagesFragment> {
        viewModel { CategoryImagesFragmentViewModel() }
        scoped { CategoryImagesAdapter() }
    }
}