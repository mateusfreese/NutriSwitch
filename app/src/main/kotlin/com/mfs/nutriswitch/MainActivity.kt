package com.mfs.nutriswitch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.mfs.nutriswitch.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() = with(binding) {
        val navHostFragment = fragmentContainerView.getFragment() as NavHostFragment
        bottomNavigation.setupWithNavController(navHostFragment.navController)
    }
}
