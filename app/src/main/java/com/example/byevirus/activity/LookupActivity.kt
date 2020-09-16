package com.example.byevirus.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.example.byevirus.R
import com.example.byevirus.model.LookUp
import kotlinx.android.synthetic.main.activity_look_up.*
import com.example.byevirus.adapter.LookUpAdapter
import com.example.byevirus.constants.ApiUrl.Companion.LOOKUP_API_URL
import okhttp3.*
import org.json.JSONArray
import java.io.IOException
import java.lang.Exception

class LookupActivity : AppCompatActivity() {
    companion object {
        const val Lookup = "LookUp"
    }

    private var skeletonScreen: SkeletonScreen? = null

    private val okHttpClient = OkHttpClient()
    private lateinit var filteredLookUpList: MutableList<LookUp>
    private lateinit var lookupList: MutableList<LookUp>
    private val mockLookUpList = mutableListOf(
        LookUp(
            provinceName = "Loading...",
            numberOfPositiveCases = " ",
            numberOfRecoveredCases = " ",
            numberOfDeathCases = " "
        ),
    )
    fun filterData(search: String) {
        val f = lookupList.filter { lookUp ->
            lookUp.provinceName.contains(search, true)
        }
        filteredLookUpList = f as MutableList<LookUp>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_look_up)

        val arrowClickBack = findViewById<ImageView>(R.id.ImageView_back)

        arrowClickBack.setOnClickListener {
            backToMainPage()
        }

        val lookUpAdapter = LookUpAdapter(mockLookUpList)
        rvlookup.layoutManager = LinearLayoutManager(this)
        rvlookup.adapter = lookUpAdapter

        val textEdit = findViewById<EditText>(R.id.Search)
        textEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(text: Editable?) {
                filterData(text.toString())
                lookUpAdapter.updateData(filteredLookUpList)
            }

            override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })

        val recyclerView = findViewById<RecyclerView>(R.id.rvlookup)
        skeletonScreen = Skeleton.bind(recyclerView)
            .adapter(lookUpAdapter)
            .load(R.layout.lookup_view_skeleton)
            .count(7)
            .show()

        val request: Request = Request.Builder()
            .url(LOOKUP_API_URL)
            .build()
        okHttpClient.newCall(request).enqueue(getCallback(lookUpAdapter))

    }

    private fun backToMainPage() {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(Lookup, "this is from look up activity")
        }
        startActivity(intent)
    }

    private fun getCallback(lookUpAdapter: LookUpAdapter): Callback {
        return object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                this@LookupActivity.runOnUiThread {
                    Toast.makeText(this@LookupActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val jsonString: String? = response.body?.string()
                    val jsonArray = JSONArray(jsonString)
                    val lookUpListFromNetwork: MutableList<LookUp> = mutableListOf<LookUp>()

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
                    this@LookupActivity.runOnUiThread {
                        skeletonScreen?.hide()
                        lookupList = lookUpListFromNetwork
                        filteredLookUpList = lookUpListFromNetwork
                        lookUpAdapter.updateData(filteredLookUpList)
                    }
                } catch (e: Exception) {
                    this@LookupActivity.runOnUiThread {
                        Toast.makeText(this@LookupActivity, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
