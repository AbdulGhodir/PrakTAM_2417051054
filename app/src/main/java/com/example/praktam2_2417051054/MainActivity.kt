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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
                Main()
            }
        }
    }
}

@Composable
fun Main() {
    Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
        KasirScreen(
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun KasirScreen(modifier: Modifier = Modifier) {
    var searchValue by remember { mutableStateOf("") }

    Box (
        modifier = modifier.fillMaxSize().background(Color(0xFFE2E8F0))
    ) {
        // List Item
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Spacer(Modifier.height(160.dp))

            BarangSource.listBarangs.forEach { barang ->
                if (searchValue == "") {
                    DetailBarang(barang = barang)
                    Spacer(Modifier.height(20.dp))
                } else {
                    if (barang.nama.contains(searchValue, ignoreCase = true)) {
                        DetailBarang(barang = barang)
                        Spacer(Modifier.height(20.dp))
                    }
                }

            }

            Spacer(Modifier.height(60.dp))
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
                Text(text = "0 Item", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Text(text = "Rp 0", fontSize = 25.sp, fontWeight = FontWeight.Bold)
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color.White)
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
                        onClick = { jumlahBeli++ },
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

@Preview(showBackground = true)
@Composable
fun KasirPreview() {
    PrakTAM2_2417051054Theme {
        Main()
    }
}