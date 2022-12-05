package com.example.eletricarapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eletricarapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

/*
        binding.ivCarro1.setOnClickListener {
            val intent = Intent(this, CalculoAutonomia::class.java)
            startActivity(intent)

        }*/
    }
}