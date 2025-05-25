package com.example.albumeter.recyclerView

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.albumeter.BBDD.Album
import com.example.albumeter.R
import com.example.albumeter.databinding.RecyclerViewDiscoBinding
import com.example.albumeter.recyclerView.Adaptador.*

class Adaptador(var discos: List<Album>) : RecyclerView.Adapter<DiscoVH>() {

    inner class DiscoVH(val binding: RecyclerViewDiscoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var posicion = 0

        //cuando implemente el boton detalles del recycler view
        init {
            binding.botonDetallesRecyclerView.setOnClickListener {
                val albumId = discos[adapterPosition].id
                val mibundle = bundleOf("id" to albumId)
                binding.rviClPrincipal.findNavController()
                    .navigate(R.id.action_misDiscosContenedor4_to_agregarDiscoFragment6, mibundle)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoVH {
        val binding =
            RecyclerViewDiscoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DiscoVH(binding)
    }

    override fun getItemCount(): Int {
        return discos.count()
    }

    override fun onBindViewHolder(holder: DiscoVH, position: Int) {
        holder.binding.EditTextTituloDisco.setText(discos[position].titulo)
        holder.binding.EditTextNombreBanda.setText(discos[position].banda)
        holder.binding.EditTextAno.setText(discos[position].ano.toString() ?: "Año desconocido")
        holder.binding.EditTextEstilo.setText(discos[position].estilo)

        holder.binding.EditTextNota.setText(discos[position].nota.toString() ?: "¿?")

        // cambio el colo de la nota segun numero, referencia: https://es.stackoverflow.com/questions/511340/como-cambiar-el-color-del-texto-y-del-bot%c3%b3n-al-hacer-click
        val nota = discos[position].nota ?: 0.0
        val color = when {
            nota < 5 -> Color.parseColor("#D20103")
            nota in 5.0..5.99 -> Color.parseColor("#F1FE04")
            nota in 6.0..6.99 -> Color.parseColor("#CA910C")
            nota in 7.0..7.99 -> Color.parseColor("#BDCA0C")
            nota in 8.0..8.99 -> Color.parseColor("#85C32F")
            nota in 9.0..10.0 -> Color.parseColor("#0BCC18")
            else -> Color.BLACK
        }

        holder.binding.EditTextNota.setTextColor(color)



    }

}