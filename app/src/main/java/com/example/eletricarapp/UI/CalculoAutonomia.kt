package com.example.eletricarapp.UI

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

        binding.btnCalculoAutonomia.setOnClickListener {
            val resultado: TextView = binding.tvResultatoCalculo
            val preco = binding.etPrecoAutonomia.text.toString()
            val kmPercorrido = binding.etKmPercorrido.text.toString()
            if (preco.isEmpty() || kmPercorrido.isEmpty()) {
                Toast.makeText(this, getString(R.string.digite_valores_validos), Toast.LENGTH_LONG)
                    .show()
            } else {
                val calculo = preco.toDouble() / kmPercorrido.toDouble()

                resultado.visibility = View.VISIBLE
                resultado.text = "R$ ${calculo} "
                limparCampor()


            }
        }

    }

    fun limparCampor() {
        val preco = binding.etPrecoAutonomia.text
        val kmPercorrido = binding.etKmPercorrido.text
        preco.clear()
        kmPercorrido.clear()
    }
}
