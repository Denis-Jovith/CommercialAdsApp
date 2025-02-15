package com.example.commercialadsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.commercialadsapp.ui.theme.CommercialAdsAppTheme

data class Product(
    val imageRes: Int,
    val name: String,
    val originalPrice: String,
    val discountedPrice: String,
    val offer: String,
)

data class CartItem(
    val product: Product,
    var quantity: Int,
)

class Cart {
    private val items = mutableListOf<CartItem>()

    fun addProduct(product: Product, quantity: Int) {
        val existingItem = items.find { it.product == product }
        if (existingItem != null) {
            existingItem.quantity += quantity
        } else {
            items.add(CartItem(product, quantity))
        }
    }

    fun getTotalPrice(): String {
        val total = items.sumOf { it.product.discountedPrice.toDouble() * it.quantity }
        return "%.2f".format(total)
    }

    fun getItems(): List<CartItem> = items
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        setContent {
            CommercialAdsAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CommercialAdsApp()
                }
            }
        }
    }
}

@Composable
fun CommercialAdsApp() {
    val navController = rememberNavController()
    val cart = remember { Cart() }

    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("phone_accessories") { ProductScreenPhoneAccessories(navController, cart) }
        composable("furniture_services") { ProductScreenFurniture(navController, cart) }
        composable("real_estate") { ProductScreenRealEstate(navController, cart) }
        composable("product_detail/{productName}") { backStackEntry ->
            val productName = backStackEntry.arguments?.getString("productName")
            ProductDetailScreen(
                productName = productName ?: "",
                navController = navController,
                cart = cart
            )
        }
        composable("cart") {
            CartScreen(cart = cart)
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 80.dp)
        ) {
            Text(
                text = "Welcome to Our Services!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedButton(
                onClick = { navController.navigate("phone_accessories") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
            ) {
                Text("Phone & Accessories", fontSize = 16.sp)
            }

            OutlinedButton(
                onClick = { navController.navigate("furniture_services") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
            ) {
                Text("Furniture Services", fontSize = 16.sp)
            }

            OutlinedButton(
                onClick = { navController.navigate("Computer & Accessories") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
            ) {
                Text("Computer & Accessories", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }

        Footer(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 0.dp)
        )
    }
}

@Composable
fun ProductCard(product: Product, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White)
            .clickable {
                navController.navigate("product_detail/${product.name}")
            }
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(product.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Original Price: ${product.originalPrice}", color = Color.Gray)
                Text("Discounted Price: ${product.discountedPrice}", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Offer: ${product.offer}", color = Color.Red)
            }
        }
    }
}

@Composable
fun ProductDetailScreen(productName: String, navController: NavController, cart: Cart) {
    val product = getProductByName(productName)
    val quantityState = remember { mutableStateOf(1) }
    var quantity = quantityState.value


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = product?.name ?: "Product Not Found",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = product?.imageRes ?: R.drawable.iphone_image),
                contentDescription = product?.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Original Price: ${product?.originalPrice}", color = Color.Gray)
            Text("Discounted Price: ${product?.discountedPrice}", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Offer: ${product?.offer}", color = Color.Red)
            Spacer(modifier = Modifier.height(16.dp))

            Text("Quantity: $quantity")
            Row {
                Button(onClick = { if (quantityState.value > 1) quantityState.value -= 1 }) {
                    Text("-")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { quantityState.value += 1 }) {
                    Text("+")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Total Price: ${
                    product?.discountedPrice?.toDouble()?.times(quantity)?.let { "%.2f".format(it) }
                }"
            )

            OutlinedButton(
                onClick = {
                    product?.let {
                        cart.addProduct(it, quantity)
                    }
                    navController.popBackStack()
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Add to Cart")
            }

            OutlinedButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Back")
            }
        }
    }
}

fun getProductByName(name: String): Product? {
    val products = listOf(
        Product(R.drawable.iphone_image, "iPhone 16 Pro", "100", "93", "Free Protector"),
        Product(R.drawable.google_pixel_image, "Google Pixel 7", "80", "75", "Free Case"),
        Product(R.drawable.table_image, "Dining Table", "700", "630", "Buy 1 Get 1 Chair"),
        Product(R.drawable.bed_image, "Sofa Set", "500", "450", "10% Off"),
        Product(R.drawable.house_image, "2 BHK Apartment", "100000", "95000", "5% Discount"),
    )
    return products.find { it.name == name }
}

@Composable
fun ProductScreenPhoneAccessories(navController: NavController, cart: Cart) {
    val products = listOf(
        Product(R.drawable.iphone_image, "iPhone 16 Pro", "100", "93", "Free Protector"),
        Product(R.drawable.google_pixel_image, "Google Pixel 7", "80", "75", "Free Case")
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .windowInsetsPadding(WindowInsets.statusBars)
        ) {
            items(products) { product ->
                ProductCard(product, navController)
            }
        }
        Footer(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 0.dp)
        )
    }
}

@Composable
fun ProductScreenFurniture(navController: NavController, cart: Cart) {
    val products = listOf(
        Product(R.drawable.table_image, "Dining Table", "700", "630", "Buy 1 Get 1 Chair"),
        Product(R.drawable.bed_image, "Sofa Set", "500", "450", "10% Off")
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .windowInsetsPadding(WindowInsets.statusBars)
        ) {
            items(products) { product ->
                ProductCard(product, navController)
            }
        }
        Footer(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 0.dp)
        )
    }
}

@Composable
fun ProductScreenRealEstate(navController: NavController, cart: Cart) {
    val products = listOf(
        Product(R.drawable.house_image, "2 BHK Apartment", "100000", "95000", "5% Discount")
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(products) { product ->
                ProductCard(product, navController)
            }
        }

        Footer(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 0.dp)
        )
    }
}

@Composable
fun CartScreen(cart: Cart) {
    val items = cart.getItems()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            items(items) { cartItem ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)) {
                    Text(
                        "${cartItem.product.name} x${cartItem.quantity}",
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text("Total: ${cartItem.product.discountedPrice.toDouble() * cartItem.quantity}")
                }
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text("Total Price: ${cart.getTotalPrice()}", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = { /* Handle Order Placement Logic Here */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Place Order")
            }
        }
    }
}

@Composable
fun Footer(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Gray)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Contact Us: denisjovitusbuberwa@gmail.com",
            fontSize = 14.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Follow us on GitHub : www.github.com/denisjovitus",
            fontSize = 14.sp,
            color = Color.White
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
