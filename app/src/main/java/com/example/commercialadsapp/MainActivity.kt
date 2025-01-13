package com.example.commercialadsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.core.view.WindowCompat
import com.example.commercialadsapp.ui.theme.CommercialAdsAppTheme

data class Product(
    val imageRes: Int,
    val name: String,
    val originalPrice: String,
    val discountedPrice: String,
    val offer: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            CommercialAdsAppTheme {
                CommercialAdsApp()
            }
        }
    }
}

@Composable
fun CommercialAdsApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("phone_accessories") { ProductScreenPhoneAccessories(navController) }
        composable("furniture_services") { ProductScreenFurniture(navController) }
        composable("real_estate") { ProductScreenRealEstate(navController) }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .windowInsetsPadding(WindowInsets.statusBars) // Add padding for status bar
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to Our Services!", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(onClick = { navController.navigate("phone_accessories") }) {
            Text("Phone & Accessories")
        }
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(onClick = { navController.navigate("furniture_services") }) {
            Text("Furniture Services")
        }
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(onClick = { navController.navigate("real_estate") }) {
            Text("Real Estate Services")
        }
    }
}

@Composable
fun ProductCard(
    imageRes: Int,
    name: String,
    originalPrice: String,
    discountedPrice: String,
    offer: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = name,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Original Price: $originalPrice", color = Color.Gray)
                Text("Discounted Price: $discountedPrice", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Offer: $offer", color = Color.Green)
            }
        }
    }
}

@Composable
fun ProductScreenPhoneAccessories(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        ProductCard(
            imageRes = R.drawable.iphone_image,
            name = "iPhone 16 Pro",
            originalPrice = "100",
            discountedPrice = "93",
            offer = "Free Protector"
        )
        ProductCard(
            imageRes = R.drawable.google_pixel_image,
            name = "Google Pixel 7",
            originalPrice = "80",
            discountedPrice = "75",
            offer = "Free Case"
        )
    }
}

@Composable
fun ProductScreenFurniture(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        ProductCard(
            imageRes = R.drawable.table_image,
            name = "Dining Table",
            originalPrice = "700",
            discountedPrice = "630",
            offer = "Buy 1 Get 1 Chair"
        )
        ProductCard(
            imageRes = R.drawable.table_image,
            name = "Sofa Set",
            originalPrice = "500",
            discountedPrice = "450",
            offer = "10% Off"
        )
    }
}

@Composable
fun ProductScreenRealEstate(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        ProductCard(
            imageRes = R.drawable.house_image,
            name = "2 BHK Apartment",
            originalPrice = "100000",
            discountedPrice = "95000",
            offer = "5% Discount"
        )
        ProductCard(
            imageRes = R.drawable.house_image,
            name = "Commercial Office Space",
            originalPrice = "200000",
            discountedPrice = "190000",
            offer = "Includes Parking"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    CommercialAdsAppTheme {
        CommercialAdsApp()
    }
}
