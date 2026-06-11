package com.example.praktam2_2417051054.data.model

import android.net.Uri
import com.example.praktam2_2417051054.R

class Users (
    val id: Int,
    var nama: String,
    var email: String,
    var namaToko: String,
    val password: String,
    var fotoProfil: Any = R.drawable.foto_profile
)
