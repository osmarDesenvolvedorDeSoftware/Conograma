package com.osmardev.meucronograma.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.osmardev.meucronograma.R
import com.osmardev.meucronograma.data.DiarioDatabase
import com.osmardev.meucronograma.model.DiarioEntry
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.text.Editable
import android.text.TextWatcher
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.graphics.Paint
import android.graphics.Canvas
import java.io.File
import java.io.FileOutputStream

class DiarioActivity : AppCompatActivity() {

    private lateinit var editProgresso: EditText
    private lateinit var editDesafios: EditText
    private lateinit var editMelhorias: EditText
    private lateinit var btnSalvarDiario: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var diarioAdapter: DiarioAdapter
    private lateinit var database: DiarioDatabase
    private lateinit var editBuscarMes: EditText
    private lateinit var btnExportarPdf: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diario)

        editProgresso = findViewById(R.id.editProgresso)
        editDesafios = findViewById(R.id.editDesafios)
        editMelhorias = findViewById(R.id.editMelhorias)
        btnSalvarDiario = findViewById(R.id.btnSalvarDiario)
        recyclerView = findViewById(R.id.recyclerDiario)
        editBuscarMes = findViewById(R.id.editBuscarMes)
        btnExportarPdf = findViewById(R.id.btnExportarPdf)

        database = DiarioDatabase.getDatabase(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        diarioAdapter = DiarioAdapter { entrada -> excluirDiario(entrada) }
        recyclerView.adapter = diarioAdapter

        carregarEntradas()

        btnSalvarDiario.setOnClickListener {
            salvarDiario()
        }


        btnExportarPdf.setOnClickListener {
            exportarParaPdf()
        }


        editBuscarMes.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                buscarPorMes()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun salvarDiario() {
        val progresso = editProgresso.text.toString()
        val desafios = editDesafios.text.toString()
        val melhorias = editMelhorias.text.toString()
        val dataAtual = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

        if (progresso.isNotEmpty() && desafios.isNotEmpty() && melhorias.isNotEmpty()) {
            val entrada = DiarioEntry(data = dataAtual, progresso = progresso, desafios = desafios, melhorias = melhorias)

            lifecycleScope.launch {
                database.diarioDao().inserirEntrada(entrada)
                carregarEntradas()
            }

            editProgresso.text.clear()
            editDesafios.text.clear()
            editMelhorias.text.clear()

            Toast.makeText(this, "Diário salvo!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun buscarPorMes() {
        val mesBusca = editBuscarMes.text.toString()

        if (mesBusca.isNotEmpty()) {
            lifecycleScope.launch {
                val entradas = database.diarioDao().buscarPorMes(mesBusca)
                diarioAdapter.atualizarLista(entradas)
            }
        } else {
            carregarEntradas()
        }
    }

    private fun carregarEntradas() {
        lifecycleScope.launch {
            val entradas = database.diarioDao().listarEntradas()
            diarioAdapter.atualizarLista(entradas)
        }
    }

    private fun excluirDiario(entrada: DiarioEntry) {
        lifecycleScope.launch {
            database.diarioDao().excluirEntrada(entrada)
            carregarEntradas()
        }

        Snackbar.make(recyclerView, "Registro excluído!", Snackbar.LENGTH_LONG)
            .setAction("Desfazer") {
                lifecycleScope.launch {
                    database.diarioDao().inserirEntrada(entrada)
                    carregarEntradas()
                }
            }.show()
    }

    private fun exportarParaPdf() {
        lifecycleScope.launch {
            val registros = database.diarioDao().listarEntradas()

            if (registros.isEmpty()) {
                Toast.makeText(this@DiarioActivity, "Nenhum registro para exportar!", Toast.LENGTH_SHORT).show()
                return@launch
            }

            val pdfDocument = PdfDocument()
            val paint = Paint()

            val paginaInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
            val pagina = pdfDocument.startPage(paginaInfo)
            val canvas: Canvas = pagina.canvas

            var y = 50f
            paint.textSize = 18f
            paint.isFakeBoldText = true
            canvas.drawText("Diário de Aprendizado", 50f, y, paint)

            paint.textSize = 14f
            paint.isFakeBoldText = false
            y += 30

            for (registro in registros) {
                if (y > 800) {
                    pdfDocument.finishPage(pagina)
                    val novaPaginaInfo = PdfDocument.PageInfo.Builder(595, 842, pdfDocument.pages.size + 1).create()
                    val novaPagina = pdfDocument.startPage(novaPaginaInfo)
                    y = 50f
                    canvas.drawText("Continuação do Diário", 50f, y, paint)
                    y += 30
                }

                canvas.drawText("Data: ${registro.data}", 50f, y, paint)
                y += 20
                canvas.drawText("O que aprendi: ${registro.progresso}", 50f, y, paint)
                y += 20
                canvas.drawText("Desafios: ${registro.desafios}", 50f, y, paint)
                y += 20
                canvas.drawText("Melhorias: ${registro.melhorias}", 50f, y, paint)
                y += 40
            }

            pdfDocument.finishPage(pagina)

            val diretorio = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "MeuCronograma")
            if (!diretorio.exists()) diretorio.mkdirs()

            val arquivo = File(diretorio, "Diario_Aprendizado.pdf")
            pdfDocument.writeTo(FileOutputStream(arquivo))
            pdfDocument.close()

            Toast.makeText(this@DiarioActivity, "PDF salvo em: ${arquivo.absolutePath}", Toast.LENGTH_LONG).show()
        }
    }
}
