package com.adelawson.youverifynotes.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.adelawson.youverifynotes.data.localSource.Task
import com.adelawson.youverifynotes.databinding.CalendarCardItemBinding
import com.adelawson.youverifynotes.ui.fragments.CalendarFragmentDirections

class CalendarTaskRecyclerAdapter(private val list: List<Task>) : RecyclerView.Adapter<CalendarTaskViewHolder>() {
    var taskList = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarTaskViewHolder {
        val binding = CalendarCardItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CalendarTaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarTaskViewHolder, position: Int) {
        val currentTask = taskList[position]
        val cardView = holder.binding.taskCrdvw
        val taskTxv = holder.binding.calTaskName

        taskTxv.text = currentTask.taskName
        holder.binding.taskDurationTxv.text = currentTask.taskDuration
        holder.binding.calTaskTimeTxv.text = currentTask.taskAlarmTime


        cardView.setOnClickListener {
            val navAction = CalendarFragmentDirections
                .actionCalendarFragmentToUpdateToDoFragment(currentTask)
            holder.itemView.findNavController().navigate(navAction)
        }

    }

    override fun getItemCount(): Int {
        return taskList.size
    }

}