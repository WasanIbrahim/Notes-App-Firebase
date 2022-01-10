package com.example.notes_app_firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.example.notes_app_firebase.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    val TAG: String = "inMainActivity"

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val db = Firebase.firestore

        val etNote = binding.etNote.text

        binding.submitButton.setOnClickListener{
            // Create a new user with a first and last name
            if (etNote.isNotEmpty()) {
                val user = hashMapOf(
                    "note" to "$etNote"
                )

                // Add a new document with a generated ID
                db.collection("notes")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }
            }
            else{
                Toast.makeText(this,"please fill all blanks",Toast.LENGTH_LONG).show()
            }
        }

        binding.refreshButton.setOnClickListener {

            db.collection("notes")
                .get()
                .addOnSuccessListener { result ->
                    var details = "\n"
                    for (note in result){
                        note.data.map{
                            (key,value) ->
                            details = details + " $value \n \n"
                        }
                    }
                    binding.tvNote.text = (""+ details)
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }

        }

    }
}