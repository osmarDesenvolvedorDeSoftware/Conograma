package com.osmardev.meucronograma.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diario_entries")
data class DiarioEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val data: String,
    val progresso: String,
    val desafios: String,
    val melhorias: String
)
