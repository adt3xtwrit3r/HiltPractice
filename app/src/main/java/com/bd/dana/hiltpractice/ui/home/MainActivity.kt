package com.bd.dana.hiltpractice.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.bd.dana.hiltpractice.R
import com.bd.dana.hiltpractice.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel.getTvShows().observe(this, Observer { list ->


            viewModel.getTvShowsEpi().observe(this, Observer { it ->
                if (!list.isNullOrEmpty() && !it.tvShows.isNullOrEmpty()){
                    binding?.dataCount?.text = "${list.size} , ${it.tvShows.size}"
                }
            })



        })
    }
}