package com.example.praktam2_2417051054.data.network
import com.example.praktam2_2417051054.data.model.Barang
import retrofit2.http.GET


interface ApiService {
    @GET("daftar_barang.json")
    suspend fun getBarang(): List<Barang>
}