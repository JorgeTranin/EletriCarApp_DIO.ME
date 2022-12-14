package com.example.eletricarapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eletricarapp.R
import com.example.eletricarapp.domain.Carro

//adaptador para preencher minha lista de carros
class CarAdapter(private val carros: List<Carro>, private val isFavoriteScreen: Boolean = false) :
    RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    var carItemListener: (Carro) -> Unit = {}

    // metodo responsavel por dar um inflate na View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.carro_item, parent, false)
        return ViewHolder(view)
    }

    // Metodo que pega conteudo de uma view e troca por um item da lista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.preco.text = carros[position].preco
        holder.bateria.text = carros[position].bateria
        holder.potencia.text = carros[position].potencia
        holder.recarga.text = carros[position].recarga
        if (isFavoriteScreen) {
            holder.favorito.setImageResource(R.drawable.ic_star_selected)
        }
        //set do favorito no carro
        holder.favorito.setOnClickListener {
            // carro na posição clicada
            val carro = carros[position]
            carItemListener(carro)
            setupFavorite(carro, holder)

        }

    }

    private fun setupFavorite(
        carro: Carro,
        holder: ViewHolder
    ) {

        // se clicar muda de estado
        carro.isFavorite = !carro.isFavorite
        if (carro.isFavorite) {
            holder.favorito.setImageResource(R.drawable.ic_star_selected)

        } else {
            holder.favorito.setImageResource(R.drawable.ic_baseline_star_border_24)
        }

    }

    //metodo responsavel por pegar o tamanho da lista
    override fun getItemCount(): Int = carros.size

    // ficara responsavel por preencher cada item e colocar na tela
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val preco: TextView
        val bateria: TextView
        val potencia: TextView
        val recarga: TextView
        val favorito: ImageView


        init {
            preco = view.findViewById(R.id.tv_preco_valor)
            bateria = view.findViewById(R.id.tv_bateria_valor)
            potencia = view.findViewById(R.id.tv_potencia_valor)
            recarga = view.findViewById(R.id.tv_recarga_valor)
            favorito = view.findViewById(R.id.iv_favorite)
        }
    }

}