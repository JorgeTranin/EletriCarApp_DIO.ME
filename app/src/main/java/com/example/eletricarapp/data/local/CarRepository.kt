package com.example.eletricarapp.data.local

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import com.example.eletricarapp.data.local.CarrosContract.CarEntry.COLUMN_NAME_BATERIA
import com.example.eletricarapp.data.local.CarrosContract.CarEntry.COLUMN_NAME_POTENCIA
import com.example.eletricarapp.data.local.CarrosContract.CarEntry.COLUMN_NAME_PRECO
import com.example.eletricarapp.data.local.CarrosContract.CarEntry.COLUMN_NAME_RECARGA
import com.example.eletricarapp.data.local.CarrosContract.CarEntry.COLUMN_NAME_URLPHOTO
import com.example.eletricarapp.domain.Carro

class CarRepository(private val context: Context) {
    fun save( carro: Carro) : Boolean{
        var isSaved = false
        try {
            findCarViewById(1)
            val dbHeper = CarsDbHeper(context)
            val db = dbHeper.writableDatabase
            val values = ContentValues().apply {
                put(CarrosContract.CarEntry.COLUMN_NAME_PRECO, carro.preco)
                put(CarrosContract.CarEntry.COLUMN_NAME_BATERIA, carro.bateria)
                put(CarrosContract.CarEntry.COLUMN_NAME_POTENCIA, carro.potencia)
                put(CarrosContract.CarEntry.COLUMN_NAME_RECARGA, carro.recarga)
                put(CarrosContract.CarEntry.COLUMN_NAME_URLPHOTO, carro.urlPhoto)
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

    fun findCarViewById(id: Int){
        val dbHeper = CarsDbHeper(context)
        val db = dbHeper.readableDatabase
        // Listagem das colunas a ser exibida no resultado da query
        val columns = arrayOf(BaseColumns._ID, COLUMN_NAME_PRECO, COLUMN_NAME_BATERIA
            , COLUMN_NAME_POTENCIA, COLUMN_NAME_RECARGA, COLUMN_NAME_URLPHOTO)
        val filter = "${BaseColumns._ID} = ?"
        val filterValues = arrayOf(id.toString())
        val cursor = db.query(
            CarrosContract.CarEntry.TABLE_NAME,
            columns,
            filter,
            filterValues,
            null,
            null,null
        )
        val itemCar = mutableListOf<Carro>()
        with(cursor){
            while (moveToNext()){
                val itemId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
            }
        }
        cursor.close()
    }
}