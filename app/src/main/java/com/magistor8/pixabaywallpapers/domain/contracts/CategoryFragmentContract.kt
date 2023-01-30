package com.magistor8.pixabaywallpapers.domain.contracts

import androidx.lifecycle.LiveData
import com.magistor8.pixabaywallpapers.data.retrofit.entires.ImagesData

interface CategoryFragmentContract {

    sealed interface ViewState {
        data class Error(val throwable: Throwable) : ViewState
        data class Success(val data: ImagesData) : ViewState
        object Loading: ViewState
    }

    sealed interface Events {
        data class OpenCategory(val category: String): Events
    }

    interface ViewModelInterface {
        val viewState: LiveData<ViewState>
        fun onEvent(event: Events)
    }
}