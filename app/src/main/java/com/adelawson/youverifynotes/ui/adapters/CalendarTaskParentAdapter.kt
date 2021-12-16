package com.adelawson.youverifynotes.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adelawson.youverifynotes.data.localSource.Task
import com.adelawson.youverifynotes.databinding.CalenarDateHeaderBinding
import androidx.recyclerview.widget.LinearLayoutManager


class CalendarTaskParentAdapter () : RecyclerView.Adapter<CalenderTaskParentVH>() {
    var dateList = emptyList<String>()
    var taskList = emptyList<Task>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalenderTaskParentVH {
        val binding = CalenarDateHeaderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CalenderTaskParentVH(binding)
    }

    override fun onBindViewHolder(holder: CalenderTaskParentVH, position: Int) {
        val currentDate = dateList[position]
        val dateTxv = holder.binding.dateTxv
        dateTxv.text = currentDate

        val filteredList = taskList.filter {
            it.taskDate == currentDate }
        val layoutManager = LinearLayoutManager(
            holder.binding.calTaskRcv.getContext(), LinearLayoutManager.VERTICAL, false)
        val calTaskRcv = holder.binding.calTaskRcv
        val adapter = CalendarTaskRecyclerAdapter(filteredList)
        calTaskRcv.adapter = adapter
        calTaskRcv.layoutManager = layoutManager
    }

    override fun getItemCount(): Int {
        return dateList.size
    }

    fun setData(date:List<String>, tasks:List<Task>){
        this.dateList = date
        this.taskList = tasks
        notifyDataSetChanged()
    }
}