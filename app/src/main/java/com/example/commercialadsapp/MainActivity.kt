package com.example.commercialadsapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.commercialadsapp.ui.theme.CommercialAdsAppTheme


data class Product(
    val id: Int,
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
        composable("phone_accessories") { ProductScreen(navController, getPhoneAccessories()) }
        composable("furniture_services") { ProductScreen(navController, getFurnitureItems()) }
        composable("real_estate") { ProductScreen(navController, getRealEstateItems()) }
        composable("product_detail/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toInt()
            ProductDetailScreen(navController, productId)
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .windowInsetsPadding(WindowInsets.statusBars)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to Our Services!", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))


        BasicTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            singleLine = true,
            decorationBox = { innerTextField ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray, RoundedCornerShape(8.dp))
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    innerTextField()
                }
            }
        )

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
    offer: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp)
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
fun ProductScreen(navController: NavController, products: List<Product>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        items(products) { product ->
            ProductCard(
                imageRes = product.imageRes,
                name = product.name,
                originalPrice = product.originalPrice,
                discountedPrice = product.discountedPrice,
                offer = product.offer,
                onClick = {
                    navController.navigate("product_detail/${product.id}")
                }
            )
        }
    }
}

fun getPhoneAccessories(): List<Product> {
    return listOf(
        Product(
            id = 1,
            imageRes = R.drawable.iphone_image,
            name = "iPhone 16 Pro",
            originalPrice = "100",
            discountedPrice = "93",
            offer = "Free Protector"
        ),
        Product(
            id = 2,
            imageRes = R.drawable.google_pixel_image,
            name = "Google Pixel 7",
            originalPrice = "80",
            discountedPrice = "75",
            offer = "Free Case"
        )
    )
}

fun getFurnitureItems(): List<Product> {
    return listOf(
        Product(
            id = 101,
            imageRes = R.drawable.table_image,
            name = "Dining Table",
            originalPrice = "700",
            discountedPrice = "630",
            offer = "Buy 1 Get 1 Chair"
        ),
        Product(
            id = 102,
            imageRes = R.drawable.table_image,
            name = "Sofa Set",
            originalPrice = "500",
            discountedPrice = "450",
            offer = "10% Off"
        )
    )
}

fun getRealEstateItems(): List<Product> {
    return listOf(
        Product(
            id = 201,
            imageRes = R.drawable.house_image,
            name = "2 BHK Apartment",
            originalPrice = "100000",
            discountedPrice = "95000",
            offer = "5% Discount"
        ),
        Product(
            id = 202,
            imageRes = R.drawable.house_image,
            name = "Commercial Office Space",
            originalPrice = "200000",
            discountedPrice = "190000",
            offer = "Includes Parking"
        )
    )
}

@Composable
fun ProductDetailScreen(navController: NavController, productId: Int?) {
    val product = productId?.let {
        getPhoneAccessories().find { product -> product.id == it }
            ?: getFurnitureItems().find { product -> product.id == it }
            ?: getRealEstateItems().find { product -> product.id == it }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        product?.let {
            Text(it.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = it.imageRes),
                contentDescription = it.name,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("Original Price: ${it.originalPrice}")
            Text("Discounted Price: ${it.discountedPrice}", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Offer: ${it.offer}", color = Color.Green)
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
