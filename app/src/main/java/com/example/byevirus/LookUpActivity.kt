package com.example.byevirus

import android.content.Intent
import android.graphics.PointF.length
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.byevirus.lookup.LookUp
import kotlinx.android.synthetic.main.activity_look_up.*
import lookup.HotlineAdapter
import lookup.LookUpAdapter
import okhttp3.*
import org.json.JSONArray
import java.io.IOException
import java.lang.Exception
import java.util.*

//mutable list lebih multiplatform ketimbang array
class LookUpActivity : AppCompatActivity() {
    companion object {
        const val Lookup = "LookUp"

    }


    private val mockLookUpList = mutableListOf(
        LookUp(provinceName = "Loading...", numberOfPositiveCases = " ", numberOfRecoveredCases = " ", numberOfDeathCases = " " ),

    )

    private val okHttpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_look_up)

        val arrow_click_back = findViewById<ImageView>(R.id.ImageView_back)

        val lookUpAdapter = LookUpAdapter(mockLookUpList)
        rvlookup.layoutManager = LinearLayoutManager(this)
        rvlookup.adapter = lookUpAdapter

        arrow_click_back.setOnClickListener {
            backToMainPage()
        }
        val request: Request = Request.Builder()
            .url("https://api.kawalcorona.com/indonesia/provinsi/")
            .build()
        okHttpClient.newCall(request).enqueue(getCallback(lookUpAdapter))

    }
    private fun backToMainPage() {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(Lookup, "this is from look up activity")
        }
        startActivity(intent)
    }
        private fun getCallback(lookUpAdapter: LookUpAdapter): Callback{
            return object: Callback{
                override fun onFailure(call: Call, e: IOException) {
                    this@LookUpActivity.runOnUiThread{
                        Toast.makeText(this@LookUpActivity, e.message,Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    try {
                        val jsonString: String? = response.body?.string()
                        val jsonArray = JSONArray(jsonString)
                        val lookUpListFromNetwork: MutableList<LookUp> = mutableListOf<LookUp>()

                        for (i in 0 until jsonArray.length()){
                            //krn di json ada object attributes jadi harus di passing
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
                        this@LookUpActivity.runOnUiThread{
                            lookUpAdapter.updateData(lookUpListFromNetwork)
                        }
                    }
                    catch (e: Exception){
                        this@LookUpActivity.runOnUiThread{
                            Toast.makeText(this@LookUpActivity, e.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }


}







