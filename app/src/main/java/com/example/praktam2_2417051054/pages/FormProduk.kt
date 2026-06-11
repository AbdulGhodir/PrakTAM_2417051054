package com.example.praktam2_2417051054.pages

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.praktam2_2417051054.R
import com.example.praktam2_2417051054.data.model.Barang
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FormProduk(
    modifier: Modifier = Modifier,
    navController: NavController,
    barangToEdit: Barang? = null
) {
    var namaBarang by remember { mutableStateOf(barangToEdit?.nama ?: "") }
    var hargaJual by remember { mutableStateOf(barangToEdit?.harga?.toString() ?: "") }
    var stokAwal by remember { mutableStateOf(barangToEdit?.stok?.toString() ?: "") }
    var kolomKosong by remember { mutableStateOf(false) }

    val judulHalaman = if (barangToEdit != null) "Edit Barang" else "Tambah Barang"

    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcherGaleri = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uriTerpilih ->
        if (uriTerpilih != null) {
            imageUri = uriTerpilih
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(130.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = "Foto Terpilih",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                launcherGaleri.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                        contentScale = ContentScale.Crop,
                    )
                } else {
                    Column(
                        modifier = Modifier
                            .size(120.dp)
                            .drawBehind {
                                drawRoundRect(
                                    color = Color(0xFFCBD5E1),
                                    style = Stroke(
                                        width = 2.dp.toPx(),
                                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f), 0f)
                                    ),
                                    cornerRadius = CornerRadius(16.dp.toPx())
                                )
                            }
                            .background(Color.Transparent, RoundedCornerShape(16.dp))
                            .clickable {
                                launcherGaleri.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_camera),
                            contentDescription = "Pilih Foto",
                            tint = Color(0xFF94A3B8),
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Pilih Foto", color = Color(0xFF94A3B8), fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            Text(text = "Nama Barang", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = namaBarang,
                onValueChange = { namaBarang = it },
                shape = RoundedCornerShape(12.dp),
                placeholder = { Text(text = "Cth: Indomie Goreng", color = Color(0xFF94A3B8), fontSize = 14.sp) },
                leadingIcon = {
                    Icon(painter = painterResource(id = R.drawable.ic_cubes), contentDescription = null, modifier = Modifier.size(20.dp), tint = Color(0xFF94A3B8))
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    unfocusedBorderColor = Color(0xFFE2E8F0)
                ),
                singleLine = true
            )

            Spacer(Modifier.height(16.dp))

            Text(text = "Harga Jual", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = hargaJual,
                onValueChange = { hargaJual = it },
                shape = RoundedCornerShape(12.dp),
                placeholder = { Text(text = "0", color = Color(0xFF94A3B8), fontSize = 14.sp) },
                leadingIcon = {
                    Icon(painter = painterResource(id = R.drawable.ic_history), contentDescription = null, modifier = Modifier.size(20.dp), tint = Color(0xFF94A3B8))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    unfocusedBorderColor = Color(0xFFE2E8F0)
                ),
                singleLine = true
            )

            Spacer(Modifier.height(16.dp))

            Text(text = "Stok Awal", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = stokAwal,
                onValueChange = { stokAwal = it },
                shape = RoundedCornerShape(12.dp),
                placeholder = { Text(text = "0", color = Color(0xFF94A3B8), fontSize = 14.sp) },
                leadingIcon = {
                    Icon(painter = painterResource(id = R.drawable.ic_cubes), contentDescription = null, modifier = Modifier.size(20.dp), tint = Color(0xFF94A3B8))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    unfocusedBorderColor = Color(0xFFE2E8F0)
                ),
                singleLine = true
            )

            Spacer(Modifier.height(16.dp))

            if (kolomKosong) {
                Text(text = "Semua kolom harus diisi!", color = Color.Red, fontSize = 14.sp)
            }

            Spacer(Modifier.height(100.dp))
        }

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
                        .clickable { navController.popBackStack() },
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
                Spacer(Modifier.width(20.dp))
                Text(
                    text = judulHalaman,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(24.dp)
        ) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        isLoading = true
                        delay(500)
                        isLoading = false

                        if (namaBarang == "" || hargaJual == "" || stokAwal == "") {
                            kolomKosong = true
                        } else {
                            navController.popBackStack()
                        }

                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
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
                    Text(text = "Simpan Barang", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}