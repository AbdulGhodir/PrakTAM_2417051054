package com.example.praktam2_2417051054

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.praktam2_2417051054.ui.theme.PrakTAM2_2417051054Theme
import model.Barang
import model.BarangSource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
    Box {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Spacer(Modifier.height(110.dp))

            BarangSource.listBarangs.forEach { barang ->
                DetailBarang(barang = barang)
                Spacer(Modifier.height(20.dp))
            }

            Spacer(Modifier.height(70.dp))
        }

        Column(
            Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF1e293b))
                .padding(top = 20.dp)
                .padding(20.dp, 30.dp)
        ) {
            Row {
                Image(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )

                Spacer(Modifier.width(15.dp))

                Text(text = "Kasir", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
        }

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
            .background(color = Color.White)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = barang.ImagesRes),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .background(Color(0xFFf1f5f9))
                .padding(5.dp)
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

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.width(35.dp).height(25.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(text = "-", fontSize = 15.sp, fontWeight = FontWeight.Medium, color = Color.White)
                    }

                    Spacer(Modifier.width(5.dp))
                    Text(text = "0", fontSize = 15.sp, fontWeight = FontWeight.Medium)
                    Spacer(Modifier.width(5.dp))

                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.width(35.dp).height(25.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(text = "+", fontSize = 15.sp, fontWeight = FontWeight.Medium, color = Color.White)
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