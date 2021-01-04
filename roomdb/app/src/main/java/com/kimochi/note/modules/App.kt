package com.kimochi.note.modules

import android.app.Application
import com.kimochi.note.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application(){

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(listOf(
                viewModelModule,
                databaseModule,
                repositoryModule
            ))
        }
    }
}