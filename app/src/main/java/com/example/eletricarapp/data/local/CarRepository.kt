package com.example.eletricarapp.data.local

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import com.example.eletricarapp.data.local.CarrosContract.CarEntry.COLUMN_NAME_BATERIA
import com.example.eletricarapp.data.local.CarrosContract.CarEntry.COLUMN_NAME_CAR_ID
import com.example.eletricarapp.data.local.CarrosContract.CarEntry.COLUMN_NAME_POTENCIA
import com.example.eletricarapp.data.local.CarrosContract.CarEntry.COLUMN_NAME_PRECO
import com.example.eletricarapp.data.local.CarrosContract.CarEntry.COLUMN_NAME_RECARGA
import com.example.eletricarapp.data.local.CarrosContract.CarEntry.COLUMN_NAME_URLPHOTO
import com.example.eletricarapp.domain.Carro

class CarRepository(private val context: Context) {

    //Metodo para salvar no DB o carro
    fun save( carro: Carro) : Boolean{
        var isSaved = false
        try {
            findCarViewById(1)
            val dbHeper = CarsDbHeper(context)
            val db = dbHeper.writableDatabase
            val values = ContentValues().apply {
                put(COLUMN_NAME_CAR_ID, carro.id)
                put(COLUMN_NAME_PRECO, carro.preco)
                put(COLUMN_NAME_BATERIA, carro.bateria)
                put(COLUMN_NAME_POTENCIA, carro.potencia)
                put(COLUMN_NAME_RECARGA, carro.recarga)
                put(COLUMN_NAME_URLPHOTO, carro.urlPhoto)
            }
            val inserted = db?.insert(CarrosContract.CarEntry.TABLE_NAME, null, values)
            if (inserted != null){
                isSaved = true
            }
        }catch (ex: Exception){
          ex.message?.let {  Log.e("erro", it) }

        }
        return isSaved
    }

    // metodo que pega o carro pelo ID
    fun findCarViewById(id: Int) : Carro{
        val dbHeper = CarsDbHeper(context)
        val db = dbHeper.readableDatabase
        // Listagem das colunas a ser exibida no resultado da query
        val columns = arrayOf(BaseColumns._ID,
            COLUMN_NAME_CAR_ID,
            COLUMN_NAME_PRECO,
            COLUMN_NAME_BATERIA
            , COLUMN_NAME_POTENCIA,
            COLUMN_NAME_RECARGA,
            COLUMN_NAME_URLPHOTO)

        val filter = "$COLUMN_NAME_CAR_ID = ?"
        val filterValues = arrayOf(id.toString())
        val cursor = db.query(
            CarrosContract.CarEntry.TABLE_NAME, //Nome da tabela
            columns, //Nome das Colunas a serem exibidas
            filter, // meu filtro
            filterValues,// valor do where, substituindo o parametro ?
            null,
            null,null
        )
        var itemId: Long = 0
        var preco = ""
        var bateria = ""
        var potencia = ""
        var recarga = ""
        var urlfoto = ""

        with(cursor){
            while (moveToNext()){
                itemId = getLong(getColumnIndexOrThrow(COLUMN_NAME_CAR_ID))
                preco = getString(getColumnIndexOrThrow(COLUMN_NAME_PRECO))
                bateria = getString(getColumnIndexOrThrow(COLUMN_NAME_BATERIA))
                potencia = getString(getColumnIndexOrThrow(COLUMN_NAME_POTENCIA))
                recarga = getString(getColumnIndexOrThrow(COLUMN_NAME_RECARGA))
                urlfoto = getString(getColumnIndexOrThrow(COLUMN_NAME_URLPHOTO))

            }
        }

        cursor.close()
        return Carro(
            id = itemId.toInt(),
            preco = preco,
            bateria = bateria,
            potencia = potencia,
            recarga = recarga,
            urlPhoto = urlfoto,
            isFavorite = true
        )
    }


    // Metodo para pegar todos os carro no DB para mostrar na tela favoritos

