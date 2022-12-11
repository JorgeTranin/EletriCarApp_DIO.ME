package com.example.eletricarapp.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.eletricarapp.Adapter.CarAdapter
import com.example.eletricarapp.R
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
    lateinit var progress: ProgressBar


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
        checkForInternet(context)
        setupView(view)
        callService()
        setupListeners()
    }

    // Função para pegar todas as views do meu layout xml
    fun setupView(view: View){
        listaCarros = view.findViewById(R.id.rv_listaDeCarros)
        fabCalcular = view.findViewById(R.id.fab_calcular)
        progress = view.findViewById(R.id.pb_loader)


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

    // metodo que analisa se tem conecção com a internet ou não
    fun checkForInternet(context: Context?): Boolean{
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false

            val activityNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when{

                activityNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activityNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        }else{
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false

            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    // Classe interna para sincronizar com o json que contem os dados da lista
    inner class MyTask : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            progress.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg url: String?): String {

            var urlConnection: HttpURLConnection? = null
            try {
                val urlBase = URL(url[0])

                urlConnection = urlBase.openConnection() as HttpURLConnection
                urlConnection.connectTimeout = 60000
                urlConnection.readTimeout = 60000
                urlConnection.setRequestProperty(
                    "Accept",
                    "application/jason"
                )

                // validação para analisar se a resposta esta ok
                val responseCode = urlConnection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK){

                    var response = urlConnection.inputStream.bufferedReader().use { it.readText() }
                    publishProgress(response)
                }else{
                    Log.e("erro", "Serviço indisponivel no momento...")
                }

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
                progress.visibility = View.GONE
                listaCarros.visibility = View.VISIBLE

                setupList()

            }catch (ex: Exception){
                Log.e("Erro ->", ex.message.toString())
            }
        }


    }

}