package com.example.programming_4261

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.firestore

class Login : AppCompatActivity() {
    private var sign_up :Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val users = Firebase.firestore.collection("users")
        val switch: Switch = findViewById(R.id.Sign_swtch)
        val signIn_btn: Button = findViewById(R.id.btn_signIn)
        val uname: EditText = findViewById(R.id.etUser)
        val pswrd: EditText = findViewById(R.id.etPswrd)
        switch.setOnCheckedChangeListener {
            buttonView, isChecked ->
            swtch(switch, signIn_btn, isChecked)
        }
        signIn_btn.setOnClickListener {
            if (sign_up) {
                sign_up(users, uname, pswrd)
            } else {
                sign_in(users, uname, pswrd)
            }

        }

    }
    private fun goToDash(user: String) {
        val intent = Intent(this, Dashboard::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
    }
    private fun sign_up(users:CollectionReference, uname: EditText, pswrd: EditText) {
        val un = uname.text.toString()
        val ps = pswrd.text.toString()
        users.get().addOnSuccessListener { documents ->
            for (document in documents) {
                if (document.id.equals(un)) {
                    Toast.makeText(this, "You are already registered. Please Sign in", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

            }
            users.document(un).set(hashMapOf("password" to ps)).addOnSuccessListener { documentReference ->
                Toast.makeText(this, "Registered!", Toast.LENGTH_SHORT).show()
                goToDash(un)
            }.addOnFailureListener {exception ->
                Log.w("IMP", "Error getting documents: ", exception)
            }

        }
            .addOnFailureListener { exception ->
                Log.w("IMP", "Error getting documents: ", exception)
            }
    }
    private fun sign_in(users:CollectionReference, uname: EditText, pswrd: EditText) {
        val un = uname.text.toString()
        val ps = pswrd.text.toString()
        users.get().addOnSuccessListener { documents ->
            for (document in documents) {
                if (document.id.equals(un)) {
                    if (document.data["password"]?.equals(ps) == true) {
                        Toast.makeText(this, "Signing in", Toast.LENGTH_SHORT).show()
                        goToDash(un)
                    }
                    else {
                        Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show()
                    }
                    return@addOnSuccessListener
                }

            }
            Toast.makeText(this, "No user found", Toast.LENGTH_SHORT).show()

        }
            .addOnFailureListener { exception ->
                Log.w("IMP", "Error getting documents: ", exception)
            }
    }
    private fun swtch(switch: Switch, btn: Button, checked: Boolean) {
        sign_up = checked
        if (sign_up) {
            switch.text = "Sign Up"
            btn.text = "Sign Up"
        } else {
            switch.text = "Sign In"
            btn.text = "Sign In"
        }
    }
}