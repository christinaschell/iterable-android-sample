package com.example.iterableandroidsample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.iterableandroidsample.ui.theme.IterableColor
import com.example.iterableandroidsample.ui.theme.IterableAndroidSampleTheme
import android.content.Intent
import android.net.Uri

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
            color = IterableColor.lightGreen) {
            IterableManager.trackEvent("Android Custom Event",
                mapOf("platform" to "iOS", "custom_key" to true))
        }
        IterableButton(title = "Product List View",
            color = IterableColor.lightRed) {
            IterableManager.trackEvent("Product List View",
                mapOf(
                    "productImpressions" to CommerceItems.listView
                )
            )
        }
        IterableButton(title = "Add To Cart",
            color = IterableColor.lightBlue) {
            IterableManager.trackEvent("Add To Cart",
                mapOf(
                    "shoppingCartItems" to CommerceItems.addToCart
                )
            )
        }
        IterableButton(title = "Remove From Cart",
            color = IterableColor.darkPurple) {
            IterableManager.trackEvent("Remove From Cart",
                mapOf(
                    "shoppingCartItems" to CommerceItems.removeFromCart
                )
            )
        }
        IterableButton(title = "Purchase",
            color = IterableColor.lightPurple) {
            IterableManager.trackPurchase(8.98, CommerceItems.purchase,
                mapOf(
                    "is_rewards_member" to true,
                    "rewards_available" to 5000,
                    "order_discount_code" to "Summer2021"
                )
            )
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