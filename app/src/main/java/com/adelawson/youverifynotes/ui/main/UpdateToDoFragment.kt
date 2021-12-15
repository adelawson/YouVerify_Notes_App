package com.adelawson.youverifynotes.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.adelawson.youverifynotes.data.localSource.TaskViewModel
import com.adelawson.youverifynotes.databinding.FragmentNewTodoBinding

class UpdateToDoFragment:Fragment() {
    private val viewModel by viewModels<TaskViewModel>()
    private val args by navArgs<UpdateToDoFragmentArgs>()
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var binding: FragmentNewTodoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewTodoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val task = args.editTask
        val headerText = "Update Task"
        binding.createtaskTxv.text = headerText
        val timeTxv = binding.timeTxv
        val taskNameEdtx = binding.tasknameEdtx
        val taskDescEdtx = binding.taskdescriptionEdtx
        val dateTxv = binding.dateTxv

        taskNameEdtx.setText(task.taskName)
        taskDescEdtx.setText(task.taskDescription)
        dateTxv.text = task.taskDate

    }
}