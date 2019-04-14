package com.gg.compassproject.screens.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.gg.compassproject.R
import com.gg.compassproject.databinding.ActivityMainBinding
import com.gg.compassproject.extensions.rotateClockwise
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        activityMainBinding.lifecycleOwner = this
        activityMainBinding.viewModel = mainViewModel
        mainViewModel.startListening()

        mainViewModel.compassRotation.observe(this, Observer {
            ivCompass.animate().rotateClockwise(ivCompass,it).start()
        })
        mainViewModel.arrowRotation.observe(this, Observer {
            clDirection.animate().rotateClockwise(clDirection,it).start()
        })
    }
}
