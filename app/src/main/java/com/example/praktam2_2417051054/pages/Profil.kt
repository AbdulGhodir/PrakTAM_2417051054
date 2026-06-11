package com.example.praktam2_2417051054.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.praktam2_2417051054.data.model.HistorySource
import com.example.praktam2_2417051054.data.model.UsersSource

@Composable
fun Profil(modifier: Modifier = Modifier, navController: NavController) {
    var tampilkanKonfirmasiHapus by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxWidth()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(vertical = 60.dp),
            ) { }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp)
                    .padding(horizontal = 24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box {
                        AsyncImage(
                            model = UsersSource.users.fotoProfil,
                            contentDescription = "Foto Profil",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .border(3.dp, Color.White, CircleShape)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = UsersSource.users.nama,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Text(
                        text = UsersSource.users.namaToko,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Pengaturan",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(horizontal = 30.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column {
                MenuProfilItem(
                    ikon = Icons.Default.Person,
                    warnaIkon = Color(0xFF2196F3),
                    judul = "Edit Profil"
                ) {
                    navController.navigate("editProfil")
                }

                HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)

                MenuProfilItem(
                    ikon = Icons.Default.Delete,
                    warnaIkon = Color(0xFFF44336),
                    judul = "Hapus Semua Data Transaksi"
                ) {
                    tampilkanKonfirmasiHapus = true
                }

                HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)

                MenuProfilItem(
                    ikon = Icons.Default.Info,
                    warnaIkon = Color(0xFF4CAF50),
                    judul = "Tentang Aplikasi"
                ) {
                    navController.navigate("tentangAplikasi")
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedButton(
            onClick = {
                navController.navigate("login") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(55.dp),
            shape = RoundedCornerShape(15.dp),
            border = BorderStroke(1.dp, Color(0xFFF44336)),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFFF44336)
            )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                contentDescription = "Keluar",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Keluar Akun", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }

    if (tampilkanKonfirmasiHapus) {
        AlertDialog(
            onDismissRequest = {
                tampilkanKonfirmasiHapus = false
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
                        HistorySource.historyTransaksi.clear()
                        tampilkanKonfirmasiHapus = false
                    }
                ) {
                    Text("Hapus", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        tampilkanKonfirmasiHapus = false
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

@Composable
fun MenuProfilItem(ikon: ImageVector, warnaIkon: Color, judul: String, aksiKlik: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { aksiKlik() }
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(warnaIkon.copy(alpha = 0.1f), RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ikon,
                contentDescription = judul,
                tint = warnaIkon,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = judul,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Masuk",
            tint = Color.LightGray,
            modifier = Modifier.size(24.dp)
        )
    }
}