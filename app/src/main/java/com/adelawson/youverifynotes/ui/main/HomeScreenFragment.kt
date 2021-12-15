package com.adelawson.youverifynotes.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.adelawson.youverifynotes.R
import com.adelawson.youverifynotes.data.localSource.TaskViewModel
import com.adelawson.youverifynotes.databinding.HomescreenFragmentBinding
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

class HomeScreenFragment : Fragment() {
    private lateinit var binding:HomescreenFragmentBinding
    private val viewModel by viewModels<TaskViewModel>()
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private val cal = Calendar.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomescreenFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNav()

        val dateTxv = binding.homescreendateTxv
        val sdf = SimpleDateFormat(" d LLL, E, yyyy ")
        val date = sdf.format(cal.time).toString()
        val dateTxt = "Today,$date"
        dateTxv.text = dateTxt

        //task recycler setup
        val taskRecycler = binding.tasksRcv
        val taskAdapter = TaskRecyclerAdapter(requireContext())
        taskRecycler.adapter = taskAdapter
        taskRecycler.layoutManager = LinearLayoutManager(requireContext())
        val sdfStore = SimpleDateFormat("d/LLLL/yyyy")
        val curDate = sdfStore.format(cal.time).toString()
        viewModel.readTasks.observe(viewLifecycleOwner, Observer{task->
            val taskList = task.filter {  it.taskDate == curDate}
            taskAdapter.setData(taskList)
        })


        //nav setup
        navHostFragment =  activity?.supportFragmentManager?.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

    }

    fun showNav(){
        val bottomNav = activity?.findViewById<BottomAppBar>(R.id.bottomAppBar)
        bottomNav?.visibility = View.VISIBLE
        val bottomFab = activity?.findViewById<FloatingActionButton>(R.id.new_note_fab)
        bottomFab?.visibility = View.VISIBLE

    }



}