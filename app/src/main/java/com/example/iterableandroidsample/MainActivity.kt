package com.example.iterableandroidsample

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.iterableandroidsample.ui.theme.IterableAndroidSampleTheme
import android.content.Intent
import android.net.Uri
import java.util.*

private val LightGreen = Color(red = 116, green = 190, blue = 169)
private val LightRed = Color(red = 219, green = 75, blue = 88)
private val LightBlue = Color(red = 99, green = 193, blue = 238)
private val DarkPurple = Color(red = 97, green = 42, blue = 106)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        IterableManager.init(application)
        val appLinkIntent = intent
        val appLinkAction = appLinkIntent.action
        val appLinkData = appLinkIntent.data
        handleIntent(intent)
        setContent {
            IterableAndroidSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    IterableView()
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        val appLinkAction = intent.action
        val appLinkData: Uri? = intent.data
        if (Intent.ACTION_VIEW == appLinkAction) {
            appLinkData?.also { url ->
               Log.d("Iterable","Handled deep link: $url")
            }
        }
    }
}

@Composable
fun IterableView() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Image(
            painterResource(id = R.drawable.iterable),
            contentDescription = null,
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .padding(20.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        IterableButton(title = "Track Custom Event",
            color = LightGreen) {
            IterableManager.trackEvent("Android Custom Event",
                mapOf("platform" to "iOS", "custom_key" to true))
        }
        IterableButton(title = "Product List View",
            color = LightRed) {
            IterableManager.trackEvent("Product List View",
                mapOf(
                    "item_id" to CommerceItems.listView.map { it.id },
                    "item_name" to CommerceItems.listView.map { it.name },
                    "item_price" to CommerceItems.listView.map { it.price }
                )
            )
        }
        IterableButton(title = "Add To Cart",
            color = LightBlue) {
            CommerceItems.addToCart.forEach { item ->
                IterableManager.trackEvent("Add To Cart",
                    mapOf(
                        "item_id" to item.id,
                        "item_name" to item.name,
                        "item_price" to item.price,
                        "item_quantity" to item.quantity,
                    )
                )
            }
        }
        IterableButton(title = "Remove From Cart",
            color = DarkPurple) {
            CommerceItems.removeFromCart.forEach { item ->
                IterableManager.trackEvent("Remove From Cart",
                    mapOf(
                        "item_id" to item.id,
                        "item_name" to item.name,
                        "item_price" to item.price,
                        "item_quantity" to item.quantity,
                    )
                )
            }
        }
        IterableButton(title = "Purchase",
            color = LightGreen) {
            CommerceItems.purchase.forEach { item ->
                IterableManager.trackEvent("Purchase",
                    mapOf(
                        "item_id" to item.id,
                        "item_name" to item.name,
                        "item_price" to item.price,
                        "item_quantity" to item.quantity,
                    )
                )
            }
        }
    }
}

@Composable
fun IterableButton(title: String, color: Color, action: () -> Unit) {
    Button(onClick = action,
        colors = ButtonDefaults
            .buttonColors(backgroundColor = color,
                contentColor = Color.White),
        modifier = Modifier.width(200.dp)) {
        Text(text = title)
    }
    Spacer(modifier = Modifier.height(15.dp))
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    IterableAndroidSampleTheme {
        IterableView()
    }
}