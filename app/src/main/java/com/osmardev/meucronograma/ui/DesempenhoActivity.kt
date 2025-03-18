package com.osmardev.meucronograma.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import com.osmardev.meucronograma.R
import com.osmardev.meucronograma.model.DesempenhoAdapter
import com.osmardev.meucronograma.model.DesempenhoItem
import android.widget.TextView
import android.widget.Button
import android.graphics.Color

class DesempenhoActivity : AppCompatActivity() {

    private lateinit var barChart: BarChart
    private lateinit var lineChart: LineChart
    private lateinit var recyclerView: RecyclerView
    private lateinit var desempenhoAdapter: DesempenhoAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var txtProdutividade: TextView
    private lateinit var btnExportarPdf: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desempenho)

        barChart = findViewById(R.id.barChart)
        lineChart = findViewById(R.id.lineChart)
        recyclerView = findViewById(R.id.recyclerDesempenho)
        txtProdutividade = findViewById(R.id.txtProdutividade)
        btnExportarPdf = findViewById(R.id.btnExportarPdf)
        sharedPreferences = getSharedPreferences("ChecklistPrefs", MODE_PRIVATE)

        carregarDesempenho()
        carregarGraficoLinha()

        btnExportarPdf.setOnClickListener {

        }
    }

    private fun carregarGraficoLinha() {
        val entries = mutableListOf<Entry>()

        entries.add(Entry(1f, 10f))
        entries.add(Entry(2f, 25f))
        entries.add(Entry(3f, 40f))
        entries.add(Entry(4f, 55f))
        entries.add(Entry(5f, 70f))
        entries.add(Entry(6f, 85f))

        val dataSet = LineDataSet(entries, "Produtividade (%)").apply {
            color = Color.BLUE
            valueTextSize = 12f
            setDrawCircles(true)
            setCircleColor(Color.RED)
            setDrawFilled(true)
            fillColor = Color.CYAN
        }

        val lineData = LineData(dataSet)
        lineChart.data = lineData

        val description = Description()
        description.text = "Evolução da produtividade"
        lineChart.description = description

        lineChart.invalidate()
    }

    private fun carregarDesempenho() {
        val dadosDesempenho = mutableListOf<DesempenhoItem>()
        val entradasGrafico = mutableListOf<BarEntry>()

        val allKeys = sharedPreferences.all.keys.filter { it.startsWith("checklist_") }

        var totalDias = allKeys.size
        var totalCheckmarks = 0

        allKeys.sorted().forEachIndexed { index, key ->
            val qtdFeitos = sharedPreferences.getInt(key, 0)
            totalCheckmarks += qtdFeitos

            val desempenho = DesempenhoItem("Estudo", qtdFeitos, 10)
            dadosDesempenho.add(desempenho)

            entradasGrafico.add(BarEntry(index.toFloat(), qtdFeitos.toFloat()))
        }

        if (entradasGrafico.isEmpty()) {
            entradasGrafico.add(BarEntry(0f, 0f))
        }

        val mediaProdutividade = if (totalDias > 0) (totalCheckmarks / (totalDias * 4.0)) * 100 else 0.0
        txtProdutividade.text = "Produtividade: ${String.format("%.2f", mediaProdutividade)}%"

        desempenhoAdapter = DesempenhoAdapter(dadosDesempenho)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = desempenhoAdapter

        val dataSet = BarDataSet(entradasGrafico, "Tarefas concluídas por dia").apply {
            colors = ColorTemplate.COLORFUL_COLORS.toList()
            valueTextSize = 12f
        }

        val barData = BarData(dataSet)
        barChart.data = barData
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChart.invalidate()
    }
}