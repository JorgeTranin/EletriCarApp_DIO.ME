package com.example.eletricarapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.eletricarapp.Adapter.CarAdapter
import com.example.eletricarapp.R
import com.example.eletricarapp.data.CarFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton


// fragmento para retornar o layout que for preciso
class CarFragment : Fragment() {
    lateinit var listaCarros: RecyclerView
    lateinit var fabCalcular: FloatingActionButton
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.car_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupList()
        setupListeners()
    }

    // Função para pegar todas as views do meu layout xml
    fun setupView(view: View){
        listaCarros = view.findViewById(R.id.rv_listaDeCarros)
        fabCalcular = view.findViewById(R.id.fab_calcular)

    }
    fun setupList(){

        val adapter = CarAdapter(CarFactory.list)
        listaCarros.adapter = adapter

    }
    //metodo para clicks
    fun setupListeners(){
        //Click no botão calcular e navegação para a tela de calculo
        fabCalcular.setOnClickListener {

            val intent = Intent(context, CalculoAutonomia::class.java)
            startActivity(intent)
        }

    }

}