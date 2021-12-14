package com.adelawson.youverifynotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.adelawson.youverifynotes.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //nav setup
        val navHostFrag =  supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFrag.navController

        //setup bottom nav and disable middle icon
        val bottomnav = binding.bottomNavigationView
        bottomnav.background = null
        bottomnav.menu.getItem(2).isEnabled = false
        bottomnav.setupWithNavController(navController)
    }
}