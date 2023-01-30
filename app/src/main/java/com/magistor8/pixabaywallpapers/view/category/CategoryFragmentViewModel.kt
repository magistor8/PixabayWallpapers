package com.magistor8.pixabaywallpapers.view.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.magistor8.pixabaywallpapers.domain.BaseViewModel
import com.magistor8.pixabaywallpapers.domain.contracts.CategoryFragmentContract
import com.magistor8.pixabaywallpapers.domain.repos.CategoryRepo
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.HttpException

class CategoryFragmentViewModel : BaseViewModel(), CategoryFragmentContract.ViewModelInterface, KoinComponent {

    private val repository : CategoryRepo by inject()
    override val viewState: LiveData<CategoryFragmentContract.ViewState> = MutableLiveData()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewState.mutable().postValue(CategoryFragmentContract.ViewState.Error(throwable))
    }

    override fun onEvent(event: CategoryFragmentContract.Events) {
        when(event) {
            is CategoryFragmentContract.Events.OpenCategory -> getImagesByCategory(event.category)
        }
    }

    private fun getImagesByCategory(category: String) {
        viewState.mutable().postValue(CategoryFragmentContract.ViewState.Loading)
        viewModelScope.launch(coroutineExceptionHandler) {
            val response = repository.getImagesByCategory(category)
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.category = category
                viewState.mutable().postValue(CategoryFragmentContract.ViewState.Success(response.body()!!))
            } else {
                viewState.mutable().postValue(CategoryFragmentContract.ViewState.Error(HttpException(response)))
            }
        }
    }

}