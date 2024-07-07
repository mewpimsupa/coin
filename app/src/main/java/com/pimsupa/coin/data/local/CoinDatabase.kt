package com.pimsupa.coin.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pimsupa.coin.data.local.dao.CoinDao
import com.pimsupa.coin.data.local.entity.CoinEntity


@Database(
    entities = [
        CoinEntity::class],
    version = 1
)
abstract class CoinDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
}