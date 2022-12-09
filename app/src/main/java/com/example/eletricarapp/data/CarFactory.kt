package com.example.eletricarapp.data

import com.example.eletricarapp.domain.Carro

//data ficara responsavel por fornecer os dados
class CarFactory {
    val list = listOf(

        Carro(id = 1,
            preco = "R$ 300.000.00",
            potencia = "300cv",
            bateria = "300Kw/h",
            recarga = "23 min",
            urlFoto = "www.google.com"

        )

    )
}