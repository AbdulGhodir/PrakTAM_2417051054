package com.example.praktam2_2417051054.data.repository

import com.example.praktam2_2417051054.data.network.RetrofitClient
import com.example.praktam2_2417051054.data.model.Barang

class BarangRepository {
    suspend fun getBarang(): List<Barang> {
        return try {
            RetrofitClient.instance.getBarang()
        } catch (e: Exception) {
            emptyList()
        }
    }
}