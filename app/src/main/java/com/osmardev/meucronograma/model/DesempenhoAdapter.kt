package com.osmardev.meucronograma.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.osmardev.meucronograma.R

class DesempenhoAdapter(private val lista: List<DesempenhoItem>) :
    RecyclerView.Adapter<DesempenhoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtTitulo: TextView = view.findViewById(R.id.txtTitulo)
        val txtTarefasConcluidas: TextView = view.findViewById(R.id.txtTarefasConcluidas)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_desempenho, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]
        holder.txtTitulo.text = item.titulo
        holder.txtTarefasConcluidas.text = "Tarefas: ${item.tarefasConcluidas}/${item.totalTarefas}"
    }

    override fun getItemCount() = lista.size
}
