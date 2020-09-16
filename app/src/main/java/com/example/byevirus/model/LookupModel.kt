package com.example.byevirus.model

import com.example.byevirus.constants.ApiUrl
import okhttp3.OkHttpClient
import okhttp3.Request

class LookupModel : BaseModel() {
    override fun getData(callback: okhttp3.Callback) {
        val request: Request = Request.Builder()
            .url(ApiUrl.LOOKUP_API_URL)
            .build()
        okHttpClient.newCall(request).enqueue(callback)
    }
}