package com.osmardev.meucronograma.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.osmardev.meucronograma.R
import com.osmardev.meucronograma.model.DiarioEntry

class DiarioAdapter(private val onItemLongClick: (DiarioEntry) -> Unit) :
    RecyclerView.Adapter<DiarioAdapter.ViewHolder>() {

    private var listaDiario = listOf<DiarioEntry>()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val data: TextView = view.findViewById(R.id.txtData)
        val progresso: TextView = view.findViewById(R.id.txtProgresso)
        val desafios: TextView = view.findViewById(R.id.txtDesafios)
        val melhorias: TextView = view.findViewById(R.id.txtMelhorias)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_diario, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listaDiario[position]
        holder.data.text = "Data: ${item.data}"
        holder.progresso.text = "O que aprendi hoje? ${item.progresso}"
        holder.desafios.text = "Quais desafios enfrentei? ${item.desafios}"
        holder.melhorias.text = "Como posso melhorar amanhã? ${item.melhorias}"


        holder.itemView.setOnLongClickListener {
            onItemLongClick(item)
            true
        }
    }

    override fun getItemCount() = listaDiario.size

    fun atualizarLista(novaLista: List<DiarioEntry>) {
        listaDiario = novaLista
        notifyDataSetChanged()
    }
}
