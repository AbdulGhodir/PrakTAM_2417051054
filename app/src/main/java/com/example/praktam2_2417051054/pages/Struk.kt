package com.example.praktam2_2417051054.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.praktam2_2417051054.SharedData
import com.example.praktam2_2417051054.data.model.Barang
import kotlin.collections.component1
import kotlin.collections.component2

@Composable
fun Struk(modifier: Modifier = Modifier, navController: NavController) {
    val totalHarga = SharedData.keranjang.entries.sumOf { (barang, jumlahBarang) ->
        barang.harga * jumlahBarang
    }
    val persentasePajak = 10
    val pajak = totalHarga * persentasePajak / 100

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.size(36.dp))

            Text(
                text = "Struk Transaksi",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.1f))
                    .clickable {
                        SharedData.keranjang = mapOf()
                        navController.navigate("kasir")
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Tutup",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Logo",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "OurKasir", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            }

            Spacer(modifier = Modifier.height(20.dp))
            GarisStruk()
            Spacer(modifier = Modifier.height(16.dp))

            InfoBarisStruk("No. Struk", "INV-2605-046")
            Spacer(modifier = Modifier.height(8.dp))
            InfoBarisStruk("Waktu", "30/05/2026 15:05")
            Spacer(modifier = Modifier.height(8.dp))
            InfoBarisStruk("Kasir", "Abdul Ghodir")

            Spacer(modifier = Modifier.height(16.dp))
            GarisStruk()
            Spacer(modifier = Modifier.height(16.dp))

            SharedData.keranjang.toList().forEachIndexed { index, (barang, jumlahBeli) ->
                ItemBarisStruk(barang, jumlahBeli)
                if (index < SharedData.keranjang.size - 1) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            InfoBarisStruk("Subtotal", "54.500")
            Spacer(modifier = Modifier.height(8.dp))
            InfoBarisStruk("Pajak ${persentasePajak}%", pajak.toString())

            Spacer(modifier = Modifier.height(12.dp))

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "TOTAL", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Text(text = "Rp 54.500", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            }

            Spacer(modifier = Modifier.height(16.dp))
            GarisStruk()
            Spacer(modifier = Modifier.height(16.dp))

            InfoBarisStruk("Tunai", SharedData.uangTunai.toString())
            Spacer(modifier = Modifier.height(8.dp))
            InfoBarisStruk("Kembali", (SharedData.uangTunai - totalHarga - pajak).toString())

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun InfoBarisStruk(kiri: String, kanan: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = kiri, fontSize = 14.sp, color = Color.Gray)
        Text(text = kanan, fontSize = 14.sp, color = Color.Gray, textAlign = TextAlign.End)
    }
}

@Composable
fun ItemBarisStruk(barang: Barang, jumlahBeli: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = barang.nama, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Text(text = "$jumlahBeli x ${barang.harga}", fontSize = 12.sp, color = Color.Gray)
        }
        Text(text = "Rp ${barang.harga * jumlahBeli}", fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun GarisStruk() {
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth(),
        thickness = 1.dp,
        color = Color.LightGray
    )
}

@Preview(showBackground = true)
@Composable
fun StrukPreview() {
    MaterialTheme {
        Struk(navController = rememberNavController())
    }
}