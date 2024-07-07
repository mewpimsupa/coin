package com.pimsupa.coin.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pimsupa.coin.data.local.entity.CoinEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoins(coins: List<CoinEntity>)

    @Delete
    suspend fun deleteCoin(coin: CoinEntity)

    @Query("DELETE FROM coin")
    suspend fun clearCoinEntity()

    @Query("SELECT * FROM coin")
    fun getCoinList(): Flow<List<CoinEntity>>

    @Query("SELECT * FROM coin LIMIT :limit OFFSET :offset")
    fun getCoinList(limit: Int, offset: Int): Flow<List<CoinEntity>>

    @Query("SELECT COUNT(*) FROM coin")
    suspend fun getCoinCount(): Int
}
