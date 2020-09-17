package com.example.byevirus.contract

import com.example.byevirus.entity.Hotline

interface HotlineContract {
    interface View {
        fun updateData(caseList: MutableList<Hotline>)
        fun onError(error: Exception)
        fun startLoading()
        fun stopLoading()
    }

    interface Presenter {
        fun getData()
    }
}