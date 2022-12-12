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
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.eletricarapp.Adapter.CarAdapter
import com.example.eletricarapp.R
import com.example.eletricarapp.data.CarsAPI
import com.example.eletricarapp.domain.Carro
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.net.URL


// fragmento para retornar o layout que for preciso
class CarFragment : Fragment() {
    lateinit var listaCarros: RecyclerView
    lateinit var fabCalcular: FloatingActionButton
    lateinit var progress: ProgressBar
    lateinit var ivEmpityState:ImageView
    lateinit var tv_no_wifi:TextView
    lateinit var carsApi: CarsAPI


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
        setupRetrofit()
        setupView(view)
        setupListeners()

    }

    override fun onResume() {
        super.onResume()
        if (checkForInternet(context)){
            //callService() //outra forma de chamar serviço
            getAllcars()
        }else{
            empityState()
        }
    }

    fun setupRetrofit(){
        val retrofit = Retrofit.Builder().baseUrl("https://igorbag.github.io/cars-api/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        carsApi = retrofit.create(CarsAPI::class.java)


    }


    fun getAllcars(){
        carsApi.getAllCars().enqueue(object : Callback<List<Carro>>{
            override fun onResponse(call: Call<List<Carro>>, response: Response<List<Carro>>) {
                if (response.isSuccessful){
                    progress.isVisible = false
                    listaCarros.isVisible = true
                    ivEmpityState.isVisible = false
                    tv_no_wifi.isVisible = false
                    response.body()?.let {
                        setupList(it)
                    }

                }else{
                    Toast.makeText(context, "Algo deu errado tente mais tarde", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Carro>>, t: Throwable) {
                Toast.makeText(context, "Algo deu errado tente mais tarde", Toast.LENGTH_LONG).show()            }

        })
    }
    fun empityState(){
        progress.isVisible = false
        listaCarros.isVisible = false
        ivEmpityState.isVisible = true
        tv_no_wifi.isVisible = true
    }

    // Função para pegar todas as views do meu layout xml
    fun setupView(view: View){
        listaCarros = view.findViewById(R.id.rv_listaDeCarros)
        fabCalcular = view.findViewById(R.id.fab_calcular)
        progress = view.findViewById(R.id.pb_loader)
        ivEmpityState = view.findViewById(R.id.iv_empityState)
        tv_no_wifi = view.findViewById(R.id.tv_no_wifi)


    }
    //função para chamar a conecção com o arquivo json
    fun callService(){
        MyTask().execute("https://igorbag.github.io/cars-api/cars.json")

    }
    fun setupList(lista: List<Carro>){

        val adapter = CarAdapter(lista)
        listaCarros.adapter = adapter
        adapter.carItemListener = { carro ->

        }
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

    // Classe interna para sincronizar com o json que contem os dados da lista, metodo sem abstração
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
                        urlPhoto = urlPhoto,
                        isFavorite = false
                    )
                    // Adicionando cada modelo de carro dentro da lista
                    carrosArray.add(model)
                }
                progress.isVisible = false
                listaCarros.isVisible = true
                ivEmpityState.isVisible = false
                tv_no_wifi.isVisible = false

                //setupList()

            }catch (ex: Exception){
                Log.e("Erro ->", ex.message.toString())
            }
        }


    }

}