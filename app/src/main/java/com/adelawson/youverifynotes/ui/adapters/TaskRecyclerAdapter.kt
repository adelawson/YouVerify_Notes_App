package com.adelawson.youverifynotes.ui.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.adelawson.youverifynotes.R
import com.adelawson.youverifynotes.data.localSource.Task
import com.adelawson.youverifynotes.data.localSource.TaskViewModel
import com.adelawson.youverifynotes.databinding.TodoCardBinding
import com.adelawson.youverifynotes.ui.fragments.HomeScreenFragmentDirections

class TaskRecyclerAdapter(val context: Context, viewModel: TaskViewModel) : RecyclerView.Adapter<TaskRecyclerViewHolder>() {
    val viewModel = viewModel
    var taskList = emptyList<Task>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskRecyclerViewHolder {
        val binding = TodoCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TaskRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskRecyclerViewHolder, position: Int) {
        val currentTask = taskList[position]
        val category_txv = holder.binding.categoryTxv
        val priority_txv = holder.binding.priorityTxv
        val taskNameCheckBox = holder.binding.tasknameChk
        val cardView = holder.binding.taskCrdvw
        holder.binding.tasktimeTxv.text = currentTask.taskAlarmTime


        priority_txv.text= currentTask.taskPriority
        when (currentTask.taskPriority){
            "Low"-> priority_txv.background = getDrawable(context, R.drawable.low_priority)
            "Medium"-> priority_txv.background = getDrawable(context, R.drawable.mid_priority)
            "High"-> priority_txv.background = getDrawable(context, R.drawable.high_priority)
        }

        category_txv.text = currentTask.taskCategory
        when (currentTask.taskCategory){
            "Work"-> {category_txv.background = getDrawable(context, R.drawable.work_cat)
                        category_txv.setTextColor(Color.parseColor("#851c02")) }
            "School"-> {category_txv.background = getDrawable(context, R.drawable.school_cat)
                category_txv.setTextColor(Color.parseColor("#970a92")) }
            "Family"-> {category_txv.background = getDrawable(context, R.drawable.family_cat)
                category_txv.setTextColor(Color.parseColor("#357bbf")) }
        }

        taskNameCheckBox.text = currentTask.taskName
        taskNameCheckBox.isChecked = currentTask.isTaskDone
        taskNameCheckBox.setOnClickListener {
            viewModel.deleteTask(currentTask)
        }

        cardView.setOnClickListener {
            val navAction = HomeScreenFragmentDirections.
            actionHomeScreenFragmentToUpdateToDoFragment(currentTask)
            holder.itemView.findNavController().navigate(navAction)
        }



    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun setData(task:List<Task>){
        this.taskList = task
        notifyDataSetChanged()
    }
}