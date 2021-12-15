package com.adelawson.youverifynotes

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.adelawson.youverifynotes.data.localSource.Task
import com.adelawson.youverifynotes.data.localSource.TaskViewModel
import com.adelawson.youverifynotes.databinding.FragmentNewTodoBinding
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

class NewTodoFragment:Fragment(), DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {
    private lateinit var binding: FragmentNewTodoBinding
    private lateinit var date_txv :TextView
    private lateinit var taskPriority:String
    private lateinit var taskCategory: String
    private val viewModel by viewModels<TaskViewModel>()
    private lateinit var taskDate:String
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewTodoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //nav setup
        navHostFragment =  activity?.supportFragmentManager?.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        hideNav()
        val taskNameEditText = binding.tasknameEdtx
        val taskDescriptionEditText = binding.taskdescriptionEdtx
        val taskReminderSwitch = binding.remindMeSwitch
        val remindMeContainer = binding.remindMe
        date_txv = binding.dateTxv


        val priorityToggle = binding.priorityToggle
        priorityToggle.setOnSelectListener {
            val selectedToggle = priorityToggle.selectedButtons[0]
            when(selectedToggle.id){
                binding.lowPriority.id-> taskPriority = "low"
                binding.midPriority.id-> taskPriority= "mid"
                binding.highPriority.id-> taskPriority= "high"
            }

        }

        val categoryToggle = binding.categoryToggle
        categoryToggle.setOnSelectListener {
            val selectedToggle = categoryToggle.selectedButtons[0]
            when(selectedToggle.id){
                binding.workToggle.id-> taskCategory = "work"
                binding.schoolToggle.id-> taskCategory= "school"
                binding.familyToggle.id-> taskCategory= "family"
            }
        }


        val datePicker = binding.datePicker
        datePicker.setOnClickListener {
            selectDate()
        }

        val alarmPicker = binding.alarmPicker
        alarmPicker.setOnClickListener {
            selectAlarm()
        }

        remindMeContainer.setOnClickListener {
            taskReminderSwitch.toggle()
        }

        val addTaskFab = binding.addtaskFab
        addTaskFab.setOnClickListener {
            val taskDescription = taskDescriptionEditText.text.toString()
            val taskName = taskNameEditText.text.toString()
            if (!(TextUtils.isEmpty(taskDescription) && TextUtils.isEmpty(taskName))){
                Toast.makeText(requireContext(), taskDate, Toast.LENGTH_SHORT).show()
                createTask(
                    taskPriority = taskPriority, taskReminder = taskReminderSwitch.isChecked,
                    taskName = taskName, taskDescription = taskDescription,
                    taskCategory = taskCategory, taskDate = taskDate)
                navigateToHome()

            }else if (TextUtils.isEmpty(taskName)){
                Toast.makeText(requireContext(), "Please enter a task name", Toast.LENGTH_SHORT).show()
            }else if (TextUtils.isEmpty(taskDescription)){
                Toast.makeText(requireContext(), "Please enter a Description", Toast.LENGTH_SHORT).show()
            }

        }

    }

    fun selectDate(){
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        val datePickerDialog = DatePickerDialog(requireContext(), this, year, month,day)
        datePickerDialog.show()

    }

    fun selectAlarm(){
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)
        val timeDialog= TimePickerDialog(requireContext(), this, hour, minute, false)
        timeDialog.show()

    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val newCal = Calendar.getInstance()
        newCal.set(Calendar.YEAR,year)
        newCal.set(Calendar.MONTH,month)
        newCal.set(Calendar.DAY_OF_MONTH,dayOfMonth)
        val sdf = SimpleDateFormat(" EEEE d, LLLL, yyyy ")
        val date = sdf.format(newCal.time).toString()
        date_txv.text = date

        val sdfStore = SimpleDateFormat("d/LLLL/yyyy")
        taskDate = sdfStore.format(newCal.time).toString()
    }

    override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {
        val newCal = Calendar.getInstance()
        newCal.set(Calendar.HOUR, hour)
        newCal.set(Calendar.MINUTE, minute)
        val sdf = SimpleDateFormat("KK:mm aaa")
        val time = sdf.format(newCal.time).toString()
        binding.timeTxv.text = time
    }

    private fun hideNav(){
        val bottomNav = activity?.findViewById<BottomAppBar>(R.id.bottomAppBar)
        bottomNav?.visibility = View.GONE
        val bottomFab = activity?.findViewById<FloatingActionButton>(R.id.new_note_fab)
        bottomFab?.visibility = View.GONE

    }

    private fun createTask( taskName:String,
                       taskDescription:String,
                       taskCategory:String,
                       taskPriority:String,
                       taskReminder:Boolean,
                       taskDate: String){
        val newTask = Task (taskName = taskName, taskDescription = taskDescription,
            taskCategory = taskCategory, taskDate = taskDate, taskReminder = taskReminder,
            taskPriority = taskPriority, taskID = 0)

        viewModel.addTask(newTask)
    }
    private fun navigateToHome(){
        val navAction = NewTodoFragmentDirections.actionNewTodoFragmentToHomeScreenFragment()
        navController.navigate(navAction)
    }
}