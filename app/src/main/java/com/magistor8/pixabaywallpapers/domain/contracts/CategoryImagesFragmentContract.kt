package com.magistor8.pixabaywallpapers.domain.contracts

import androidx.lifecycle.LiveData
import com.magistor8.pixabaywallpapers.data.retrofit.entires.ImagesData

interface CategoryImagesFragmentContract {

    sealed interface ViewState {
        data class Error(val throwable: Throwable) : ViewState
        data class Success(val data: ImagesData) : ViewState
    }

    sealed interface Events {
        object LoadMore: Events
    }

    interface ViewModelInterface {
        val viewState: LiveData<ViewState>
        fun onEvent(event: Events)
    }
}