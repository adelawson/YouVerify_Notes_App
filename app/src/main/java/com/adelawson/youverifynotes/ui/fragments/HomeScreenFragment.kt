package com.adelawson.youverifynotes.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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

class HomeScreenFragment : Fragment(), SearchView.OnQueryTextListener {
    private lateinit var binding:HomescreenFragmentBinding
    private val viewModel by viewModels<TaskViewModel>()
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private val cal = Calendar.getInstance()
    private lateinit var taskList: List<Task>
    private lateinit var taskRecycler :RecyclerView
    private lateinit var taskAdapter:TaskRecyclerAdapter
    private lateinit var date:String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = HomescreenFragmentBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNav()
        val toolBar = binding.toolbarMain
        toolBar.inflateMenu(R.menu.search)
        toolBar.setOnMenuItemClickListener {
            when (it.itemId){
                R.id.search->{ setupSearch(it)
                    true
                }
                else -> {
                    super.onOptionsItemSelected(it)
                }
            }
        }

        val dateTxv = binding.homescreendateTxv
        val sdf = SimpleDateFormat("EEEE d, LLLL", Locale.getDefault())
        date = sdf.format(cal.time).toString()
        val dateTxt = "Today,$date"
        dateTxv.text = dateTxt

        //task recycler setup
        taskRecycler = binding.tasksRcv
        taskAdapter = TaskRecyclerAdapter(requireContext(), viewModel)
        taskRecycler.adapter = taskAdapter
        taskRecycler.layoutManager = LinearLayoutManager(requireContext())
        viewModel.readTasks.observe(viewLifecycleOwner, Observer{task->
            taskList = task.filter {  it.taskDate == date }

            taskAdapter.setData(taskList)
        })

//        binding.noTaskTxv.visibility = (if (taskAdapter.itemCount ==0) View.VISIBLE else View.GONE)


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

    fun setupSearch(item: MenuItem){
        val searchVw = item.actionView as SearchView
        searchVw.isSubmitButtonEnabled = true
        searchVw.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query!=null){
            searchDB(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query!=null){
            searchDB(query)
        }
        return true
    }

    private fun searchDB(taskName:String){
        val taskName = "%$taskName%"

        viewModel.searchTask(taskName).observe(this,{
            it.let {
                val searchList = it.filter { tsk -> tsk.taskDate ==date }
                taskAdapter.setData(searchList)
            }
        })
    }

}