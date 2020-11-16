package ru.geekbrains.AndroidKotlin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // OK
        findViewById<Button>(R.id.button).setOnClickListener { findViewById<TextView>(R.id.text).setText("Ok") }
    }
}