package com.davidmendozamartinez.movieinfo.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.davidmendozamartinez.movieinfo.databinding.ActivityDetailsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private val viewModel: DetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.getMovieDetails(intent.extras?.getInt("id")!!)
    }
}