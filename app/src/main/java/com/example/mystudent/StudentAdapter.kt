package com.example.mystudent

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mystudent.databinding.ItemStudentBinding

// membuat alias untuk tipe data fungsi OnClickUpdate
typealias onClickUpdate = (Student) -> Unit

class StudentAdapter(private val listStudents: List<Student>,private val onClickUpdate : onClickUpdate):
    RecyclerView.Adapter<StudentAdapter.ItemStudentViewHolder>() {

    inner class ItemStudentViewHolder(private val binding: ItemStudentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Student) {
            with(binding) {
                txtName.text = data.nama
                txtJurusan.text = data.jurusan

                itemView.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString("id", data.id)
                    bundle.putString("nama", data.nama)
                    bundle.putString("nim", data.nim)
                    bundle.putString("jurusan", data.jurusan)
                    bundle.putString("alamat", data.alamat)

                    val intent = Intent(
                        binding.root.context,
                        DetailActivity::class.java
                    ).apply { putExtras(bundle) }
                    binding.root.context.startActivity(intent)
                }

                edit.setOnClickListener {
                    onClickUpdate(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemStudentViewHolder {
        val binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ItemStudentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listStudents.size
    }

    override fun onBindViewHolder(holder: StudentAdapter.ItemStudentViewHolder, position: Int) {
        holder.bind(listStudents[position])
    }

}