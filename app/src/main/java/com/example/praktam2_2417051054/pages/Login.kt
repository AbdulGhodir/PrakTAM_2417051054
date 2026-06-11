package com.example.praktam2_2417051054.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.praktam2_2417051054.R
import com.example.praktam2_2417051054.data.model.UsersSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Login(modifier: Modifier = Modifier, navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }

    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(MaterialTheme.colorScheme.primary)
                .padding(top = 80.dp, bottom = 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .background(Color.White, shape = RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                    contentDescription = "Logo",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "OurKasir",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Kelola toko jadi lebih mudah",
                fontSize = 14.sp,
                color = Color(0xFF94A3B8)
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 260.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Selamat Datang!",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(30.dp))

                if (isError) {
                    Text(
                        text = "Email atau Password salah!",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Email", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = email,
                        onValueChange = { email = it; isError = false },
                        shape = RoundedCornerShape(12.dp),
                        placeholder = { Text(text = "Masukkan email", color = Color.Gray, fontSize = 14.sp) },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = Color.Gray)
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            unfocusedBorderColor = if (isError) Color.Red else Color(0xFFE2E8F0),
                            focusedBorderColor = MaterialTheme.colorScheme.primary
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Password", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = password,
                        onValueChange = { password = it; isError = false },
                        shape = RoundedCornerShape(12.dp),
                        placeholder = { Text(text = "Masukkan password", color = Color.Gray, fontSize = 14.sp) },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = Color.Gray)
                        },
                        trailingIcon = {
                            val image = if (passwordVisible) painterResource(id = R.drawable.ic_visibility) else painterResource(id = R.drawable.ic_visibility_off)
                            val description = if (passwordVisible) "Sembunyikan password" else "Tampilkan password"

                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(painter = image, contentDescription = description, tint = Color.Gray)
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            unfocusedBorderColor = if (isError) Color.Red else Color(0xFFE2E8F0),
                            focusedBorderColor = MaterialTheme.colorScheme.primary
                        ),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {
                        if (email == UsersSource.users.email && password == UsersSource.users.password) {
                            coroutineScope.launch {
                                isLoading = true
                                delay(1000)
                                isLoading = false

                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        } else {
                            isError = true
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

                        Text(text = "Masuk", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        Login(navController = rememberNavController())
    }
}