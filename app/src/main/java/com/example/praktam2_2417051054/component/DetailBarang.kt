package com.example.praktam2_2417051054.component

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
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
import com.example.praktam2_2417051054.data.model.Barang
import com.example.praktam2_2417051054.data.model.HistorySource
import com.example.praktam2_2417051054.formatRibuan
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DetailBarang(modifier: Modifier = Modifier, barang: Barang, navController: NavController) {
    var konfirmasiHapus by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 30.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(90.dp))

            AsyncImage(
                model = barang.imagesUrl,
                contentDescription = barang.nama,
                placeholder = painterResource(id = R.drawable.loading),
                error = painterResource(id = R.drawable.error_image),

                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(5.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = barang.nama, style = MaterialTheme.typography.headlineLarge)
                Text(text = "Rp ${barang.harga.formatRibuan()}", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Button(
                    onClick = {
                        navController.navigate("editBarang/${barang.id}")
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Edit", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = {
                        konfirmasiHapus = true
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEF4444),
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Hapus", modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Hapus", fontSize = 16.sp, fontWeight = FontWeight.Bold)
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
                        .clickable {
                            if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                                navController.popBackStack()
                            }
                        },
                    tint = MaterialTheme.colorScheme.onPrimary,
                )

                Spacer(Modifier.width(20.dp))

                Text(text = "Detail Barang", color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.titleMedium)
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

    if (konfirmasiHapus) {
        AlertDialog(
            onDismissRequest = {
                konfirmasiHapus = false
            },
            icon = {
                Icon(Icons.Default.Warning, contentDescription = "Peringatan", tint = Color.Red)
            },
            title = {
                Text(text = "Hapus Semua Riwayat?", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            },
            text = {
                Text(text = "Apakah kamu yakin ingin menghapus semua data transaksi? Tindakan ini tidak dapat dibatalkan.", textAlign = TextAlign.Center)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        konfirmasiHapus = false
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Barang berhasil dihapus")
                            delay(1000)

                            navController.navigate("daftarBarang") {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }

                    }
                ) {
                    Text("Hapus", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        konfirmasiHapus = false
                    }
                ) {
                    Text("Batal", color = Color.Gray)
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(16.dp)
        )
    }
}