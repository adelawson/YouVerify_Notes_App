package com.adelawson.youverifynotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.adelawson.youverifynotes.databinding.FragmentNewTodoBinding

class NewTodoFragment:Fragment() {
    private lateinit var binding: FragmentNewTodoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewTodoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val datePicker = binding.datePicker
        datePicker.setOnClickListener {

        }

        val alarmPicker = binding.alarmPicker
        alarmPicker.setOnClickListener {

        }

    }

    fun
}