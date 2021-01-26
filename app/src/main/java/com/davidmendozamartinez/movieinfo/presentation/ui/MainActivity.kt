package com.davidmendozamartinez.movieinfo.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.davidmendozamartinez.movieinfo.R
import com.davidmendozamartinez.movieinfo.databinding.ActivityMainBinding
import com.davidmendozamartinez.movieinfo.presentation.model.Section
import com.davidmendozamartinez.movieinfo.presentation.ui.movies.MoviesFragmentDirections
import com.davidmendozamartinez.movieinfo.presentation.ui.search.SearchFragmentDirections
import com.davidmendozamartinez.movieinfo.presentation.util.hide
import com.davidmendozamartinez.movieinfo.presentation.util.setScrimAnimation
import com.davidmendozamartinez.movieinfo.presentation.util.show
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationView
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialFadeThrough

class MainActivity : AppCompatActivity(),
    NavController.OnDestinationChangedListener,
    NavigationView.OnNavigationItemSelectedListener,
    Toolbar.OnMenuItemClickListener {

    companion object {
        private const val KEY_SECTION_TITLE = "section_title_key"
        private const val KEY_LOCAL_MODE = "local_mode_key"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<NavigationView>

    private val sharedPreferences by lazy { getPreferences(Context.MODE_PRIVATE) }

    private val currentNavigationFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.navHostFragment)
            ?.childFragmentManager
            ?.fragments
            ?.first()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        savedInstanceState?.let {
            binding.bottomAppBarTitle.text = savedInstanceState.getString(
                KEY_SECTION_TITLE,
                getString(Section.POPULAR.stringResId)
            )
        }
        setLocalNightMode(getLocalNightMode())

        setupNavController()
        setupFab()
        setupBottomAppBar()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_SECTION_TITLE, binding.bottomAppBarTitle.text.toString())
    }

    private fun setupNavController() {
        (supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment).let {
            navController = it.navController
            navController.addOnDestinationChangedListener(this)
        }
    }

    private fun setupFab() {
        binding.fab.setOnClickListener {
            navigateToSearch()
        }
    }

    private fun setupBottomAppBar() {
        binding.bottomAppBar.setOnMenuItemClickListener(this)
        binding.navigationView.setNavigationItemSelectedListener(this)

        with(BottomSheetBehavior.from(binding.navigationView)) {
            bottomSheetBehavior = this
            binding.bottomSheetBehavior = this
            hide()
            setScrimAnimation(binding.scrim)
        }
    }

    override fun onDestinationChanged(
        controller: NavController, destination: NavDestination, arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.moviesFragment -> {
                binding.bottomAppBar.show()
                binding.fab.show()
            }
            R.id.detailsFragment, R.id.searchFragment -> {
                binding.bottomAppBar.hide()
                binding.fab.hide()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        bottomSheetBehavior.hide()

        binding.bottomAppBarTitle.let { currentTitle ->
            if (currentTitle.text != item.title) {
                currentTitle.text = item.title
                navigateToHome(Section.parse(item.itemId))
            }
        }
        return true
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        val mode = when (item?.itemId) {
            R.id.light -> AppCompatDelegate.MODE_NIGHT_NO
            R.id.dark -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        saveLocalNightMode(mode)
        setLocalNightMode(mode)
        return true
    }

    private fun setLocalNightMode(mode: Int) {
        delegate.localNightMode = mode
    }

    private fun getLocalNightMode(): Int =
        sharedPreferences.getInt(KEY_LOCAL_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

    private fun saveLocalNightMode(mode: Int) = sharedPreferences.edit {
        putInt(KEY_LOCAL_MODE, mode)
    }

    private fun navigateToHome(section: Section) {
        currentNavigationFragment?.apply {
            exitTransition = MaterialFadeThrough().apply {
                duration = resources.getInteger(R.integer.motion_duration_large).toLong()
            }
        }

        navController.navigate(MoviesFragmentDirections.actionGlobalMoviesFragment(section))
    }

    private fun navigateToSearch() {
        currentNavigationFragment?.apply {
            exitTransition = Hold().apply {
                duration = resources.getInteger(R.integer.motion_duration_large).toLong()
            }
            reenterTransition = Hold().apply {
                duration = resources.getInteger(R.integer.motion_duration_large).toLong()
            }
        }

        navController.navigate(SearchFragmentDirections.actionGlobalSearchFragment())
    }
}