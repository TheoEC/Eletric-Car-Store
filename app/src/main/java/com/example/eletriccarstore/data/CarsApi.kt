package com.example.eletriccarstore.data

import com.example.eletriccarstore.domain.Car
import retrofit2.Call
import retrofit2.http.GET

interface CarsApi {

    @GET("cars.json")
    fun getAlCars(): Call<List<Car>>
}