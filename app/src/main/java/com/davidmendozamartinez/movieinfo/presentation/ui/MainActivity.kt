package com.davidmendozamartinez.movieinfo.presentation.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.davidmendozamartinez.movieinfo.R
import com.davidmendozamartinez.movieinfo.databinding.ActivityMainBinding
import com.davidmendozamartinez.movieinfo.presentation.model.Section
import com.davidmendozamartinez.movieinfo.presentation.ui.movies.MoviesFragmentDirections
import com.davidmendozamartinez.movieinfo.presentation.util.getTranslucentColor
import com.davidmendozamartinez.movieinfo.presentation.util.hide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    Toolbar.OnMenuItemClickListener,
    NavController.OnDestinationChangedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<NavigationView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener(this)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setupBottomAppBar()
    }

    private fun setupBottomAppBar() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.navigationView)
        binding.bottomSheetBehavior = bottomSheetBehavior
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        binding.bottomAppBar.setOnMenuItemClickListener(this@MainActivity)
        binding.navigationView.setNavigationItemSelectedListener(this@MainActivity)

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.scrim.setBackgroundColor(getTranslucentColor(slideOffset))
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                binding.scrim.isVisible = newState != BottomSheetBehavior.STATE_HIDDEN
            }
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        item.isChecked = true

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        binding.bottomAppBarTitle.text = item.title
        binding.bottomAppBar.visibility = View.VISIBLE

        navController.navigate(
            MoviesFragmentDirections.actionGlobalMoviesFragment(Section.parse(item.itemId))
        )
        return true
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        delegate.localNightMode = when (item?.itemId) {
            R.id.light -> AppCompatDelegate.MODE_NIGHT_NO
            R.id.dark -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        return true
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.moviesFragment -> {
                binding.bottomAppBar.visibility = View.VISIBLE
                binding.bottomAppBar.performShow()
                binding.fab.show()
            }
            R.id.detailsFragment -> {
                binding.bottomAppBar.hide()
                binding.fab.hide()
            }
        }
    }
}