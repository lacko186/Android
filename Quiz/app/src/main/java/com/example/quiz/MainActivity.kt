package com.example.quiz

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    var kerdesIndex = 0
    val kerdesek = Kerdesvalaszok.kerdesek
    val lehetosegek = Kerdesvalaszok.lehetosegek
    val valaszok = Kerdesvalaszok.valaszok
    var helyesValaszokSzama = 0

    lateinit var timer: CountDownTimer
    var remainingTime: Long = 180000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val kerdesTextView: TextView = findViewById(R.id.kerdes)
        val button1: Button = findViewById(R.id.elso)
        val button2: Button = findViewById(R.id.masodik)
        val button3: Button = findViewById(R.id.harmadik)
        val button4: Button = findViewById(R.id.negyedik)
        val beviteliButton: Button = findViewById(R.id.bevitel)
        val timerTextView: TextView = findViewById(R.id.timerTextView)

        startTimer(timerTextView)

        fun kovetkezoKerdes() {
            kerdesTextView.text = kerdesek[kerdesIndex]
            button1.text = lehetosegek[kerdesIndex][0]
            button2.text = lehetosegek[kerdesIndex][1]
            button3.text = lehetosegek[kerdesIndex][2]
            button4.text = lehetosegek[kerdesIndex][3]
        }

        kovetkezoKerdes()

        var kivalsztottValasz: String? = null

        button1.setOnClickListener { kivalsztottValasz = button1.text.toString() }
        button2.setOnClickListener { kivalsztottValasz = button2.text.toString() }
        button3.setOnClickListener { kivalsztottValasz = button3.text.toString() }
        button4.setOnClickListener { kivalsztottValasz = button4.text.toString() }

        beviteliButton.setOnClickListener {
            if (kivalsztottValasz != null) {
                if (kivalsztottValasz == valaszok[kerdesIndex]) {
                    Toast.makeText(this, "Helyes válasz!", Toast.LENGTH_SHORT).show()
                    helyesValaszokSzama++
                } else {
                    Toast.makeText(this, "Helytelen válasz!", Toast.LENGTH_SHORT).show()
                }

                kerdesIndex++
                if (kerdesIndex < kerdesek.size) {
                    kovetkezoKerdes()
                } else {
                    endQuiz()
                }
                kivalsztottValasz = null
            } else {
                Toast.makeText(this, "Kérlek válassz egy lehetőséget!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startTimer(timerTextView: TextView) {
        timer = object : CountDownTimer(remainingTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished
                val seconds = (remainingTime / 1000).toInt()
                timerTextView.text = String.format("%02d:%02d", seconds / 60, seconds % 60)
            }

            override fun onFinish() {
                Toast.makeText(this@MainActivity, "Lejárt az idő!", Toast.LENGTH_SHORT).show()
                endQuiz()
            }
        }.start()
    }

    private fun endQuiz() {
        timer.cancel()
        val eredmeny = (helyesValaszokSzama.toDouble() / kerdesek.size) * 100
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("eredmeny", eredmeny)
        }
        startActivity(intent)

        kerdesIndex = 0
        helyesValaszokSzama = 0
        finish()
    }
}
