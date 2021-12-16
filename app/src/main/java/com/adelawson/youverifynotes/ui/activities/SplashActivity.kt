package com.adelawson.youverifynotes.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent

import android.R

import android.os.Bundle
import android.os.Handler
import com.adelawson.youverifynotes.databinding.SplashScreenBinding


class SplashActivity: AppCompatActivity(){
    private lateinit var binding :SplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}