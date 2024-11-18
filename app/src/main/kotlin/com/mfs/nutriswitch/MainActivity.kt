package com.mfs.nutriswitch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.NavigationUiSaveStateControl
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.mfs.nutriswitch.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val navHostFragment by lazy { binding.fragmentContainerView.getFragment() as NavHostFragment }
    private val navController by lazy { navHostFragment.navController }
    private val appBarConfiguration by lazy {
        val topLevelDestinations = setOf(
            com.mfs.feature.foodlist.R.id.food_list_nav_graph,
            com.mfs.feature.foodview.R.id.food_view_nav_graph,
            com.mfs.feature.foodconverter.R.id.food_nutrinets_converter_nav_graph
        )

        return@lazy AppBarConfiguration(topLevelDestinations)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupNavigation()
    }

    @OptIn(NavigationUiSaveStateControl::class)
    private fun setupNavigation() {
        setupWithNavController(binding.bottomNavigation, navController, false)
        handleBottomNavigationVisibility()

        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun handleBottomNavigationVisibility() {
        ViewCompat.setWindowInsetsAnimationCallback(
            binding.root,
            object : WindowInsetsAnimationCompat.Callback(DISPATCH_MODE_STOP) {

                override fun onProgress(
                    insets: WindowInsetsCompat,
                    runningAnimations: MutableList<WindowInsetsAnimationCompat>
                ): WindowInsetsCompat {
                    runningAnimations.find {
                        it.typeMask and WindowInsetsCompat.Type.ime() != 0
                    } ?: return insets

                    val imeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())

                    binding.bottomNavigation.isGone = imeVisible

                    return insets
                }
            }
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
