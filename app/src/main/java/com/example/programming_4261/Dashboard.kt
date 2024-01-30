package com.example.programming_4261

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.firestore

class Dashboard : AppCompatActivity() {
    lateinit var user: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        user = intent.getStringExtra("user").toString()
        val tvGreeting = findViewById<TextView>(R.id.tvGreeting)
        val numUsers = findViewById<TextView>(R.id.tvNumUsers)
        val users = Firebase.firestore.collection("users")

        tvGreeting.text = "Hello: $user"
        countUsers(numUsers, users)

    }
    private fun countUsers(numUsers: TextView, users: CollectionReference) {
        users.get().addOnSuccessListener { documents ->
            numUsers.text = "Total Users: ${documents.size()}"
            }.addOnFailureListener {exception ->
                Log.w("IMP", "Error getting documents: ", exception)
            }
    }
}