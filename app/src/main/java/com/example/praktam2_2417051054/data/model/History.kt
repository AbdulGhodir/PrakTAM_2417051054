package com.example.praktam2_2417051054.data.model

data class History(
    val kodeTransaksi: String,
    val waktuTransaksi: String,
    val daftarBelanja: Map<Barang, Int>,
    val uangTunai: Int,
    val kasir: String
)