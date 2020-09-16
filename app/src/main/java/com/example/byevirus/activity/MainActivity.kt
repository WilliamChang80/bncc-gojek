package com.example.byevirus.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.example.byevirus.fragment.BottomSheetFragment
import com.example.byevirus.R
import com.example.byevirus.constants.ApiUrl.Companion.HOMEPAGE_API_URL
import com.example.byevirus.entity.TotalCase
import okhttp3.*
import org.json.JSONArray
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private val okHttpClient = OkHttpClient()

    lateinit var skeletonScreen: SkeletonScreen
    lateinit var caseSkeletonScreen: SkeletonScreen
    lateinit var positiveSkeletonScreen: SkeletonScreen
    lateinit var deathSkeletonScreen: SkeletonScreen
    lateinit var recoveredSkeletonScreen: SkeletonScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomSheetFragment =
            BottomSheetFragment()
        val arrow1 = findViewById<ImageView>(R.id.Image_arrow)
        val arrow2 = findViewById<ImageView>(R.id.Image_arrow2)

        val request: Request = Request.Builder()
            .url(HOMEPAGE_API_URL)
            .build()

        val titleSkeletonView = findViewById<TextView>(R.id.TextView_Indonesia)
        val caseSkeletonView = findViewById<TextView>(R.id.TextView_Jumlah)
        val positiveSkeletonView = findViewById<TextView>(R.id.TextView_Number_positive)
        val deathSkeletonView = findViewById<TextView>(R.id.TextView_Number_death)
        val recoveredSkeletonView = findViewById<TextView>(R.id.TextView_Number_recovered)

        skeletonScreen = Skeleton.bind(titleSkeletonView).load(R.layout.title_skeleton).show()
        caseSkeletonScreen = Skeleton.bind(caseSkeletonView).load(R.layout.case_skeleton).show()
        positiveSkeletonScreen = Skeleton.bind(positiveSkeletonView).load(R.layout.possitive_case_skeleton).show()
        recoveredSkeletonScreen = Skeleton.bind(recoveredSkeletonView).load(R.layout.recovered_case_skeleton).show()
        deathSkeletonScreen = Skeleton.bind(deathSkeletonView).load(R.layout.death_case_skeleton).show()

        okHttpClient.newCall(request).enqueue(getCallback())

        arrow1.setOnClickListener {
            openSecondPage()
        }
        arrow2.setOnClickListener {
            bottomSheetFragment.show(supportFragmentManager, "bottomSheetDialog")
        }


    }

    private fun getCallback(): Callback {
        return object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                this@MainActivity.runOnUiThread {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                }
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
                    this@MainActivity.runOnUiThread {
                        skeletonScreen.hide()
                        caseSkeletonScreen.hide()
                        positiveSkeletonScreen.hide()
                        recoveredSkeletonScreen.hide()
                        deathSkeletonScreen.hide()

                        findViewById<TextView>(R.id.TextView_Jumlah).text =
                            homeListFromNetwork[0].hospitalize
                        findViewById<TextView>(R.id.TextView_Number_positive).text =
                            homeListFromNetwork[0].positive
                        findViewById<TextView>(R.id.TextView_Number_recovered).text =
                            homeListFromNetwork[0].recovered
                        findViewById<TextView>(R.id.TextView_Number_death).text =
                            homeListFromNetwork[0].death
                        findViewById<TextView>(R.id.TextView_Indonesia).text =
                            homeListFromNetwork[0].country
                    }
                } catch (e: Exception) {
                    this@MainActivity.runOnUiThread {
                        Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

    private fun openSecondPage() {
        val intent = Intent(this, LookupActivity::class.java).apply {
            putExtra("extra", "This is from main activity")
        }
        startActivity(intent)
    }

}