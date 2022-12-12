package com.example.eletricarapp.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eletricarapp.R
import com.example.eletricarapp.databinding.ActivityCalculoAutonomiaBinding

class CalculoAutonomia : AppCompatActivity() {
    lateinit var binding: ActivityCalculoAutonomiaBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityCalculoAutonomiaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnClose.setOnClickListener {
            finish()
        }
        val resultado: TextView = binding.tvResultatoCalculo
        setupCacheResult(resultado)

        binding.btnCalculoAutonomia.setOnClickListener {

            val preco = binding.etPrecoAutonomia.text.toString()
            val kmPercorrido = binding.etKmPercorrido.text.toString()
            if (preco.isEmpty() || kmPercorrido.isEmpty()) {
                Toast.makeText(this, getString(R.string.digite_valores_validos), Toast.LENGTH_LONG)
                    .show()
            } else {
                val calculo = preco.toDouble() / kmPercorrido.toDouble()
                saveSharedPreferences(calculo)

                resultado.visibility = View.VISIBLE
                resultado.text = "R$ ${calculo} "
                limparCampor()


            }
        }

    }
    //Metodo para apresentar o ultimo calculo salvo no cache
    private fun setupCacheResult(resultado: TextView) {
        val shared = getSharedPreferences()
        resultado.visibility = View.VISIBLE
        resultado.text = "R$ ${shared} "
    }

    fun limparCampor() {
        val preco = binding.etPrecoAutonomia.text
        val kmPercorrido = binding.etKmPercorrido.text
        preco.clear()
        kmPercorrido.clear()

    }

    //Metodo para salvar dados com sharedPreferences dos valores calculados, salvando em cache
    fun saveSharedPreferences(resultado : Double){
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPreferences.edit()){
            putFloat(getString(R.string.saved_calc), resultado.toFloat())
            apply()
        }

    }
  //Metodo para buscar sharedpreferences
    fun getSharedPreferences(): Double{
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val calc = sharedPreferences.getFloat(getString(R.string.saved_calc), 0.0f)
      return  calc.toDouble()

    }
}
