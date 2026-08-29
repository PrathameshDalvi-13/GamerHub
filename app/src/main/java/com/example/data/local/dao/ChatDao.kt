package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entities.ChatMessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat_messages WHERE scope = 'GAME' AND targetId = :gameId AND channelName = :channel ORDER BY timestamp ASC")
    fun getGameChannelMessages(gameId: String, channel: String): Flow<List<ChatMessageEntity>>

    @Query("SELECT * FROM chat_messages WHERE scope = 'SQUAD' AND targetId = :squadId ORDER BY timestamp ASC")
    fun getSquadMessages(squadId: String): Flow<List<ChatMessageEntity>>

    @Query("SELECT * FROM chat_messages WHERE scope = 'DM' AND ((senderId = :myId AND targetId = :otherId) OR (senderId = :otherId AND targetId = :myId)) ORDER BY timestamp ASC")
    fun getDirectMessages(myId: String, otherId: String): Flow<List<ChatMessageEntity>>

    @Query("SELECT * FROM chat_messages WHERE scope = 'GLOBAL' ORDER BY timestamp ASC LIMIT 100")
    fun getGlobalMessages(): Flow<List<ChatMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: ChatMessageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<ChatMessageEntity>)

    @Query("SELECT COUNT(*) FROM chat_messages")
    suspend fun getMessageCount(): Int
}
