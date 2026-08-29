package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local.dao.ChatDao
import com.example.data.local.dao.GamerDao
import com.example.data.local.dao.SquadDao
import com.example.data.local.entities.CardEndorsementEntity
import com.example.data.local.entities.ChatMessageEntity
import com.example.data.local.entities.GamerProfileEntity
import com.example.data.local.entities.SquadEntity
import com.example.data.local.entities.SquadInviteEntity
import com.example.data.local.entities.SquadMemberEntity

@Database(
    entities = [
        GamerProfileEntity::class,
        SquadEntity::class,
        SquadMemberEntity::class,
        ChatMessageEntity::class,
        CardEndorsementEntity::class,
        SquadInviteEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gamerDao(): GamerDao
    abstract fun squadDao(): SquadDao
    abstract fun chatDao(): ChatDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "squad_forge_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
