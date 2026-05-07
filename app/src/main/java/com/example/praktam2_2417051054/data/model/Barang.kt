package com.example.praktam2_2417051054.data.model
import com.google.gson.annotations.SerializedName

data class Barang(
    @SerializedName("nama")
    val nama: String,

    @SerializedName("harga")
    val harga: Int,

    @SerializedName("stok")
    val stok: Int,

    @SerializedName("terjual")
    val terjual: Int,

    @SerializedName("image_url")
    val imagesUrl: String
)