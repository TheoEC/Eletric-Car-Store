package com.example.eletriccarstore.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eletriccarstore.R
import com.example.eletriccarstore.domain.Car

class CardAdapter(private val carros: List<Car>) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    var carItemLister : (Car) -> Unit = {}

    //Cria uma nova view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_card_item, parent, false)
        return ViewHolder(view)
    }

    //Pega o conteudo da view e troca pela informacao de item de uma lista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.preco.text    = carros[position].preco
        holder.bateria.text  = carros[position].bateria
        holder.potencia.text = carros[position].potencia
        holder.recarga.text  = carros[position].recarga
        holder.favorito.setOnClickListener{
            carItemLister(carros[position])
        }
    }

    override fun getItemCount(): Int = carros.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val preco   : TextView
        val bateria : TextView
        val potencia: TextView
        val recarga : TextView
        val favorito: ImageView



        init {
            view.apply {
                preco    = findViewById(R.id.tv_preco_value)
                bateria  = findViewById(R.id.tv_bateria_value)
                potencia = findViewById(R.id.tv_potencia_value)
                recarga  = findViewById(R.id.tv_recarga_value)
                favorito = findViewById(R.id.iv_favorite)
            }
        }
    }
}

