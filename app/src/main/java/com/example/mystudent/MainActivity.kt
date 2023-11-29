package com.example.mystudent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mystudent.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object{
        val firestore = FirebaseFirestore.getInstance()
        val studentListLiveData: MutableLiveData<List<Student>>
                by lazy {
                    MutableLiveData<List<Student>>()
                }
        val studentCollectionRef = firestore.collection("student")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnAdd.setOnClickListener{
                val intent = Intent(this@MainActivity, AddActivity::class.java)
                startActivity(intent)
            }
        }

        observeStudents()
        getAllStudents()
    }

    private fun getAllStudents() {
        studentCollectionRef.addSnapshotListener { snapshots, error ->
            if (error != null) {
                Log.d("MainActivity", "Error listening for student changes", error)
                return@addSnapshotListener
            }
            val students = arrayListOf<Student>()
            snapshots?.forEach { documentReference ->
                students.add(
                    Student(
                        documentReference.id,
                        documentReference.get("nama").toString(),
                        documentReference.get("nim").toString(),
                        documentReference.get("jurusan").toString(),
                        documentReference.get("alamat").toString()
                        )
                )
            }
            if (students != null) {
                studentListLiveData.postValue(students)
            }

            with(binding) {
                rvStudents.apply {
                    layoutManager = LinearLayoutManager(context)
                }
            }
        }
    }

    private fun observeStudents() {
        studentListLiveData.observe(this) { students ->
            val adapter = StudentAdapter(students) { student ->
                val intent = Intent(this, EditActivity::class.java).putExtra("student", student)
                startActivity(intent)
            }
            binding.rvStudents.adapter = adapter
        }
    }


//    private fun observeStudents() {
//        studentListLiveData.observe(this) { students ->
//            val adapter = StudentAdapter( students, {
//                val intent = Intent(this, EditActivity::class.java).putExtra("student", student)
//                startActivity(intent)
//            })
//            binding.rvStudents.adapter = adapter
//        }
//    }
}