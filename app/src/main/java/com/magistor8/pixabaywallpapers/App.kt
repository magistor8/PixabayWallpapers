package com.magistor8.pixabaywallpapers

import android.app.Application
import com.magistor8.pixabaywallpapers.di.myModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    val baseUri = "https://pixabay.com/"

    companion object {
        lateinit var instance: App
            private set

        const val BUNDLE_KEY = "BUNDLE_KEY"
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin{
            androidContext(this@App)
            modules(myModule)
        }
    }

}