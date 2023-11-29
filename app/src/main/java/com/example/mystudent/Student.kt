package com.example.mystudent

import java.io.Serializable

data class Student(
    var id: String = "",
    var nama: String = "",
    var nim: String = "",
    var jurusan: String = "",
    var alamat: String = ""
): Serializable
