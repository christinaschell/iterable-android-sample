package com.example.iterableandroidsample
import android.app.Application
import com.iterable.iterableapi.CommerceItem
import com.iterable.iterableapi.IterableApi
import com.iterable.iterableapi.IterableConfig
import org.json.JSONObject


object IterableManager {

    fun init(application: Application) {
        val config = IterableConfig.Builder()
            .setPushIntegrationName(Tokens.pushIntegrationName)
            .build()
        IterableApi.initialize(application.applicationContext, Tokens.apiKey, config)
        IterableApi.getInstance().setEmail(Tokens.email)
    }

    fun trackEvent(name: String, data: Map<String, Any>? = null) {
        IterableApi.getInstance().track(name, toJson(data));
    }

    fun trackPurchase(total: Double, items: ArrayList<CommerceItem>, data: Map<String, Any>? = null) {
        IterableApi.getInstance().trackPurchase(total, items, toJson(data));
    }

    private fun toJson(map: Map<String, Any>?): JSONObject {
        val json = JSONObject()
        map?.forEach {
            json.put(it.key, it.value)
        }
        return json
    }
}

class CommerceItems {

    companion object {

        val listView: Array<CommerceItem> = arrayOf(
            CommerceItem("item1", "Item 1", 2.99, 1),
            CommerceItem("item2", "Item 2", 5.99, 1),
            CommerceItem("item3", "Item 3", 8.99, 1),
            CommerceItem("item4", "Item 4", 1.99, 1),
            CommerceItem("item5", "Item 5", 7.99, 1),
            CommerceItem("item6", "Item 6", 9.99, 1),
            CommerceItem("item7", "Item 7", 6.99, 1)
        )

        val addToCart: Array<CommerceItem> = arrayOf(
            CommerceItem("item1", "Item 1", 2.99, 1),
            CommerceItem("item2", "Item 2", 5.99, 1),
            CommerceItem("item3", "Item 3", 8.99, 1)
        )

        val removeFromCart: Array<CommerceItem> = arrayOf(
            CommerceItem("item3", "Item 3", 8.99, 1)
        )

        val purchase: Array<CommerceItem> = arrayOf(
            CommerceItem("item1", "Item 1", 2.99, 1),
            CommerceItem("item2", "Item 2", 5.99, 1)
        )

    }
}