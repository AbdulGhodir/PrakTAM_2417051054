package com.example.praktam2_2417051054.pages

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.praktam2_2417051054.R
import com.example.praktam2_2417051054.SharedData
import com.example.praktam2_2417051054.data.model.Barang
import com.example.praktam2_2417051054.data.repository.BarangRepository
import com.example.praktam2_2417051054.formatRibuan
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Kasir(modifier: Modifier = Modifier, navController: NavController, onBarangLoaded: (List<Barang>) -> Unit = {}) {
    var listBarang by remember { mutableStateOf<List<Barang>>(emptyList()) }
    var isLoadingBarang by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    var searchValue by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val repository = remember { BarangRepository() }

    BackHandler {
        SharedData.keranjang = mapOf()
        navController.popBackStack()
    }

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
        Box (
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            val totalHarga = SharedData.keranjang.entries.sumOf { (barang, jumlahBarang) ->
                barang.harga * jumlahBarang
            }
            val totalItem = SharedData.keranjang.values.sum()

            // List Item
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                item { Spacer(Modifier.height(150.dp)) }

                val daftarBarangFilter = if (searchValue == "") {
                    listBarang
                } else {
                    listBarang.filter { barang ->
                        barang.nama.contains(searchValue, ignoreCase = true)
                    }
                }

                items(daftarBarangFilter) { barang ->
                    val jumlahBarangDibeli = SharedData.keranjang[barang] ?: 0
                    DetailScreen(
                        barang = barang,
                        jumlahBeli = jumlahBarangDibeli,
                        onJumlahChange = { jumlahBaru ->
                            val keranjangBaru = SharedData.keranjang.toMutableMap()
                            if (jumlahBaru <= 0) {
                                keranjangBaru.remove(barang)
                            } else {
                                keranjangBaru[barang] = jumlahBaru
                            }
                            SharedData.keranjang = keranjangBaru
                        }
                    )
                }

                item { Spacer(Modifier.height(60.dp)) }

            }

            // Top Bar
            Column(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomEnd = 25.dp, bottomStart = 25.dp))
                    .background(color = MaterialTheme.colorScheme.primary)
                    .padding(20.dp, 30.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = null,
                        modifier = Modifier
                            .size(15.dp).scale(2.5f)
                            .clickable{
                                if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                                    SharedData.keranjang = mapOf()
                                    navController.popBackStack()
                                }
                            },
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )

                    Spacer(Modifier.width(20.dp))

                    Text(text = "Kasir", color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.titleMedium)
                }

                Spacer(Modifier.height(30.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = searchValue,
                    onValueChange = { teks -> searchValue = teks },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    ),
                    shape = RoundedCornerShape(20.dp),
                    placeholder = { Text(text = "Cari barang...", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.secondary) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                        )
                    }
                )
            }

            // Bottom Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(color = MaterialTheme.colorScheme.surfaceVariant, width = 2.dp)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(25.dp, 20.dp)
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Text(text = "$totalItem Item", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "Rp $totalHarga", style = MaterialTheme.typography.headlineLarge)
                }

                Button(
                    onClick = {
                        coroutineScope.launch {
                            isLoading = true
                            delay(500)
                            isLoading = false

                            navController.navigate("pembayaran")
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    enabled = !SharedData.keranjang.isEmpty() && !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.secondary,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Memproses...")
                    } else {
                        Text(text = "Bayar", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        }
    }
}

@Composable
fun DetailScreen(barang: Barang, jumlahBeli: Int, onJumlahChange: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = barang.imagesUrl,
                contentDescription = barang.nama,
                placeholder = painterResource(id = R.drawable.loading),
                error = painterResource(id = R.drawable.error_image),

                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(5.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.width(15.dp))

            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(text = barang.nama, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(text = "Sisa Stok: ${barang.stok}", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.secondary)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Rp ${barang.harga.formatRibuan()}", style = MaterialTheme.typography.bodyLarge)

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(5.dp))
                            .border(1.dp, MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(5.dp))
                            .background(color = MaterialTheme.colorScheme.surfaceVariant),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                if (jumlahBeli > 0) {
                                    onJumlahChange(jumlahBeli - 1)
                                }
                            },
                            modifier = Modifier.width(35.dp).height(25.dp),
                            contentPadding = PaddingValues(0.dp),
                            shape = RoundedCornerShape(5.dp),

                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = MaterialTheme.colorScheme.onSurface
                            )
                        ) {
                            Text(text = "-", style = MaterialTheme.typography.labelLarge)
                        }

                        Text(text = "$jumlahBeli", style = MaterialTheme.typography.labelLarge, modifier = Modifier.width(20.dp), textAlign = TextAlign.Center)

                        Button(
                            onClick = {
                                onJumlahChange(jumlahBeli + 1)
                            },
                            modifier = Modifier.width(35.dp).height(25.dp),
                            contentPadding = PaddingValues(0.dp),
                            shape = RoundedCornerShape(5.dp),

                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = MaterialTheme.colorScheme.onSurface
                            )
                        ) {
                            Text(text = "+", style = MaterialTheme.typography.labelLarge)
                        }
                    }
                }
            }
        }
    }
}