package com.example.mystudent

import android.app.ActionBar.LayoutParams
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import com.example.mystudent.MainActivity.Companion.studentCollectionRef
import com.example.mystudent.databinding.ActivityDetailBinding
import java.nio.file.Files.delete

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    // mendeklarasikan variabel untuk menyimpan data mahasiswa
    lateinit var id: String

    // mendeklarasikan variabel untuk TextView pada dialog konfirm
    private lateinit var cancelTxt: TextView
    private lateinit var yesTxt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // mendapatkan data yg dikirim dr intent
        val data = intent.extras
        id = data?.getString("id").toString()

        with(binding) {
            // data yang akan ditampilkan
            namaMhs.text = "${data?.getString("nama")}"
            nimMhs.text = "${data?.getString("nim")}"
            jurusanMhs.text = "${data?.getString("jurusan")}"
            alamatMhs.text = "${data?.getString("alamat")}"

            // membuat objek Dialog untuk konfirm penghapusan
            val dialog = Dialog(this@DetailActivity)
            btnHapus.setOnClickListener {
                // menampilkna dialog konfirm penghapusan
                dialog.setContentView(R.layout.dialog_delete)
                dialog.window!!.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                dialog.setCancelable(false)
                dialog.window!!.attributes.gravity = Gravity.BOTTOM
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                cancelTxt = dialog.findViewById(R.id.txt_cancel)
                yesTxt = dialog.findViewById(R.id.txt_yes)

                cancelTxt.setOnClickListener {
                    dialog.dismiss()
                }

                yesTxt.setOnClickListener {
                    deleteStudent(id)
                    finish()
                }

                dialog.show()
            }

            btnBack.setOnClickListener {
                onBackPressed()
            }
        }
    }

    // fungsi untuk menghapus data mahasiswa dari firestore
    private fun deleteStudent(id: String) {
        studentCollectionRef.document(id).delete()
            .addOnFailureListener {
                Log.d("MainActivity", "Error deleting student : ", it)
            }
    }
}