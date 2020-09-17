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

import com.example.byevirus.constants.FragmentTag
import com.example.byevirus.contract.TotalCaseContract
import com.example.byevirus.entity.TotalCase
import com.example.byevirus.fragment.AboutFragment
import com.example.byevirus.model.TotalCaseModel
import com.example.byevirus.presenter.TotalCasePresenter


class MainActivity : AppCompatActivity(), TotalCaseContract.View {


    lateinit var titleSkeletonScreen: SkeletonScreen
    lateinit var caseSkeletonScreen: SkeletonScreen
    lateinit var positiveSkeletonScreen: SkeletonScreen
    lateinit var deathSkeletonScreen: SkeletonScreen
    lateinit var recoveredSkeletonScreen: SkeletonScreen
    lateinit var totalCasePresenter: TotalCasePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        totalCasePresenter = TotalCasePresenter(TotalCaseModel(), this)
        initializeArrowButtonAndBottomFragment()
        initializeAboutFragment()
        initializeLoadingComponents()
        totalCasePresenter.getData()
    }

    private fun initializeArrowButtonAndBottomFragment() {
        val activityArrow = findViewById<ImageView>(R.id.Image_arrow)
        val hotlineArrow = findViewById<ImageView>(R.id.Image_arrow2)
        val surveyArrow = findViewById<ImageView>(R.id.Image_arrow3)
        val bottomSheetFragment = BottomSheetFragment()
        activityArrow.setOnClickListener {
            openLookupActivity()
        }
        hotlineArrow.setOnClickListener {
            bottomSheetFragment.show(supportFragmentManager, FragmentTag.BOTTOM_SHEET_TAG)
        }
        surveyArrow.setOnClickListener {
            openSurveyPage()
        }
    }


    private fun initializeLoadingComponents() {
        val titleSkeletonView = findViewById<TextView>(R.id.TextView_Indonesia)
        val caseSkeletonView = findViewById<TextView>(R.id.TextView_Jumlah)
        val positiveSkeletonView = findViewById<TextView>(R.id.TextView_Number_positive)
        val deathSkeletonView = findViewById<TextView>(R.id.TextView_Number_death)
        val recoveredSkeletonView = findViewById<TextView>(R.id.TextView_Number_recovered)

        titleSkeletonScreen = Skeleton.bind(titleSkeletonView).load(R.layout.title_skeleton).show()
        caseSkeletonScreen = Skeleton.bind(caseSkeletonView).load(R.layout.case_skeleton).show()
        positiveSkeletonScreen =
            Skeleton.bind(positiveSkeletonView).load(R.layout.possitive_case_skeleton).show()
        recoveredSkeletonScreen =
            Skeleton.bind(recoveredSkeletonView).load(R.layout.recovered_case_skeleton).show()
        deathSkeletonScreen =
            Skeleton.bind(deathSkeletonView).load(R.layout.death_case_skeleton).show()
    }

    private fun initializeAboutFragment() {
        val aboutBtn = findViewById<ImageView>(R.id.Image_info)
        val aboutFragment = AboutFragment()

        aboutBtn.setOnClickListener {
            aboutFragment.show(supportFragmentManager, "bottomSheetDialog")
        }
    }

    private fun openLookupActivity() {
        val intent = Intent(this, LookupActivity::class.java).apply {
            putExtra("extra", "This is from main activity")
        }
        startActivity(intent)
    }

    override fun updateData(totalCase: TotalCase) {
        stopLoading()
        runOnUiThread {
            findViewById<TextView>(R.id.TextView_Jumlah).text =
                totalCase.hospitalize
            findViewById<TextView>(R.id.TextView_Number_positive).text =
                totalCase.positive
            findViewById<TextView>(R.id.TextView_Number_recovered).text =
                totalCase.recovered
            findViewById<TextView>(R.id.TextView_Number_death).text =
                totalCase.death
            findViewById<TextView>(R.id.TextView_Indonesia).text =
                totalCase.country
        }
    }

    override fun onError(error: Exception) {
        runOnUiThread {
            Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun startLoading() {
        runOnUiThread {
            titleSkeletonScreen.show()
            caseSkeletonScreen.show()
            positiveSkeletonScreen.show()
            recoveredSkeletonScreen.show()
            deathSkeletonScreen.show()
        }
    }

    private fun openSurveyPage() {
        val intent = Intent(this, survey::class.java).apply {
            putExtra("extra", "This is from main activity")
        }
        startActivity(intent)

    }

    override fun stopLoading() {
        runOnUiThread {
            titleSkeletonScreen.hide()
            caseSkeletonScreen.hide()
            positiveSkeletonScreen.hide()
            recoveredSkeletonScreen.hide()
            deathSkeletonScreen.hide()
        }

    }

}