package com.example.eletriccarstore.ui

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
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.loader.content.AsyncTaskLoader
import androidx.recyclerview.widget.RecyclerView
import com.example.eletriccarstore.R
import com.example.eletriccarstore.data.CarFactory
import com.example.eletriccarstore.data.CarsApi
import com.example.eletriccarstore.domain.Car
import com.example.eletriccarstore.ui.adapter.CardAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class CarFragment : Fragment() {
    lateinit var btnCalculate : FloatingActionButton
    lateinit var lstCards     : RecyclerView
    lateinit var progressBar  : ProgressBar

    lateinit var carsApi      : CarsApi

    lateinit var noWifiImage  : ImageView
    lateinit var noWifiText   : TextView

    var carArray : ArrayList<Car> = ArrayList()

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
        setupViews(view)
        setupListeners()
    }

    override fun onResume() {
        super.onResume()

        if (checkForInternet(context)) {
            getAllCars()
        } else {
            setEmptyState()
        }
    }

    fun setupViews(view: View){
        view.apply {
            btnCalculate = findViewById(R.id.btn_calculate)
            lstCards     = findViewById(R.id.rv_informacoes)
            progressBar  = findViewById(R.id.pb_loader)
            noWifiImage  = findViewById(R.id.iv_noWifi)
            noWifiText   = findViewById(R.id.tv_noWifi)
        }
    }

    fun setEmptyState() {
        btnCalculate.isVisible = false
        lstCards.isVisible     = false
        progressBar.isVisible  = false
        noWifiImage.isVisible  = true
        noWifiText.isVisible   = true
    }

    fun setupCardList(list: List<Car>){
        val car_adapter = CardAdapter(list)
        lstCards.apply {
            adapter = car_adapter
            isVisible = true
        }
        progressBar.isVisible = false

        car_adapter.carItemLister = { carro ->
            val bateria = carro.bateria
        }
    }

    fun setupListeners() {
        btnCalculate.setOnClickListener {
            startActivity(Intent(context, CalcularAutonomia::class.java))
        }
    }

    fun setupRetrofit(){
        val  retrofit = Retrofit.Builder()
            .baseUrl("https://igorbag.github.io/cars-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        carsApi = retrofit.create(CarsApi::class.java)
    }

    fun getAllCars(){
        carsApi.getAlCars().enqueue(object : Callback<List<Car>>{
            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        progressBar.isVisible = false
                        noWifiImage.isVisible = false
                        noWifiText.isVisible  = false

                        setupCardList(it)
                    }

                } else {
                    Toast.makeText(context, R.string.response_error, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                Toast.makeText(context, R.string.response_error, Toast.LENGTH_LONG).show()
            }

        })
    }

    fun callService() {
        MyTask().execute("https://igorbag.github.io/cars-api/cars.json")
        progressBar.visibility = View.VISIBLE
    }

    fun checkForInternet(context: Context?): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false

            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

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
                urlConnection.readTimeout    = 60000
                urlConnection.setRequestProperty(
                    "Accept",
                    "application/json"
                )

                val responseCode = urlConnection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    var response = urlConnection.inputStream.bufferedReader().use { it.readText() }
                    publishProgress(response)
                } else {
                    Log.e("Erro", "Serviço indisponivel no momento ....")
                }
            } catch (ex: Exception) {
                Log.e("Erro", "Erro ao realizar o processamento ...")
            } finally {
                urlConnection?.disconnect()
            }

            return  " "
        }

        override fun onProgressUpdate(vararg values: String?) {
            try {
                val jsonArray = JSONTokener(values[0]).nextValue() as JSONArray
                carArray.clear()

                for (i in 0 until jsonArray.length()) {
                    val id = jsonArray.getJSONObject(i).getString("id")
                    Log.d("ID->", id)

                    val preco = jsonArray.getJSONObject(i).getString("preco")
                    Log.d("preco->", preco)

                    val bateria = jsonArray.getJSONObject(i).getString("bateria")
                    Log.d("bateria->", bateria)

                    val potencia = jsonArray.getJSONObject(i).getString("potencia")
                    Log.d("potencia->", potencia)

                    val recarga = jsonArray.getJSONObject(i).getString("recarga")
                    Log.d("recarga->", recarga)

                    val urlPhoto = jsonArray.getJSONObject(i).getString("urlPhoto")
                    Log.d("urlPhoto->", recarga)

                    val model = Car(
                        id.toInt(),
                        preco,
                        bateria,
                        potencia,
                        recarga,
                        urlPhoto
                    )
                    carArray.add(model)
                }
            } catch (ex: Exception) {
                Log.e("CarFragment.onProgressUpdate", "Deu ruim...")
            }
        }
    }
}