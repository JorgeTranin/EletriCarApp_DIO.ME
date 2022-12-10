package com.example.eletricarapp.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.eletricarapp.Adapter.CarAdapter
import com.example.eletricarapp.R
import com.example.eletricarapp.data.CarFactory


// fragmento para retornar o layout que for preciso
class CarFragment : Fragment() {
    lateinit var listaCarros: RecyclerView
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
    }
    fun setupView(view: View){
        listaCarros = view.findViewById(R.id.rv_listaDeCarros)

    }
    fun setupList(){

        val adapter = CarAdapter(CarFactory.list)
        listaCarros.adapter = adapter

    }

}