package com.example.byevirus.presenter

import com.example.byevirus.adapter.LookUpAdapter
import com.example.byevirus.contract.LookupContract
import com.example.byevirus.entity.LookUp
import com.example.byevirus.model.LookupModel
import okhttp3.*
import org.json.JSONArray
import java.io.IOException
import java.lang.Exception

class LookupPresenter(val model: LookupModel, val view: LookupContract.View) :
    LookupContract.Presenter {

    private lateinit var lookUpList: MutableList<LookUp>
    override fun getData() {
        model.getData(getCallback())
    }

    override fun filterData(search: String) {
        val filteredLookUpList = lookUpList.filter { lookUp ->
            lookUp.provinceName.contains(search, true)
        }
        view.updateData(filteredLookUpList as MutableList<LookUp>)
    }

    private fun getCallback(): Callback {
        return object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                view.onError(e)
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val jsonString: String? = response.body?.string()
                    val jsonArray = JSONArray(jsonString)
                    val lookUpListFromNetwork: MutableList<LookUp> = mutableListOf()

                    for (i in 0 until jsonArray.length()) {
                        val attribute = jsonArray.getJSONObject(i).getJSONObject("attributes")
                        lookUpListFromNetwork.add(
                            LookUp(
                                provinceName = attribute.getString("Provinsi"),
                                numberOfPositiveCases = attribute.getString("Kasus_Posi"),
                                numberOfRecoveredCases = attribute.getString("Kasus_Semb"),
                                numberOfDeathCases = attribute.getString("Kasus_Meni")
                            )
                        )
                    }
                    lookUpList = lookUpListFromNetwork
                    view.updateData(lookUpListFromNetwork)
                } catch (e: Exception) {
                    view.onError(e)
                }
            }
        }
    }
}