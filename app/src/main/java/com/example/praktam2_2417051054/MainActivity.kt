package com.example.praktam2_2417051054

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.praktam2_2417051054.ui.theme.PrakTAM2_2417051054Theme
import model.Barang
import model.BarangSource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            // Buat status bar yang di atas hp yang ada wifi, baterai, jam dll warnanya nyatu sama aplikasi
            statusBarStyle = SystemBarStyle.dark(
                scrim = Color(0xFF1e293b).hashCode()
            ),

            // Buat tombol navigasi hp yang di bawah (back, home, task) warnanya nyatu sama aplikasi
            navigationBarStyle = SystemBarStyle.light(
                scrim = Color.White.hashCode(),
                darkScrim = Color.White.hashCode()
            )
        )
        setContent {
            PrakTAM2_2417051054Theme {
                Main("Dashboard")
            }
        }
    }
}

@Composable
fun Main(page: String) {
    Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
        if (page == "Dashboard") {
            DashboardScreen(
                modifier = Modifier.padding(innerPadding)
            )
        } else if (page == "Kasir") {
            KasirScreen(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun DashboardScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFE2E8F0))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.height(235.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .border(width = 2.dp, color = Color(0xFFf1f5f9), shape = RoundedCornerShape(20.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp, 15.dp),
                    ) {
                        Text(text = "Total Transaksi", fontSize = 14.sp, fontWeight = FontWeight.Medium)

                        Spacer(Modifier.height(5.dp))

                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(text = "45", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                            Text(text = " Pelanggan", fontSize = 11.sp, color = Color(0xFF64748b))
                        }
                    }
                }

                Card(
                    modifier = Modifier
                        .weight(1f)
                        .border(width = 2.dp, color = Color(0xFFf1f5f9), shape = RoundedCornerShape(20.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp, 15.dp),
                    ) {
                        Text(text = "Barang Terjual", fontSize = 14.sp, fontWeight = FontWeight.Medium)

                        Spacer(Modifier.height(5.dp))

                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(text = "100", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                            Text(text = " Item", fontSize = 11.sp, color = Color(0xFF64748b))
                        }
                    }
                }
            }

            Spacer(Modifier.height(30.dp))

            Text(text = "Menu Utama", fontSize = 18.sp, fontWeight = FontWeight.Bold)

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
                            .clip(shape = RoundedCornerShape(20.dp))
                            .background(color = Color(0xFFf1f5f9))
                            .padding(10.dp, 15.dp),
                        tint = Color.Black
                    )

                    Spacer(Modifier.height(3.dp))
                    Text(text = "Kasir", fontSize = 13.sp, color = Color(0xFF64748b), fontWeight = FontWeight.Bold)
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
                            .clip(shape = RoundedCornerShape(20.dp))
                            .background(color = Color(0xFFf3e8ff))
                            .padding(10.dp, 15.dp),
                        tint = Color(0xFF9333ea)

                    )

                    Spacer(Modifier.height(3.dp))
                    Text(text = "Barang", fontSize = 13.sp, color = Color(0xFF64748b), fontWeight = FontWeight.Bold)
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
                            .clip(shape = RoundedCornerShape(20.dp))
                            .background(color = Color(0xFFffedd5))
                            .padding(10.dp, 15.dp),
                        tint = Color(0xFFea580c)
                    )

                    Spacer(Modifier.height(3.dp))
                    Text(text = "Riwayat", fontSize = 13.sp, color = Color(0xFF64748b), fontWeight = FontWeight.Bold)
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
                            .background(color = Color(0xFFD0D5DE))
                            .padding(10.dp, 15.dp),
                        tint = Color(0xFF64748b)
                    )

                    Spacer(Modifier.height(3.dp))
                    Text(text = "Setting", fontSize = 13.sp, color = Color(0xFF64748b), fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(30.dp))

            Text(text = "Barang Terlaris", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(15.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                val bestSeller = BarangSource.listBarang
                    .sortedByDescending { it.terjual }
                    .take(5)

                items(bestSeller) { barang ->
                    DetailBarangTerlaris(barang = barang)
                }
            }

            Spacer(Modifier.height(30.dp))

            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Transaksi Terakhir", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = "Lihat Semua", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(15.dp))

            Column (verticalArrangement = Arrangement.spacedBy(15.dp)) {
                for (i in 1..3) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(width = 2.dp, color = Color(0xFFf1f5f9), shape = RoundedCornerShape(20.dp)),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
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
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Filled.ShoppingCart,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(color = Color(0xFFf1f5f9), shape = CircleShape)
                                        .padding(10.dp),
                                    tint = Color.Black
                                )

                                Spacer(Modifier.width(10.dp))

                                Column(verticalArrangement = Arrangement.spacedBy((-5).dp)) {
                                    Text(text = "PJ-0000${i}", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                    Text(text = "1 Barang", fontSize = 12.sp, color = Color(0xFF64748b))
                                }
                            }

                            Text(text = "Rp 45.000", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))
            }
        }

        Box(contentAlignment = Alignment.TopCenter) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
                    .background(Color(0xFF1e293b))
                    .padding(25.dp, 24.dp, bottom = 80.dp, end = 25.dp)
            ) {
                Text(text = "Sabtu, 28 Februari 2026", color = Color(0xFF64748b), fontSize = 14.sp)
                Text(text = "Halo, Abdul!", color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Bold)
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 110.dp)
                    .padding(horizontal = 25.dp)
                    .border(width = 2.dp, color = Color(0xFFf1f5f9), shape = RoundedCornerShape(20.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 25.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                ) {
                    Text(text = "Pendapatan Hari Ini", fontSize = 14.sp, color = Color(0xFF64748b), fontWeight = FontWeight.Medium)

                    Spacer(Modifier.height(3.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Rp", fontSize = 18.sp, fontWeight = FontWeight.Medium)

                        Spacer(Modifier.width(5.dp))

                        Text(text = "1.000.000", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun DetailBarangTerlaris(barang: Barang) {
    Card(
        modifier = Modifier
            .width(125.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = barang.ImagesRes),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFE2E8F0))
                    .padding(5.dp)
            )

            Spacer(Modifier.height(3.dp))

            Text(text = barang.nama, fontSize = 14.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, maxLines = 2, minLines = 2)

            Spacer(Modifier.height(5.dp))

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color(0xFFffedd5))
                    .padding(horizontal = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    Icons.Filled.Star,
                    contentDescription = null,
                    modifier = Modifier
                        .size(15.dp),
                    tint = Color(0xFFEA580C)
                )

                Spacer(Modifier.width(2.dp))

                Text(text = "${barang.terjual} Terjual", fontSize = 9.sp, color = Color(0xFFEA580C), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun KasirScreen(modifier: Modifier = Modifier) {
    var searchValue by remember { mutableStateOf("") }
    var totalItem by remember { mutableIntStateOf(0) }
    var totalHarga by remember { mutableIntStateOf(0) }

    Box (
        modifier = modifier.fillMaxSize().background(Color(0xFFE2E8F0))
    ) {
        // List Item
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item { Spacer(Modifier.height(150.dp)) }

            val daftarBarangFilter = if (searchValue == "") {
                BarangSource.listBarang
            } else {
                BarangSource.listBarang.filter { barang ->
                    barang.nama.contains(searchValue, ignoreCase = true)
                }
            }

            items(daftarBarangFilter) { barang ->
                DetailBarang(barang = barang)
            }

            item { Spacer(Modifier.height(60.dp)) }

        }

        // Top Bar
        Column(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomEnd = 25.dp, bottomStart = 25.dp))
                .background(color = Color(0xFF1e293b))
                .padding(20.dp, 30.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = null,
                    modifier = Modifier.size(15.dp).scale(2.5f),
                    tint = Color.White,
                )

                Spacer(Modifier.width(20.dp))

                Text(text = "Kasir", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(30.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = searchValue,
                onValueChange = { teks -> searchValue = teks },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp),
                placeholder = { Text(text = "Cari barang...", color = Color.Gray) },
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
                .background(Color.White)
                .padding(25.dp, 20.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Text(text = "$totalItem Item", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Text(text = "Rp $totalHarga", fontSize = 25.sp, fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(10.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1e293b),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Bayar", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

@Composable
fun DetailBarang(barang: Barang) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = barang.ImagesRes),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFE2E8F0))
                    .padding(5.dp),
            )

            Spacer(Modifier.width(15.dp))

            Column {
                Text(text = barang.nama, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = "Sisa Stok: ${barang.stok}", fontSize = 12.sp)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Rp ${barang.harga}", fontSize = 16.sp, fontWeight = FontWeight.Bold)

                    Row(
                        modifier = Modifier.border(1.dp, Color(0xFFe2e8f0), RoundedCornerShape(5.dp)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        var jumlahBeli by remember { mutableIntStateOf(0) }

                        Button(
                            onClick = {
                                if (jumlahBeli > 0) {
                                    jumlahBeli--
                                }
                            },
                            modifier = Modifier.width(35.dp).height(25.dp),
                            contentPadding = PaddingValues(0.dp),
                            shape = RoundedCornerShape(5.dp),

                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFf1f5f9),
                                contentColor = Color.Black
                            )
                        ) {
                            Text(text = "-", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        }

                        Text(text = "$jumlahBeli", fontSize = 15.sp, fontWeight = FontWeight.Medium, modifier = Modifier.width(20.dp).background(Color(0xFFf1f5f9)), textAlign = TextAlign.Center)

                        Button(
                            onClick = {
                                jumlahBeli++
                            },
                            modifier = Modifier.width(35.dp).height(25.dp),
                            contentPadding = PaddingValues(0.dp),
                            shape = RoundedCornerShape(5.dp),

                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFf1f5f9),
                                contentColor = Color.Black
                            )
                        ) {
                            Text(text = "+", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun KasirPreview() {
    PrakTAM2_2417051054Theme {
        Main("Kasir")
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    PrakTAM2_2417051054Theme {
        Main("Dashboard")
    }
}