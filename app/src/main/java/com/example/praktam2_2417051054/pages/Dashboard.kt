package com.example.praktam2_2417051054.pages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.praktam2_2417051054.R
import com.example.praktam2_2417051054.data.model.Barang
import com.example.praktam2_2417051054.data.model.HistorySource
import com.example.praktam2_2417051054.data.repository.BarangRepository
import com.example.praktam2_2417051054.formatRibuan
import com.example.praktam2_2417051054.ui.theme.IconAbuBg
import com.example.praktam2_2417051054.ui.theme.IconOren
import com.example.praktam2_2417051054.ui.theme.IconOrenBg
import com.example.praktam2_2417051054.ui.theme.IconUngu
import com.example.praktam2_2417051054.ui.theme.IconUnguBg

@Composable
fun Dashboard(modifier: Modifier = Modifier, navController: NavController, onBarangLoaded: (List<Barang>) -> Unit = {}) {
    var listBarang by remember { mutableStateOf<List<Barang>>(emptyList()) }
    var isLoadingBarang by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    val repository = remember { BarangRepository() }

    LaunchedEffect(Unit) {
        try {
            listBarang = repository.getBarang()
            onBarangLoaded(listBarang)
            isLoadingBarang = false
        } catch (e: Exception) {
            Log.e("API_ERROR", "Gagal ngambil data barang: ${e.message}")
            isLoadingBarang = false
            isError = true
        }
    }

    if (isLoadingBarang) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (isError || listBarang.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Gagal Memuat Data",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Pastikan koneksi internet Anda menyala",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    textAlign =
                        TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    } else {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 25.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                Spacer(modifier = Modifier.height(235.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .border(width = 2.dp, color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(20.dp)),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(modifier = Modifier.padding(20.dp, 15.dp)) {
                            Text(text = "Total Transaksi", style = MaterialTheme.typography.labelLarge)

                            Spacer(Modifier.height(5.dp))

                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(text = HistorySource.historyTransaksi.size.toString(), style = MaterialTheme.typography.headlineLarge)
                                Text(text = " Pelanggan", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                            }
                        }
                    }

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .border(width = 2.dp, color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(20.dp)),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        val barangTerjual = HistorySource.historyTransaksi.sumOf { transaksi ->
                            transaksi.daftarBelanja.values.sum()
                        }

                        Column(modifier = Modifier.padding(20.dp, 15.dp)) {
                            Text(text = "Barang Terjual", style = MaterialTheme.typography.labelLarge)

                            Spacer(Modifier.height(5.dp))

                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(text = barangTerjual.toString(), style = MaterialTheme.typography.headlineLarge)
                                Text(text = " Item", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                            }
                        }
                    }
                }

                Spacer(Modifier.height(30.dp))

                Text(text = "Menu Utama", style = MaterialTheme.typography.titleMedium)

                Spacer(Modifier.height(10.dp))

                Row {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_calculator),
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                                .clickable { navController.navigate("kasir") }
                                .clip(shape = RoundedCornerShape(20.dp))
                                .background(color = MaterialTheme.colorScheme.surfaceVariant)
                                .padding(10.dp, 15.dp),
                            tint = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(Modifier.height(5.dp))
                        Text(text = "Kasir", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.SemiBold)
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_cubes),
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                                .clickable { navController.navigate("daftarBarang") }
                                .clip(shape = RoundedCornerShape(20.dp))
                                .background(color = IconUnguBg)
                                .padding(10.dp, 15.dp),
                            tint = IconUngu
                        )

                        Spacer(Modifier.height(5.dp))
                        Text(text = "Barang", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.SemiBold)
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_history),
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                                .clickable { navController.navigate("riwayat") }
                                .clip(shape = RoundedCornerShape(20.dp))
                                .background(color = IconOrenBg)
                                .padding(10.dp, 15.dp),
                            tint = IconOren
                        )

                        Spacer(Modifier.height(5.dp))
                        Text(text = "Riwayat", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.SemiBold)
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Filled.Settings,
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                                .clip(shape = RoundedCornerShape(20.dp))
                                .background(color = IconAbuBg)
                                .padding(10.dp, 15.dp),
                            tint = MaterialTheme.colorScheme.secondary
                        )

                        Spacer(Modifier.height(5.dp))
                        Text(text = "Setting", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.SemiBold)
                    }
                }

                Spacer(Modifier.height(30.dp))

                Text(text = "Barang Terlaris", style = MaterialTheme.typography.titleMedium)

                Spacer(Modifier.height(15.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                    val bestSeller = listBarang
                        .sortedByDescending { it.terjual }
                        .take(5)

                    items(bestSeller) { barang ->
                        DetailScreenBarangTerlaris(barang = barang, navController = navController)
                    }
                }

                Spacer(Modifier.height(30.dp))

                if (!HistorySource.historyTransaksi.isEmpty()) {
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Transaksi Terakhir", style = MaterialTheme.typography.titleMedium)
                        Text(text = "Lihat Semua", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary)
                    }

                    Spacer(Modifier.height(15.dp))

                    Column (verticalArrangement = Arrangement.spacedBy(15.dp)) {
                        val historyTerbaru = HistorySource.historyTransaksi.take(3)
                        historyTerbaru.forEach { transaksi ->
                            val kodeTransaksi = transaksi.kodeTransaksi
                            val jumlahBarang = transaksi.daftarBelanja.values.sum()
                            val totalHarga = transaksi.daftarBelanja.entries.sumOf { (barang, jumlahBarang) ->
                                barang.harga * jumlahBarang
                            }

                            Card(
                                onClick = { navController.navigate("struk/${transaksi.kodeTransaksi}") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(width = 2.dp, color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(20.dp)),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                                shape = RoundedCornerShape(20.dp)
                            ) {
                                Row (
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(20.dp, 15.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            Icons.Filled.ShoppingCart,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(40.dp)
                                                .background(color = MaterialTheme.colorScheme.surfaceVariant, shape = CircleShape)
                                                .padding(10.dp),
                                            tint = MaterialTheme.colorScheme.onSurface
                                        )

                                        Spacer(Modifier.width(10.dp))

                                        Column(verticalArrangement = Arrangement.spacedBy((-2).dp)) {
                                            Text(text = kodeTransaksi, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
                                            Text(text = "$jumlahBarang Barang", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                                        }
                                    }

                                    Text(text = "Rp ${totalHarga.formatRibuan()}", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        Spacer(Modifier.height(20.dp))
                    }
                }
            }

            Box(contentAlignment = Alignment.TopCenter) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(25.dp, 24.dp, bottom = 80.dp, end = 25.dp)
                ) {
                    Text(text = "Sabtu, 28 Februari 2026", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary)
                    Text(text = "Halo, Abdul!", style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.onPrimary)
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 110.dp)
                        .padding(horizontal = 25.dp)
                        .border(width = 2.dp, color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(20.dp)),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 25.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    val totalPendapatan = HistorySource.historyTransaksi.sumOf { transaksi ->
                        transaksi.daftarBelanja.entries.sumOf { (barang, jumlahBarang) ->
                            barang.harga * jumlahBarang
                        }
                    }

                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(text = "Total Pendapatan", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.secondary)

                        Spacer(Modifier.height(3.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Rp", style = MaterialTheme.typography.titleMedium)

                            Spacer(Modifier.width(5.dp))

                            Text(text = totalPendapatan.formatRibuan(), style = MaterialTheme.typography.displayLarge)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailScreenBarangTerlaris(barang: Barang, navController: NavController) {
    Card(
        onClick = { navController.navigate("detail/${barang.id}") },
        modifier = Modifier
            .width(125.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                model = barang.imagesUrl,
                contentDescription = barang.nama,
                placeholder = painterResource(id = R.drawable.loading),
                error = painterResource(id = R.drawable.error_image),

                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(5.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(3.dp))

            Text(text = barang.nama, style = MaterialTheme.typography.labelMedium, textAlign = TextAlign.Center, maxLines = 2, minLines = 2)

            Spacer(Modifier.height(5.dp))

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(color = IconOrenBg)
                    .padding(horizontal = 5.dp, vertical = 1.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    Icons.Filled.Star,
                    contentDescription = null,
                    modifier = Modifier
                        .size(15.dp),
                    tint = IconOren
                )

                Spacer(Modifier.width(2.dp))

                Text(text = "${barang.terjual} Terjual", fontSize = 9.sp, color = IconOren, fontWeight = FontWeight.Bold)
            }
        }
    }
}