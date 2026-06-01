package com.example.praktam2_2417051054

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.praktam2_2417051054.ui.theme.*
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.navigation.NavHostController
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.praktam2_2417051054.Component.DetailBarang
import com.example.praktam2_2417051054.data.model.Barang
import com.example.praktam2_2417051054.pages.DaftarBarang
import com.example.praktam2_2417051054.pages.Dashboard
import com.example.praktam2_2417051054.pages.Kasir
import com.example.praktam2_2417051054.pages.Pembayaran
import com.example.praktam2_2417051054.pages.Struk

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
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val halamanSaatIni = navBackStackEntry?.destination?.route ?: "home"
                val noNavbar = listOf("detail/{id}", "kasir", "daftarBarang", "pembayaran", "struk")

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    bottomBar = { if (halamanSaatIni !in noNavbar){ Navbar(navController) } },
                    floatingActionButton = {
                        if (halamanSaatIni !in noNavbar) {
                            FloatingActionButton(
                                onClick = { navController.navigate("kasir")  },
                                shape = CircleShape,
                                modifier = Modifier
                                    .size(60.dp)
                                    .offset(y = 60.dp)
                                    .border(width = 3.dp, color = Color.White, shape = CircleShape),
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = Color.White,
                            ) {
                                Icon(Icons.Filled.Add, contentDescription = "Kasir")
                            }
                        }
                    },
                    floatingActionButtonPosition = FabPosition.Center
                ) { innerPadding ->
                    AppNavigation(
                        modifier = Modifier.padding(
                            innerPadding
                        ), navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun AppNavigation(modifier: Modifier, navController: NavHostController) {
    var listBarang by remember { mutableStateOf<List<Barang>>(emptyList()) }

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            Dashboard(modifier = modifier, navController = navController) { fechedBarang ->
                listBarang = fechedBarang
            }
        }

        composable("daftarBarang") {
            DaftarBarang(modifier = modifier, navController = navController) { fechedBarang ->
                listBarang = fechedBarang
            }
        }

        composable("kasir") {
            Kasir(modifier = modifier, navController = navController) { fechedBarang ->
                listBarang = fechedBarang
            }
        }

        composable("pembayaran") {
            Pembayaran(modifier = modifier, navController = navController)
        }

        composable("struk") {
            Struk(modifier = modifier, navController = navController)
        }

        composable("detail/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            val barang = listBarang.find { it.id.toString() == id }
            if (barang != null) {
                DetailBarang(modifier = modifier, barang = barang, navController = navController)
            }
        }
    }
}

object SharedData {
    var keranjang by mutableStateOf(mapOf<Barang, Int>())
    var uangTunai by mutableStateOf(0)
}

@Composable
fun Navbar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val halamanSaatIni = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = Modifier
            .border(width = 2.dp, color = Color(0xFFe2e8f0))
            .background(Color.White)
            .padding(15.dp, 0.dp),
        containerColor = Color.White
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home", modifier = Modifier.size(30.dp)) },
            label = { Text(text = "Home") },
            selected = halamanSaatIni == "home",
            onClick = {
                navController.navigate("home") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },

            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = Color.Transparent,

                unselectedTextColor = MaterialTheme.colorScheme.secondary,
                unselectedIconColor = MaterialTheme.colorScheme.secondary
            )
        )

        NavigationBarItem(
            icon = { Icon(painter = painterResource(id = R.drawable.ic_items_stacks), contentDescription = "Barang", modifier = Modifier.size(30.dp)) },
            label = { Text(text = "Barang") },
            selected = halamanSaatIni == "daftarBarang",
            onClick = {
                navController.navigate("daftarBarang") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },

            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = Color.Transparent,

                unselectedTextColor = MaterialTheme.colorScheme.secondary,
                unselectedIconColor = MaterialTheme.colorScheme.secondary
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        NavigationBarItem(
            icon = { Icon(painter = painterResource(id = R.drawable.ic_history), contentDescription = "Riwayat", modifier = Modifier.size(30.dp)) },
            label = { Text(text = "Riwayat") },
            selected = halamanSaatIni == "Riwayat",
            onClick = {
                navController.navigate("home") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },

            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = Color.Transparent,

                unselectedTextColor = MaterialTheme.colorScheme.secondary,
                unselectedIconColor = MaterialTheme.colorScheme.secondary
            )
        )

        NavigationBarItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Profil", modifier = Modifier.size(30.dp)) },
            label = { Text(text = "Profil") },
            selected = halamanSaatIni == "Profil",
            onClick = {
                navController.navigate("home") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },

            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = Color.Transparent,

                unselectedTextColor = MaterialTheme.colorScheme.secondary,
                unselectedIconColor = MaterialTheme.colorScheme.secondary
            )
        )
    }
}