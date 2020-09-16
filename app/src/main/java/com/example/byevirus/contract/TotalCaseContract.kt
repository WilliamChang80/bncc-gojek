package com.example.byevirus.contract

import com.example.byevirus.entity.TotalCase

interface TotalCaseContract {
    interface View {
        fun updateData(totalCase: TotalCase)
        fun onError(error: Exception)
        fun startLoading()
        fun stopLoading()
    }

    interface Presenter {
        fun getData()
    }
}