package com.example.byevirus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.byevirus.model.Hotline
import com.example.byevirus.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_hotline_bottom_sheet.*
import com.example.byevirus.adapter.HotlineAdapter
import okhttp3.*
import org.json.JSONArray
import java.io.IOException

class BottomSheetFragment: BottomSheetDialogFragment(){

    private val mockHotlineList = mutableListOf(
        Hotline(
            name = "Loading...",
            imgIcon = "",
            phone = ""
        ),
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.activity_hotline_bottom_sheet,container,false)
        return view
    }

    //buat tambahin button click listener di dalam di bottom sheet
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val okHttpClient = OkHttpClient()

        val hotlineAdapter = HotlineAdapter(mockHotlineList)
        rvhotline.layoutManager = LinearLayoutManager(context)
        rvhotline.adapter = hotlineAdapter

        val close = view.findViewById<ImageView>(R.id.Image_X)

        close.setOnClickListener {
            this@BottomSheetFragment.dismiss()
        }


        val request: Request = Request.Builder()
            .url("https://bncc-corona-versus.firebaseio.com/v1/hotlines.json")
            .build()
        //new call buat request yang di prepare sama okhttp dan enqueue buat jalanin
        okHttpClient.newCall(request).enqueue(getCallback(hotlineAdapter))
    }
    private fun getCallback(hotlineAdapter: HotlineAdapter): Callback {
        //object buat bikin class dari interface
        return object : Callback {
            override fun onFailure(call: Call, e: IOException) {
               //pake activity krn fragment attach di activity
                this@BottomSheetFragment.activity?.runOnUiThread() {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }

            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val jsonString: String? = response.body?.string()
                    val jsonArray = JSONArray(jsonString)
                    val hotlineListFromNetwork: MutableList<Hotline> =
                        mutableListOf<Hotline>()

                    for (i in 0 until jsonArray.length()) {
                        hotlineListFromNetwork.add(
                            Hotline(
                                imgIcon = jsonArray.getJSONObject(i).getString("img_icon"),
                                phone = jsonArray.getJSONObject(i).getString("phone"),
                                name = jsonArray.getJSONObject(i).getString("name"),

                                )
                        )
                    }
                    this@BottomSheetFragment.activity?.runOnUiThread {
                        hotlineAdapter.updateData(hotlineListFromNetwork)
                    }

                } catch (e: Exception) {
                    this@BottomSheetFragment.activity?.runOnUiThread {
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
     }
    }
