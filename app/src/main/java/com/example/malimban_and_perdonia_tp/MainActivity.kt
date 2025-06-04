package com.example.malimban_and_perdonia_tp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.malimban_and_perdonia_tp.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.sql.Ref

class MainActivity : AppCompatActivity() {

    lateinit var bindind : ActivityMainBinding

    private lateinit var firebaseRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindind.root)

        firebaseRef = FirebaseDatabase.getInstance().getReference("userInfo")

        bindind.save.setOnClickListener {
            val fullName = bindind.name.text.toString().trim()
            val email = bindind.email.text.toString().trim()
            val phone = bindind.phone.text.toString().trim()
            val age = bindind.age.text.toString().toIntOrNull() ?: 0
            val gender = bindind.gender.text.toString().trim()

            if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty() || age == 0 || gender.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = UserInfo(fullName, email, phone, age, gender)


            firebaseRef.child(fullName).setValue(user)
                .addOnSuccessListener {
                    Toast.makeText(this, "Info submitted successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to submit info", Toast.LENGTH_SHORT).show()
                }
        }

    }
}