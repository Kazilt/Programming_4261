package com.example.programming_4261

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView

class Dashboard : AppCompatActivity() {
    lateinit var user: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        user = intent.getStringExtra("user").toString()
        val tvGreeting = findViewById<TextView>(R.id.tvGreeting)
        tvGreeting.text = "Hello: $user"
    }
}