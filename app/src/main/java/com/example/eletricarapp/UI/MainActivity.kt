package com.example.eletricarapp.UI

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.eletricarapp.Adapter.CarAdapter
import com.example.eletricarapp.Adapter.TabAdapter
import com.example.eletricarapp.data.CarFactory
import com.example.eletricarapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var listaCarros: RecyclerView
    lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupList()
        setupTabs()

        /*binding.ivCarro11.setOnClickListener {
            val intent = Intent(this, CalculoAutonomia::class.java)
            startActivity(intent)
        }

         */
    }

    fun setupList(){
        listaCarros = binding.rvListaDeCarros
        var dados = CarFactory.list

        val adapter = CarAdapter(dados)
        listaCarros.adapter = adapter
    }
    fun setupTabs(){
        val tab = binding.tabLayout
        val tabAdapter = TabAdapter(this)
        viewPager.adapter = tabAdapter

    }
}