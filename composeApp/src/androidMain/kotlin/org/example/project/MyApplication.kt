package org.example.project

import android.app.Application
import di.initializeKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin()
    }
}