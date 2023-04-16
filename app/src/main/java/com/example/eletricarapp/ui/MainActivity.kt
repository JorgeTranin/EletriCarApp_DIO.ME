package com.example.eletricarapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.eletricarapp.Adapter.TabAdapter
import com.example.eletricarapp.R
import com.example.eletricarapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupListeners()

      val navController =  findNavController(R.id.nav_host_fragment)
       setupWithNavController(binding.BtnNavigation, navController)
    }
    fun setupListeners(){
        //Click no botão calcular e navegação para a tela de calculo
       binding.fabCalcular.setOnClickListener {

            val intent = Intent(this, CalculoAutonomia::class.java)
            startActivity(intent)
        }

    }
}