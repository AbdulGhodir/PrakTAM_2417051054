package com.example.praktam2_2417051054.data.network
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL =
        "https://gist.githubusercontent.com/AbdulGhodir/2620ac8e014a08e74618f8e17bcf6c5b/raw/8ebfbd1f30728dddc50493bbeda6f2668e1a2740/"

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
