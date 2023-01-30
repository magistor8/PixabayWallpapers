package com.magistor8.pixabaywallpapers.domain

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.magistor8.pixabaywallpapers.App

abstract class BaseViewModel : ViewModel() {

    fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
        return this as MutableLiveData<T>
    }

    fun isInternetAvailable(): Boolean {
        val context = App.instance.applicationContext
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetwork
        val capabilities = cm.getNetworkCapabilities(activeNetwork)
        return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

}