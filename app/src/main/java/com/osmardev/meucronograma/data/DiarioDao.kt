package com.osmardev.meucronograma.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.osmardev.meucronograma.model.DiarioEntry

@Dao
interface DiarioDao {
    @Insert
    suspend fun inserirEntrada(diarioEntry: DiarioEntry)

    @Query("SELECT * FROM diario_entries ORDER BY id DESC")
    suspend fun listarEntradas(): List<DiarioEntry>

    @Query("SELECT * FROM diario_entries WHERE SUBSTR(data, 4, 7) = :mes ORDER BY id DESC")
    suspend fun buscarPorMes(mes: String): List<DiarioEntry>

    @Delete
    suspend fun excluirEntrada(diarioEntry: DiarioEntry)
}

