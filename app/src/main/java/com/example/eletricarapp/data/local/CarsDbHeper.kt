package com.example.eletricarapp.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.eletricarapp.data.local.CarrosContract.SQL_DELETE_ENTRIES
import com.example.eletricarapp.data.local.CarrosContract.TABLE_CAR


// Classe que passa o context de onde esta chamando e extende de SqLite que precisa de um contesto de da classe criada CarrosContract
class CarsDbHeper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase) {
     db.execSQL(TABLE_CAR)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object{
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "DbCar.db"
    }
}