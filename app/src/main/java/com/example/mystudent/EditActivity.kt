package com.example.mystudent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.mystudent.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            val student = intent.getSerializableExtra("student") as Student
            edtNama.setText(student.nama)
            edtNim.setText(student.nim)
            edtJurusan.setText(student.jurusan)
            edtAlamat.setText(student.alamat)

            btnSimpan.setOnClickListener {
                val updateStudent = Student(
                    id = student.id,
                    nama = edtNama.text.toString(),
                    nim = edtNim.text.toString(),
                    jurusan = edtJurusan.text.toString(),
                    alamat = edtAlamat.text.toString()
                )

                updateStudent(updateStudent) // memperbarui data dr objek Student
            }

            btnBack.setOnClickListener {
                onBackPressed()
            }
        }
    }

    // fungsi untuk memperbarui data dahasiswa di firestore
    private fun updateStudent(student: Student) {
        MainActivity.studentCollectionRef.document(student.id).set(student).addOnSuccessListener {
            runOnUiThread {
                Toast.makeText(this@EditActivity, "Data mahasiswa berhasil disimpan", Toast.LENGTH_SHORT).show()
                finish()
            }
        }.addOnFailureListener {
            Log.d("MainActivity", "Error updating student : ", it)
        }
    }
}