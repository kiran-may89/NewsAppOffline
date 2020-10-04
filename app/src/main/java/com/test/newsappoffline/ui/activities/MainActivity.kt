package com.test.newsappoffline.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.test.newsappoffline.Injection
import com.test.newsappoffline.R
import com.test.newsappoffline.net.ServiceConfig
import com.test.newsappoffline.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by lazy {

        ViewModelProvider(this, Injection.providesMainFactory(this))
            .get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setupWithNavController(host_fragment.findNavController())

    }
}