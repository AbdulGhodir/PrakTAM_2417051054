package model
import androidx.annotation.DrawableRes

data class Barang(
    val nama: String,
    val harga: Int,
    val stok: Int,
    @DrawableRes val ImagesRes: Int
)