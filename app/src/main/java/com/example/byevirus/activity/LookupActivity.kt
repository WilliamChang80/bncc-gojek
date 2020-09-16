package com.example.byevirus.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.example.byevirus.R
import com.example.byevirus.entity.LookUp
import kotlinx.android.synthetic.main.activity_look_up.*
import com.example.byevirus.adapter.LookUpAdapter
import com.example.byevirus.contract.LookupContract
import com.example.byevirus.model.LookupModel
import com.example.byevirus.presenter.LookupPresenter
import kotlin.Exception

class LookupActivity : AppCompatActivity(), LookupContract.View {

    companion object {
        const val NUMBER_OF_LOADING_ITEMS = 7
    }

    private lateinit var skeletonScreen: SkeletonScreen
    private lateinit var lookupAdapter: LookUpAdapter
    private lateinit var lookupPresenter: LookupPresenter

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_look_up)
        lookupPresenter = LookupPresenter(LookupModel(), this)
        initializeAdapter()
        initializeBackButton()
        initializeLoadingComponents()
        initializeTextEdit()
        lookupPresenter.getData()
    }

    private fun initializeAdapter() {
        val initialLookupList: MutableList<LookUp> = mutableListOf()
        lookupAdapter = LookUpAdapter(initialLookupList)
        rvlookup.layoutManager = LinearLayoutManager(this)
        rvlookup.adapter = lookupAdapter
    }

    private fun initializeBackButton() {
        val arrowClickBack = findViewById<ImageView>(R.id.ImageView_back)
        arrowClickBack.setOnClickListener {
            backToMainPage()
        }
    }

    private fun initializeLoadingComponents() {
        val recyclerView = findViewById<RecyclerView>(R.id.rvlookup)
        skeletonScreen = Skeleton.bind(recyclerView)
            .adapter(lookupAdapter)
            .load(R.layout.lookup_view_skeleton)
            .count(NUMBER_OF_LOADING_ITEMS)
            .show()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initializeTextEdit() {

        val textEdit = findViewById<EditText>(R.id.Search)
        textEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(text: Editable?) {
                startLoading()
                lookupPresenter.filterData(text.toString())
            }

            override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        textEdit.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, event: MotionEvent?): Boolean {
                if (event?.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= textEdit.right - textEdit.totalPaddingRight) {
                        textEdit.setText("")
                        return true
                    }
                }
                return false
            }

        })
    }

    private fun backToMainPage() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun updateData(caseList: MutableList<LookUp>) {
        stopLoading()
        lookupAdapter.updateData(caseList)
    }

    override fun onError(error: Exception) {
        runOnUiThread {
            Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun startLoading() {
        runOnUiThread {
            skeletonScreen.show()
        }
    }

    override fun stopLoading() {
        runOnUiThread {
            skeletonScreen.hide()
        }
    }
}