    fun getAllCars(): List<Carro>{
        val dbHeper = CarsDbHeper(context)
        val db = dbHeper.readableDatabase
        // Listagem das colunas a ser exibida no resultado da query
        val columns = arrayOf(BaseColumns._ID,
            COLUMN_NAME_CAR_ID,
            COLUMN_NAME_PRECO,
            COLUMN_NAME_BATERIA
            , COLUMN_NAME_POTENCIA,
            COLUMN_NAME_RECARGA,
            COLUMN_NAME_URLPHOTO)


        val cursor = db.query(
            CarrosContract.CarEntry.TABLE_NAME, //Nome da tabela
            columns, //Nome das Colunas a serem exibidas
            null, // meu filtro
            null,// valor do where, substituindo o parametro ?
            null,
            null,null
        )
        val listaDeCarros = mutableListOf<Carro>()

        with(cursor){
            while (moveToNext()){
                val itemId = getLong(getColumnIndexOrThrow(COLUMN_NAME_CAR_ID))
                val preco = getString(getColumnIndexOrThrow(COLUMN_NAME_PRECO))
                val bateria = getString(getColumnIndexOrThrow(COLUMN_NAME_BATERIA))
                val potencia = getString(getColumnIndexOrThrow(COLUMN_NAME_POTENCIA))
                val recarga = getString(getColumnIndexOrThrow(COLUMN_NAME_RECARGA))
                val urlfoto = getString(getColumnIndexOrThrow(COLUMN_NAME_URLPHOTO))

                // Adiciona todos os itens encontrados na lista
                listaDeCarros.add(
                    Carro(
                        id = itemId.toInt(),
                        preco = preco,
                        bateria = bateria,
                        potencia = potencia,
                        recarga = recarga,
                        urlPhoto = urlfoto,
                        isFavorite = true
                    )
                )
            }
        }

        cursor.close()
        return listaDeCarros
    }

    // Metodo que irá verificar se já existe no Db as informações, para não gerar duplicatas, se não existir ele salva
    fun saveIfNotExist(carro: Carro){
        val car = findCarViewById(carro.id)
        if (car.id == ID_NO_CAR){
            // chamada do metodo para salvar no Db
            save(carro)
        }else{

        }
    }

    // metodo que deleta um carro
    /*fun DeleteCar(id: Int) : Carro{
        val dbHeper = CarsDbHeper(context)
        val db = dbHeper.readableDatabase
        // Listagem das colunas a ser exibida no resultado da query
        val columns = arrayOf(BaseColumns._ID,
            COLUMN_NAME_CAR_ID,
            COLUMN_NAME_PRECO,
            COLUMN_NAME_BATERIA
            , COLUMN_NAME_POTENCIA,
            COLUMN_NAME_RECARGA,
            COLUMN_NAME_URLPHOTO)

        val filter = "$COLUMN_NAME_CAR_ID = ?"
        val filterValues = arrayOf(id.toString())
        val cursor = db.query(
            CarrosContract.CarEntry.TABLE_NAME, //Nome da tabela
            columns, //Nome das Colunas a serem exibidas
            filter, // meu filtro
            filterValues,// valor do where, substituindo o parametro ?
            null,
            null,null
        )
        var itemId: Long = 0
        var preco = ""
        var bateria = ""
        var potencia = ""
        var recarga = ""
        var urlfoto = ""

        with(cursor){
            while (moveToNext()){
                itemId = getLong(getColumnIndexOrThrow(COLUMN_NAME_CAR_ID))
                preco = getString(getColumnIndexOrThrow(COLUMN_NAME_PRECO))
                bateria = getString(getColumnIndexOrThrow(COLUMN_NAME_BATERIA))
                potencia = getString(getColumnIndexOrThrow(COLUMN_NAME_POTENCIA))
                recarga = getString(getColumnIndexOrThrow(COLUMN_NAME_RECARGA))
                urlfoto = getString(getColumnIndexOrThrow(COLUMN_NAME_URLPHOTO))

            }
        }

        cursor.close()
        return Carro(
            id = itemId.toInt(),
            preco = preco,
            bateria = bateria,
            potencia = potencia,
            recarga = recarga,
            urlPhoto = urlfoto,
            isFavorite = true
        )
    }

     */

    companion object{
        const val ID_NO_CAR = 0
    }
}