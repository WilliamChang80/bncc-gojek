package com.example.byevirus.contract

import com.example.byevirus.entity.LookUp

interface LookupContract {
    interface View {
        fun updateData(caseList: MutableList<LookUp>)
        fun onError(error: Exception)
        fun startLoading()
        fun stopLoading()
    }

    interface Presenter {
        fun getData()
        fun filterData(search: String)
    }

    interface Repository {
        fun getData(): List<LookUp>
    }
}