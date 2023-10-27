package com.example.eletriccarstore.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.eletriccarstore.R
import com.example.eletriccarstore.data.CarFactory
import com.example.eletriccarstore.ui.adapter.CardAdapter

class FavoritesFragment  : Fragment(){
//    lateinit var btnCalculate : Button
    lateinit var lstCards     : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews(view)
        setupCardList()
        setupListeners(view)
    }

    fun setupViews(view: View){
        view.apply {
//            btnCalculate = findViewById(R.id.btn_calcular)
            lstCards     = findViewById(R.id.rv_informacoes)
        }
    }

    fun setupCardList(){
        val dados   = CarFactory.carList
        val adapter = CardAdapter(dados)
        lstCards.adapter = adapter
    }

    fun setupListeners(view: View) {
//        btnCalculate.setOnClickListener {
//            startActivity(view.Intent(this, CalcularAutonomia::class.java))
//        }
    }
}