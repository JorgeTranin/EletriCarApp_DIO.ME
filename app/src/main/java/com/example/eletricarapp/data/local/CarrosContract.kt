package com.example.eletricarapp.data.local

import android.provider.BaseColumns

//Classe responsavel por falar o que queremos gravar no banco de dados e o que deletar
object CarrosContract {



    object CarEntry : BaseColumns{
        //NOME DA TAbela em seguida o nome das colunas que iram ser salvas
        const val TABLE_NAME = "Car"
        const val COLUMN_NAME_PRECO = "preco"
        const val COLUMN_NAME_BATERIA = "bateria"
        const val COLUMN_NAME_POTENCIA = "potencia"
        const val COLUMN_NAME_RECARGA = "recarga"
        const val COLUMN_NAME_URLPHOTO = "urlPhoto"
    }

    // Estrutura do banco de dados
    const val TABLE_CAR = "CREATE TABLE ${CarEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${CarEntry.COLUMN_NAME_PRECO} TEXT," +
            "${CarEntry.COLUMN_NAME_BATERIA} TEXT," +
            "${CarEntry.COLUMN_NAME_POTENCIA} TEXT," +
            "${CarEntry.COLUMN_NAME_RECARGA} TEXT," +
            "${CarEntry.COLUMN_NAME_URLPHOTO} TEXT)"

    const val SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS ${CarEntry.TABLE_NAME}"
}