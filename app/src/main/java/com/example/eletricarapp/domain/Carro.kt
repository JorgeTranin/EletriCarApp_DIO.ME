package com.example.eletricarapp.domain

// dominio carro
data class Carro(
    val id: Int,
    val preco: String,
    val bateria: String,
    val potencia: String,
    val recarga: String,
    //PARA PODER COLOCAR AS FOTOS DINAMICAMENTE NA LISTA
    val urlPhoto: String,
    var isFavorite: Boolean
)
