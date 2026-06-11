package com.example.praktam2_2417051054.pages


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.praktam2_2417051054.R

@Composable
fun TentangAplikasi(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
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
                    text = "Tentang Aplikasi",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Box(
                modifier = Modifier
                    .size(90.dp)
                    .background(MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                    contentDescription = "Logo OurKasir",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "OurKasir",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant, shape = CircleShape)
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Versi 1.0",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Aplikasi kasir sederhana yang dirancang untuk memudahkan Anda dalam melakukan transaksi.",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(30.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Dikembangkan Oleh",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )

                    HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)

                    InfoDeveloperItem(ikon = Icons.Default.Person, label = "Nama Mahasiswa", nilai = "Abdul Ghodir Firdiansyah")
                    InfoDeveloperItem(ikon = painterResource(id = R.drawable.ic_id_card), label = "NPM", nilai = "2417051054")
                    InfoDeveloperItem(ikon = painterResource(id = R.drawable.graduation_cap), label = "Jurusan", nilai = "Ilmu Komputer")
                    InfoDeveloperItem(ikon = painterResource(id = R.drawable.ic_building_columns), label = "Universitas", nilai = "Universitas Lampung")
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "© 2026 OurKasir Project.",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun InfoDeveloperItem(ikon: ImageVector, label: String, nilai: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFFF1F5F9), shape = RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                ikon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(text = label, fontSize = 11.sp, color = Color.Gray)
            Text(text = nilai, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun InfoDeveloperItem(ikon: Painter, label: String, nilai: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFFF1F5F9), shape = RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                ikon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(text = label, fontSize = 11.sp, color = Color.Gray)
            Text(text = nilai, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TentangAplikasiPreview() {
    MaterialTheme {
        TentangAplikasi(navController = rememberNavController())
    }
}