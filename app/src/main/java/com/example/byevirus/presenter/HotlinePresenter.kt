package com.example.byevirus.presenter

import com.example.byevirus.contract.HotlineContract
import com.example.byevirus.entity.Hotline
import com.example.byevirus.model.HotlineModel
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException

class HotlinePresenter(private val model: HotlineModel, private val view: HotlineContract.View) :
    HotlineContract.Presenter {
    override fun getData() {
        return model.getData(getCallback())
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
                    val hotlineListFromNetwork: MutableList<Hotline> = mutableListOf()

                    for (i in 0 until jsonArray.length()) {
                        hotlineListFromNetwork.add(
                            Hotline(
                                imgIcon = jsonArray.getJSONObject(i).getString("img_icon"),
                                phone = jsonArray.getJSONObject(i).getString("phone"),
                                name = jsonArray.getJSONObject(i).getString("name"),
                            )
                        )
                    }
                    view.updateData(hotlineListFromNetwork)
                } catch (e: Exception) {
                    view.onError(e)
                }
            }
        }
    }
}