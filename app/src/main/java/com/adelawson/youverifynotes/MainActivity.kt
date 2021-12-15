package com.adelawson.youverifynotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.adelawson.youverifynotes.databinding.MainActivityBinding
import com.adelawson.youverifynotes.ui.main.HomeScreenFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //nav setup
        val navHostFrag =  supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFrag.navController

        //setup bottom nav and disable middle icon
        val bottomnav = binding.bottomNavigationView
        bottomnav.background = null
        bottomnav.menu.getItem(2).isEnabled = false
        bottomnav.setupWithNavController(navController)

        val newNoteFab= binding.newNoteFab
        newNoteFab.setOnClickListener{
            navController.navigate(R.id.newTodoFragment)
        }

    }

    fun createNewNote(){
        val navAction = navController.navigate(R.id.newTodoFragment)
    }
}