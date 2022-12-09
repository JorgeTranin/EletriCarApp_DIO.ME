package com.example.eletricarapp.UI

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.eletricarapp.Adapter.CarAdapter
import com.example.eletricarapp.data.CarFactory
import com.example.eletricarapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var listaCarros: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupList()

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
}