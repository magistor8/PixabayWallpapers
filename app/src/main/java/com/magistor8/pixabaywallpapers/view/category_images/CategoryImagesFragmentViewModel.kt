package com.magistor8.pixabaywallpapers.view.category_images

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.magistor8.pixabaywallpapers.domain.BaseViewModel
import com.magistor8.pixabaywallpapers.domain.contracts.CategoryImagesFragmentContract
import com.magistor8.pixabaywallpapers.domain.repos.CategoryRepo
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.HttpException

class CategoryImagesFragmentViewModel : BaseViewModel(), CategoryImagesFragmentContract.ViewModelInterface, KoinComponent {

    var category: String = ""
    var page = 2
    var allDataLoaded = false

    private val repository : CategoryRepo by inject()
    override val viewState: LiveData<CategoryImagesFragmentContract.ViewState> = MutableLiveData()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewState.mutable().postValue(CategoryImagesFragmentContract.ViewState.Error(throwable))
    }

    override fun onEvent(event: CategoryImagesFragmentContract.Events) {
        when(event) {
            is CategoryImagesFragmentContract.Events.LoadMore -> getImagesByCategoryAndPage()
        }
    }

    private fun getImagesByCategoryAndPage() {
        if (allDataLoaded) return
        viewModelScope.launch(coroutineExceptionHandler) {
            val response = repository.getImagesByCategoryAndPage(category, page)
            if (response.isSuccessful && response.body() != null) {
                viewState.mutable().postValue(CategoryImagesFragmentContract.ViewState.Success(response.body()!!))
                page++
                if (response.body()!!.images.isEmpty()) allDataLoaded = true
            } else {
                viewState.mutable().postValue(CategoryImagesFragmentContract.ViewState.Error(HttpException(response)))
            }
        }
    }

}