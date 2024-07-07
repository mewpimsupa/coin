package com.pimsupa.coin.data.local

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class CoinTypeConverters {

    private val moshi = Moshi.Builder().build()
    private val stringListAdapter = moshi.adapter<List<String>>(Types.newParameterizedType(List::class.java, String::class.java))

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return stringListAdapter.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return stringListAdapter.fromJson(value) ?: emptyList()
    }
}