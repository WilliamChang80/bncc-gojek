package com.example.byevirus.model

import okhttp3.OkHttpClient

abstract class BaseModel {
    val okHttpClient = OkHttpClient()

    abstract fun getData(callback: okhttp3.Callback)

}