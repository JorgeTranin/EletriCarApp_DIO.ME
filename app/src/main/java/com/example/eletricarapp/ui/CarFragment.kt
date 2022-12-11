package com.example.eletricarapp.ui

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.eletricarapp.Adapter.CarAdapter
import com.example.eletricarapp.R
import com.example.eletricarapp.data.CarFactory
import com.example.eletricarapp.domain.Carro
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONTokener
import java.net.HttpURLConnection
import java.net.URL


// fragmento para retornar o layout que for preciso
class CarFragment : Fragment() {
    lateinit var listaCarros: RecyclerView
    lateinit var fabCalcular: FloatingActionButton

    var carrosArray: ArrayList<Carro> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.car_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callService()
        setupView(view)
        setupListeners()
    }

    // Função para pegar todas as views do meu layout xml
    fun setupView(view: View){
        listaCarros = view.findViewById(R.id.rv_listaDeCarros)
        fabCalcular = view.findViewById(R.id.fab_calcular)

    }
    //função para chamar a conecção com o arquivo json
    fun callService(){
        MyTask().execute("https://igorbag.github.io/cars-api/cars.json")
    }
    fun setupList(){

        val adapter = CarAdapter(carrosArray)
        listaCarros.adapter = adapter

    }
    //metodo para clicks
    fun setupListeners(){
        //Click no botão calcular e navegação para a tela de calculo
        fabCalcular.setOnClickListener {

            val intent = Intent(context, CalculoAutonomia::class.java)
            startActivity(intent)
        }

    }

    // Classe interna para sincronizar com o json que contem os dados da lista
    inner class MyTask : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg url: String?): String {

            var urlConnection: HttpURLConnection? = null
            try {
                val urlBase = URL(url[0])

                urlConnection = urlBase.openConnection() as HttpURLConnection
                urlConnection.connectTimeout = 60000
                urlConnection.readTimeout = 60000

                var response = urlConnection.inputStream.bufferedReader().use { it.readText() }
                publishProgress(response)
            }catch (ex: Exception){
                Log.e("erro", "Erro ao realizar processamento")
            }finally {
                if (urlConnection != null){
                    urlConnection.disconnect()
                }
            }
            return " "
        }

        override fun onProgressUpdate(vararg values: String?) {
            try {
                // Leitura do array de json
                val jsonArray = JSONTokener(values[0]).nextValue() as JSONArray

                for (i in 0 until jsonArray.length()){
                    val id = jsonArray.getJSONObject(i).getString("id")
                    val preco = jsonArray.getJSONObject(i).getString("preco")
                    val bateria = jsonArray.getJSONObject(i).getString("bateria")
                    val potencia = jsonArray.getJSONObject(i).getString("potencia")
                    val recarga = jsonArray.getJSONObject(i).getString("recarga")
                    val urlPhoto = jsonArray.getJSONObject(i).getString("urlPhoto")


                    //Classe para construir o modelo de carro

                    val model = Carro(
                        id = id.toInt(),
                        preco = preco,
                        bateria = bateria,
                        potencia = potencia,
                        recarga = recarga,
                        urlPhoto = urlPhoto
                    )
                    // Adicionando cada modelo de carro dentro da lista
                    carrosArray.add(model)
                }
                setupList()

            }catch (ex: Exception){
                Log.e("Erro ->", ex.message.toString())
            }
        }


    }

}