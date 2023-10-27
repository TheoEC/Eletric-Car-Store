package com.example.eletriccarstore.ui

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.eletriccarstore.R
import android.widget.ImageView
import android.widget.TextView


class CalcularAutonomia: AppCompatActivity() {
    private lateinit var preco       : EditText
    private lateinit var kmPercorrido: EditText
    private lateinit var btnCalcular : Button
    private lateinit var btnClose    : ImageView
    private lateinit var resultado   : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calcular_autonomia)
        setupView()
        setupListeners()

        val lastResult = getSharedPref()
        resultado.text = lastResult.toString()
    }

    private fun setupView(){
        preco        = findViewById(R.id.et_preco)
        kmPercorrido = findViewById(R.id.et_km_percorrido)
        btnCalcular  = findViewById(R.id.btn_calcular)
        btnClose     = findViewById(R.id.iv_close)
        resultado    = findViewById(R.id.tv_resultado)
    }

    private fun setupListeners(){
        btnCalcular.setOnClickListener{
            calcular()
        }
        btnClose.setOnClickListener {
            finish()
        }
    }

    private fun calcular(){
        val preco  = preco.text.toString().toFloat()
        val kmPerc = kmPercorrido.text.toString().toFloat()
        val result = preco / kmPerc

        resultado.text = result.toString()
        saveSharedPref(result)
    }

    fun saveSharedPref(resultado: Float){
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putFloat(getString(R.string.last_calc), resultado)
            apply()
        }
    }

    fun getSharedPref(): Float {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getFloat(getString(R.string.last_calc), 0.0f)
    }
}