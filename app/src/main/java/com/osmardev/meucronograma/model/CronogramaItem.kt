package com.osmardev.meucronograma.model

data class CronogramaItem(
    val horario: String,
    val atividade: String,
    val objetivo: String,
    var feito: Boolean = false
)
