package com.mfs.nutriswitch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
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


    private fun setupNavigation() {
        binding.bottomNavigation.setupWithNavController(navController)

        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
