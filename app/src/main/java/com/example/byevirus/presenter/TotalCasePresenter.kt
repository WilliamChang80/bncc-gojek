package com.example.byevirus.presenter

import android.util.Log
import com.example.byevirus.contract.TotalCaseContract
import com.example.byevirus.entity.TotalCase
import com.example.byevirus.model.TotalCaseModel
import okhttp3.Call
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException

class TotalCasePresenter(
    private val model: TotalCaseModel, private val view: TotalCaseContract.View
) : TotalCaseContract.Presenter {
    override fun getData() {
        model.getData(object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) {
                view.onError(e)
            }
            override fun onResponse(call: Call, response: Response) {
                try {
                    val jsonString: String? = response.body?.string()
                    val jsonArray = JSONArray(jsonString)
                    val homeListFromNetwork: MutableList<TotalCase> = mutableListOf()
                    for (i in 0 until jsonArray.length()) {
                        homeListFromNetwork.add(
                            TotalCase(
                                country = jsonArray.getJSONObject(i).getString("name"),
                                hospitalize = jsonArray.getJSONObject(i).getString("positif"),
                                positive = jsonArray.getJSONObject(i).getString("dirawat"),
                                recovered = jsonArray.getJSONObject(i).getString("sembuh"),
                                death = jsonArray.getJSONObject(i).getString("meninggal")
                            )
                        )
                    }
                    view.updateData(homeListFromNetwork[0])
                } catch (e: Exception) {
                    view.onError(e)
                }
            }
        })
    }
}