package com.example.commercialadsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.commercialadsapp.ui.theme.CommercialAdsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        composable("phone_accessories") { ProductScreen("Phone & Accessories") }
        composable("furniture_services") { ProductScreen("Furniture Services") }
        composable("real_estate") { ProductScreen("Real Estate Services") }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
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
fun ProductScreen(title: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        ProductCard(
            imageRes = R.drawable.google_pixel_image,
            name = "Google Pixel 6A",
            originalPrice = "$10",
            discountedPrice = "$8",
            offer = "Free USB Charger"
        )
        Spacer(modifier = Modifier.height(16.dp))
        ProductCard(
            imageRes = R.drawable.iphone_image,
            name = "iPhone 16 Pro",
            originalPrice = "$100",
            discountedPrice = "$93",
            offer = "Free Protector"
        )
    }
}

@Composable
fun ProductCard(imageRes: Int, name: String, originalPrice: String, discountedPrice: String, offer: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
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

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    CommercialAdsAppTheme {
        CommercialAdsApp()
    }
}
