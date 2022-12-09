package com.example.eletricarapp.data

import com.example.eletricarapp.domain.Carro

//data ficara responsavel por fornecer os dados
object CarFactory {
    val list = listOf(

        Carro(id = 1,
            preco = "R$ 300.000.00",
            potencia = "300cv",
            bateria = "300Kw/h",
            recarga = "23 min",
            urlFoto = "www.google.com"

        ),
        Carro(id = 2,
            preco = "R$ 100.000.00",
            potencia = "100cv",
            bateria = "100Kw/h",
            recarga = "13 min",
            urlFoto = "www.google.com"

        )

    )
}