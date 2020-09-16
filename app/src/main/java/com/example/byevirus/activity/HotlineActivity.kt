package com.example.byevirus.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.byevirus.R
import com.example.byevirus.adapter.HotlineAdapter
import com.example.byevirus.constants.ApiUrl.Companion.HOTLINES_API_URL
import com.example.byevirus.contract.HotlineContract
import com.example.byevirus.entity.Hotline
import com.example.byevirus.entity.LookUp
import kotlinx.android.synthetic.main.activity_hotline_bottom_sheet.*
import okhttp3.*
import org.json.JSONArray
import java.io.IOException


class HotlineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotline_bottom_sheet)
        rvhotline.layoutManager = LinearLayoutManager(this)
    }
}
