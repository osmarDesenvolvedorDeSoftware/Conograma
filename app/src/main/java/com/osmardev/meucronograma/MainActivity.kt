package com.osmardev.meucronograma

import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.osmardev.meucronograma.model.CronogramaAdapter
import com.osmardev.meucronograma.model.CronogramaItem
import com.osmardev.meucronograma.ui.DiarioActivity
import com.osmardev.meucronograma.utils.NotificationScheduler
import android.content.SharedPreferences
import android.widget.CheckBox
import com.osmardev.meucronograma.ui.DesempenhoActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cronogramaAdapter: CronogramaAdapter
    private lateinit var checkEstudo: CheckBox
    private lateinit var checkExercicio: CheckBox
    private lateinit var checkLeitura: CheckBox
    private lateinit var checkTarefa: CheckBox
    private lateinit var sharedPreferences: SharedPreferences
    private val DATE_KEY = "lastChecklistDate"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDesempenho: Button = findViewById(R.id.btnDesempenho)
        btnDesempenho.setOnClickListener {
            val intent = Intent(this, DesempenhoActivity::class.java)
            startActivity(intent)
        }


        checkEstudo = findViewById(R.id.checkEstudo)
        checkExercicio = findViewById(R.id.checkExercicio)
        checkLeitura = findViewById(R.id.checkLeitura)
        checkTarefa = findViewById(R.id.checkTarefa)

        sharedPreferences = getSharedPreferences("ChecklistPrefs", MODE_PRIVATE)

        checkEstudo.isChecked = sharedPreferences.getBoolean("checkEstudo", false)
        checkExercicio.isChecked = sharedPreferences.getBoolean("checkExercicio", false)
        checkLeitura.isChecked = sharedPreferences.getBoolean("checkLeitura", false)
        checkTarefa.isChecked = sharedPreferences.getBoolean("checkTarefa", false)

        verificarData()

        checkEstudo.setOnCheckedChangeListener { _, isChecked -> salvarChecklist("checkEstudo", isChecked) }
        checkExercicio.setOnCheckedChangeListener { _, isChecked -> salvarChecklist("checkExercicio", isChecked) }
        checkLeitura.setOnCheckedChangeListener { _, isChecked -> salvarChecklist("checkLeitura", isChecked) }
        checkTarefa.setOnCheckedChangeListener { _, isChecked -> salvarChecklist("checkTarefa", isChecked) }


        solicitarPermissoes()


        recyclerView = findViewById(R.id.recyclerCronograma)

        val cronograma = listOf(
            CronogramaItem("06:30 - 07:00", "Café da manhã + Podcast em Inglês", "Imersão em Inglês (Listening)"),
            CronogramaItem("07:00 - 08:00", "Academia","Saude Física e mental"),
            CronogramaItem("08:00 - 08:45", "Estudo prático de Android", "Desenvolvimento profissional"),
            CronogramaItem("09:00 - 13:00", "Trabalho", "Concentração profissional"),
            CronogramaItem("13:00 - 15:00", "Almoço + Leitura BBC", "Recuperação + Inglês"),
            CronogramaItem("15:00 - 18:00", "Trabalho", "Produtividade profissional"),
            CronogramaItem("18:30 - 19:00", "Jantar + descanso", "Recuperação"),
            CronogramaItem("19:00 - 19:30", "Série/YouTube + Anki", "Imersão e vocabulário"),
            CronogramaItem("19:30 - 21:30", "Tempo livre", "Relaxamento e lazer"),
            CronogramaItem("21:30 - 22:00", "Preparação para dormir", "Garantir qualidade do sono")


        )

        cronogramaAdapter = CronogramaAdapter(cronograma)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = cronogramaAdapter


        agendarNotificacoes()


        val btnDiario: Button = findViewById(R.id.btnDiario)
        btnDiario.setOnClickListener {
            val intent = Intent(this, DiarioActivity::class.java)
            startActivity(intent)
        }
    }


    private fun solicitarPermissoes() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                startActivity(intent)
            }
        }
    }

    private fun salvarChecklist(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    private fun verificarData() {
        val hoje = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val ultimaData = sharedPreferences.getString(DATE_KEY, "")

        if (hoje != ultimaData) {

            salvarChecklist("checkEstudo", false)
            salvarChecklist("checkExercicio", false)
            salvarChecklist("checkLeitura", false)
            salvarChecklist("checkTarefa", false)


            val editor = sharedPreferences.edit()
            editor.putString(DATE_KEY, hoje)
            editor.apply()
        }
    }


    private fun agendarNotificacoes() {
        NotificationScheduler.agendarNotificacao(this, 6, 30, "Hora do café da manhã + Podcast!", 1)
        NotificationScheduler.agendarNotificacao(this, 7, 0, "Hora do estudo de Android!", 2)
        NotificationScheduler.agendarNotificacao(this, 8, 0, "Hora da academia!", 3)
        NotificationScheduler.agendarNotificacao(this, 13, 0, "Hora do almoço + leitura!", 4)
        NotificationScheduler.agendarNotificacao(this, 19, 0, "Hora de ver um vídeo em inglês!", 5)
        NotificationScheduler.agendarNotificacao(this, 21, 30, "Hora de se preparar para dormir!", 6)
    }
}
