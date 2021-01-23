package com.davidmendozamartinez.movieinfo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.davidmendozamartinez.movieinfo.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupList()
    }

    private fun setupList() {
        val adapter = MovieAdapter()
        binding.movieList.adapter = adapter
        viewModel.movies.observe(this) {
            adapter.submitList(it)
        }
    }
}