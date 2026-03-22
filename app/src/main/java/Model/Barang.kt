package model
import androidx.annotation.DrawableRes

data class Barang(
    val nama: String,
    val harga: Int,
    val stok: Int,
    val terjual: Int,
    @DrawableRes val ImagesRes: Int
)