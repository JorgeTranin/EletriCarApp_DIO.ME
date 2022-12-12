package com.example.eletricarapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.eletricarapp.Adapter.CarAdapter
import com.example.eletricarapp.R
import com.example.eletricarapp.data.local.CarRepository
import com.example.eletricarapp.domain.Carro

class FavoriteFragment : Fragment() {
    lateinit var listaCarrosFavoritos: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupList()
    }

    private fun GetCarsOnLocalDB(): List<Carro> {
        val repository = CarRepository(requireContext())
        val carList = repository.getAllCars()
        return carList
    }

    fun setupList(){
        val cars = GetCarsOnLocalDB()
        val adapter = CarAdapter(cars, isFavoriteScreen = true)
        listaCarrosFavoritos.adapter = adapter

        //Quando o usuario clicar na estrela irá salvar no db o carro especifico
        adapter.carItemListener = { carro ->

            //val isSaved = CarRepository(requireContext()).getAllCars(carro)
        }



    }
    // Função para pegar todas as views do meu layout xml
    fun setupView(view: View){
        listaCarrosFavoritos = view.findViewById(R.id.rv_listaDeCarros_Favoritos)


    }

}