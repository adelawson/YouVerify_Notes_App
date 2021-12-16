package com.adelawson.youverifynotes.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adelawson.youverifynotes.R
import com.adelawson.youverifynotes.data.localSource.Task
import com.adelawson.youverifynotes.data.localSource.TaskViewModel
import com.adelawson.youverifynotes.databinding.HomescreenFragmentBinding
import com.adelawson.youverifynotes.ui.SwipeGestureController
import com.adelawson.youverifynotes.ui.adapters.TaskRecyclerAdapter
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
    private lateinit var taskList: List<Task>


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
        val sdf = SimpleDateFormat("EEEE d, LLLL", Locale.getDefault())
        val date = sdf.format(cal.time).toString()
        val dateTxt = "Today,$date"
        dateTxv.text = dateTxt

        //task recycler setup
        val taskRecycler = binding.tasksRcv
        val taskAdapter = TaskRecyclerAdapter(requireContext(), viewModel)
        taskRecycler.adapter = taskAdapter
        taskRecycler.layoutManager = LinearLayoutManager(requireContext())
        viewModel.readTasks.observe(viewLifecycleOwner, Observer{task->
            taskList = task.filter {  it.taskDate == date }
            taskAdapter.setData(taskList)
        })


        val swipeGestureController = object : SwipeGestureController(requireContext()){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when(direction){
                    ItemTouchHelper.RIGHT ->{
                        val task = taskList[viewHolder.adapterPosition]
                        viewModel.deleteTask(task)}
                    ItemTouchHelper.LEFT->{
                        val task = taskList[viewHolder.adapterPosition]
                        viewModel.deleteTask(task)
                    }
                }
            }
        }
        val touchHelper = ItemTouchHelper(swipeGestureController)
        touchHelper.attachToRecyclerView(taskRecycler)



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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search,menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

}