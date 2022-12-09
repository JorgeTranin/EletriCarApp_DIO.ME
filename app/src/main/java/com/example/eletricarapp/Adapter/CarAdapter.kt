package com.example.eletricarapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eletricarapp.R
import com.example.eletricarapp.domain.Carro

//adaptador para preencher minha lista de carros
class CarAdapter(private val carros: Array<String>): RecyclerView.Adapter<CarAdapter.ViewHolder>() {

// metodo responsavel por dar um inflate na View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.carro_item, parent, false)
        return ViewHolder(view)
    }

    // Metodo que pega conteudo de uma view e troca por um item da lista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.preco.text = carros[position]
        
    }

    //metodo responsavel por pegar o tamanho da lista
    override fun getItemCount(): Int = carros.size

    // ficara responsavel por preencher cada item e colocar na tela
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val preco: TextView
        init {
            preco = view.findViewById(R.id.tv_preco_valor)
        }
    }

}