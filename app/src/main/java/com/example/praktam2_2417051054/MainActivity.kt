package com.example.praktam2_2417051054

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.praktam2_2417051054.ui.theme.*
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.example.praktam2_2417051054.data.model.Barang
import com.example.praktam2_2417051054.data.repository.BarangRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            // Buat status bar yang di atas hp yang ada wifi, baterai, jam dll warnanya nyatu sama aplikasi
            statusBarStyle = SystemBarStyle.dark(
                scrim = AbuTua.hashCode()
            ),

            // Buat tombol navigasi hp yang di bawah (back, home, task) warnanya nyatu sama aplikasi
            navigationBarStyle = SystemBarStyle.light(
                scrim = SurfacePutih.hashCode(),
                darkScrim = SurfacePutih.hashCode()
            )
        )
        setContent {
            PrakTAM2_2417051054Theme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
        var listBarang by remember { mutableStateOf<List<Barang>>(emptyList()) }

        NavHost(
            navController = navController,
            startDestination = "kasir"
        ) {
            composable("home") {
                DashboardScreen(modifier = Modifier.padding(innerPadding), navController = navController) { fechedBarang ->
                    listBarang = fechedBarang
                }
            }

            composable("kasir") {
                KasirScreen(modifier = Modifier.padding(innerPadding)) { fechedBarang ->
                    listBarang = fechedBarang
                }
            }

            composable("detail/{nama}") { backStackEntry ->
                val nama = backStackEntry.arguments?.getString("nama")
                val barang = listBarang.find { it.nama == nama }
                if (barang != null) {
                    DetailRinciBarang(modifier = Modifier.padding(innerPadding), barang = barang, navController = navController)
                }
            }
        }
    }
}

@Composable
fun DashboardScreen(modifier: Modifier = Modifier, navController: NavController, onBarangLoaded: (List<Barang>) -> Unit = {}) {
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
                                Text(text = "45", style = MaterialTheme.typography.headlineLarge)
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
                        Column(modifier = Modifier.padding(20.dp, 15.dp)) {
                            Text(text = "Barang Terjual", style = MaterialTheme.typography.labelLarge)

                            Spacer(Modifier.height(5.dp))

                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(text = "100", style = MaterialTheme.typography.headlineLarge)
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
                                .clip(shape = RoundedCornerShape(20.dp))
                                .background(color = MaterialTheme.colorScheme.surfaceVariant)
                                .padding(10.dp, 15.dp),
                            tint = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(Modifier.height(5.dp))
                        Text(text = "Kasir", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
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
                                .background(color = IconUnguBg)
                                .padding(10.dp, 15.dp),
                            tint = IconUngu
                        )

                        Spacer(Modifier.height(5.dp))
                        Text(text = "Barang", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
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
                                .background(color = IconOrenBg)
                                .padding(10.dp, 15.dp),
                            tint = IconOren
                        )

                        Spacer(Modifier.height(5.dp))
                        Text(text = "Riwayat", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
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
                        Text(text = "Setting", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
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
                        DetailBarangTerlaris(barang = barang, navController = navController)
                    }
                }

                Spacer(Modifier.height(30.dp))

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
                    for (i in 1..3) {
                        Card(
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
                                        Text(text = "PJ-0000${i}", style = MaterialTheme.typography.labelLarge)
                                        Text(text = "1 Barang", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                                    }
                                }

                                Text(text = "Rp 45.000", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
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
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(text = "Pendapatan Hari Ini", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.secondary)

                        Spacer(Modifier.height(3.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Rp", style = MaterialTheme.typography.titleMedium)

                            Spacer(Modifier.width(5.dp))

                            Text(text = "1.000.000", style = MaterialTheme.typography.displayLarge)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailBarangTerlaris(barang: Barang, navController: NavController) {
    Card(
        modifier = Modifier
            .width(125.dp)
            .clickable{navController.navigate("detail/${barang.nama}")},
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
                placeholder = painterResource(id = R.drawable.gula),
                error = painterResource(id = R.drawable.telur),

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

@Composable
fun KasirScreen(modifier: Modifier = Modifier, onBarangLoaded: (List<Barang>) -> Unit = {}) {
    var listBarang by remember { mutableStateOf<List<Barang>>(emptyList()) }
    var isLoadingBarang by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    var searchValue by remember { mutableStateOf("") }
    var totalItem by remember { mutableIntStateOf(0) }
    var totalHarga by remember { mutableIntStateOf(0) }

    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

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

        Box (
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
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
                    DetailBarang(barang = barang)
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
                        modifier = Modifier.size(15.dp).scale(2.5f),
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
                    placeholder = { Text(text = "Cari barang...", color = MaterialTheme.colorScheme.secondary) },
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
                            delay(2000)
                            snackbarHostState.showSnackbar(
                                "Pembayaran berhasil diproses!"
                            )
                            isLoading = false
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    enabled = !isLoading
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

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
fun DetailBarang(barang: Barang) {
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
                placeholder = painterResource(id = R.drawable.gula),
                error = painterResource(id = R.drawable.telur),

                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(5.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.width(15.dp))

            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(text = barang.nama, style = MaterialTheme.typography.titleLarge)
                Text(text = "Sisa Stok: ${barang.stok}", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.secondary)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Rp ${barang.harga}", style = MaterialTheme.typography.bodyLarge)

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(5.dp))
                            .border(1.dp, MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(5.dp))
                            .background(color = MaterialTheme.colorScheme.surfaceVariant),
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
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = MaterialTheme.colorScheme.onSurface
                            )
                        ) {
                            Text(text = "-", style = MaterialTheme.typography.labelLarge)
                        }

                        Text(text = "$jumlahBeli", style = MaterialTheme.typography.labelLarge, modifier = Modifier.width(20.dp), textAlign = TextAlign.Center)

                        Button(
                            onClick = {
                                jumlahBeli++
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

@Composable
fun DetailRinciBarang(modifier: Modifier = Modifier, barang: Barang, navController: NavController) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(170.dp))

            AsyncImage(
                model = barang.imagesUrl,
                contentDescription = barang.nama,
                placeholder = painterResource(id = R.drawable.gula),
                error = painterResource(id = R.drawable.telur),

                modifier = Modifier
                    .fillMaxWidth()
                    .size(300.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(5.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                Arrangement.spacedBy(8.dp)
            ) {
                Text(text = barang.nama, style = MaterialTheme.typography.headlineLarge)
                Text(text = "Rp ${barang.harga}", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 20.dp, vertical = 15.dp),
                    verticalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    Text(text = "Sisa Stok", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
                    Text(text = "${barang.stok} Pcs", style = MaterialTheme.typography.headlineMedium)
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 20.dp, vertical = 15.dp),
                    verticalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    Text(text = "Terjual", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
                    Text(text = "${barang.terjual} Pcs", style = MaterialTheme.typography.headlineMedium)
                }
            }
        }

        // Top Bar
        Column(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomEnd = 25.dp, bottomStart = 25.dp))
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(20.dp, 30.dp)
                .padding(bottom = 10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = null,
                    modifier = Modifier
                        .size(15.dp)
                        .scale(2.5f)
                        .clickable{navController.popBackStack()},
                    tint = MaterialTheme.colorScheme.onPrimary,
                )

                Spacer(Modifier.width(20.dp))

                Text(text = "Detail Barang", color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}