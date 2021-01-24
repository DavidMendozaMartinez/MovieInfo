package com.davidmendozamartinez.movieinfo.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.davidmendozamartinez.movieinfo.R
import com.davidmendozamartinez.movieinfo.databinding.ActivityMainBinding
import com.davidmendozamartinez.movieinfo.presentation.model.Section
import com.davidmendozamartinez.movieinfo.presentation.ui.movies.MoviesFragmentDirections
import com.davidmendozamartinez.movieinfo.presentation.util.getTranslucentColor
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        window.run { WindowCompat.setDecorFitsSystemWindows(this, false) }
        setupDrawer()
    }

    private fun setupDrawer() {
        with(BottomSheetBehavior.from(binding.navigationView)) {
            binding.bottomSheetBehavior = this
            state = BottomSheetBehavior.STATE_HIDDEN

            binding.navigationView.setNavigationItemSelectedListener { menuItem ->
                menuItem.isChecked = true
                state = BottomSheetBehavior.STATE_HIDDEN
                binding.bottomAppBarTitle.text = menuItem.title

                findNavController(R.id.navHostFragment).navigate(
                    MoviesFragmentDirections.actionGlobalMoviesFragment(Section.parse(menuItem.itemId))
                )
                true
            }

            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    binding.scrim.setBackgroundColor(getTranslucentColor(slideOffset))
                }

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    binding.scrim.isVisible = newState != BottomSheetBehavior.STATE_HIDDEN
                }
            })
        }
    }
}