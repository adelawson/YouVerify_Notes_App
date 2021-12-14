package com.adelawson.youverifynotes.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import com.adelawson.youverifynotes.R
import com.adelawson.youverifynotes.databinding.HomescreenFragmentBinding

class HomeScreenFragment : Fragment() {
    private lateinit var binding:HomescreenFragmentBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomescreenFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //nav setup
        navHostFragment =  activity?.supportFragmentManager?.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

    }

}