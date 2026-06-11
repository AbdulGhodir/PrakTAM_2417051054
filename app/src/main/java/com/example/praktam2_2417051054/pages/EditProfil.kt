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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.praktam2_2417051054.R
import com.example.praktam2_2417051054.data.model.UsersSource

@Composable
fun EditProfil(modifier: Modifier = Modifier, navController: NavController) {
    var namaLengkap by remember { mutableStateOf(UsersSource.users.nama) }
    var email by remember { mutableStateOf(UsersSource.users.email) }
    var namaToko by remember { mutableStateOf(UsersSource.users.namaToko) }
    var kolomKosong by remember { mutableStateOf(false) }

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
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(130.dp))

            Box(
                modifier = Modifier.padding(top = 20.dp, bottom = 30.dp)
            ) {
                AsyncImage(
                    model = imageUri ?: UsersSource.users.fotoProfil,
                    contentDescription = "Foto Profil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .border(4.dp, Color.White, CircleShape)
                )

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.BottomEnd)
                        .offset(x = 5.dp, y = 5.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                        .border(2.dp, Color.White, CircleShape)
                        .clickable {
                            launcherGaleri.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Ubah Foto",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Nama Lengkap",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = namaLengkap,
                    onValueChange = { namaLengkap = it },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        unfocusedBorderColor = Color(0xFFE2E8F0), // Abu-abu terang
                        focusedBorderColor = MaterialTheme.colorScheme.primary
                    ),
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = Color.Gray)
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Email",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = email,
                    onValueChange = { email = it },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        unfocusedBorderColor = Color(0xFFE2E8F0),
                        focusedBorderColor = MaterialTheme.colorScheme.primary
                    ),
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = Color.Gray)
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Nama Toko",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = namaToko,
                    onValueChange = { namaToko = it },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        unfocusedBorderColor = Color(0xFFE2E8F0),
                        focusedBorderColor = MaterialTheme.colorScheme.primary
                    ),
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null, tint = Color.Gray)
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (kolomKosong) {
                Text(text = "Semua kolom harus diisi!", color = Color.Red, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(120.dp))
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
                    contentDescription = "Kembali",
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
                Text(
                    text = "Edit Profil",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
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
                    if (namaLengkap == "" || email == "" || namaToko == "") {
                        kolomKosong = true
                    } else {
                        UsersSource.users.nama = namaLengkap
                        UsersSource.users.email = email
                        UsersSource.users.namaToko = namaToko

                        if (imageUri != null) {
                            UsersSource.users.fotoProfil = imageUri!!
                        }

                        if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
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
                )
            ) {
                Text(
                    text = "Simpan Perubahan",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

    }
}