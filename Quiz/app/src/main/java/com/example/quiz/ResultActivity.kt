package com.example.quiz

import android.content.Intent // Importáld az Intent osztályt
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val eredmenyTextView: TextView = findViewById(R.id.eredmenyTextView)
        val eredmeny = intent.getDoubleExtra("eredmeny", 0.0)
        eredmenyTextView.text = "Eredmény: %.2f%%".format(eredmeny)

        val ujrakezdesButton: Button = findViewById(R.id.ujrakezdesButton)
        ujrakezdesButton.setOnClickListener { ujrakezdes(it) }
    }

    fun ujrakezdes(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
