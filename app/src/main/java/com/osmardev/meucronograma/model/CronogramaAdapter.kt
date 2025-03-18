package com.osmardev.meucronograma.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.osmardev.meucronograma.R

class CronogramaAdapter(
    private val listaCronograma: List<CronogramaItem>
) : RecyclerView.Adapter<CronogramaAdapter.CronogramaViewHolder>() {

    inner class CronogramaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val horario: TextView = itemView.findViewById(R.id.txtHorario)
        val atividade: TextView = itemView.findViewById(R.id.txtAtividade)
        val objetivo: TextView = itemView.findViewById(R.id.txtObjetivo)
        val checkFeito: CheckBox = itemView.findViewById(R.id.checkFeito)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CronogramaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cronograma, parent, false)
        return CronogramaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CronogramaViewHolder, position: Int) {
        val item = listaCronograma[position]
        holder.horario.text = item.horario
        holder.atividade.text = item.atividade
        holder.objetivo.text = item.objetivo
        holder.checkFeito.isChecked = item.feito
    }

    override fun getItemCount(): Int = listaCronograma.size
}
