package com.example.mystudent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.mystudent.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnBack.setOnClickListener {
                onBackPressed()
            }

            btnAdd.setOnClickListener {
                // mengambil nilai yang diinputkan
                val nama = addNama.text.toString()
                val nim = addNim.text.toString()
                val jurusan = addJurusan.text.toString()
                val alamat = addAlamat.text.toString()

                // membuat objek baru
                val newStudent = Student(nama = nama, nim = nim, jurusan = jurusan, alamat = alamat)
                addStudent(newStudent)

                Toast.makeText(this@AddActivity, "Data mahasiswa berhasil ditambahkan",
                    Toast.LENGTH_SHORT).show()
                finish()
            }

            btnBatal.setOnClickListener {
                onBackPressed()
            }
        }
    }

    // fungsi untuk menambahkan data mahasiswa ke firestore
    private fun addStudent(student: Student) {
        MainActivity.studentCollectionRef.add(student).addOnFailureListener {
            Log.d("MainActivity", "Error adding student : ", it)
        }
    }
}